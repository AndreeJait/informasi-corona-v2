package student.if419014.informasicorona

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.coroutines.*
import pl.droidsonroids.gif.GifImageView
import ru.gildor.coroutines.retrofit.awaitResponse
import student.if419014.informasicorona.api.ApiService
import student.if419014.informasicorona.api.Client
import student.if419014.informasicorona.repo.db.AppDatabase

class SplashActivity : AppCompatActivity() {
    lateinit var loading:GifImageView
    var client: ApiService = Client().createService
    val db by lazy { AppDatabase(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()
        loading = findViewById(R.id.loading)
        View.VISIBLE.also { loading.visibility = it }
        CoroutineScope(Dispatchers.IO).launch {
            val all = client.getInfoGlobal().awaitResponse()
            if(all.isSuccessful){
                val delete =async { db.allDao().bulkDelete() }
                delete.await()
                val insert = async { all.body()?.let { db.allDao().insertResponse(it) } }
                insert.await()
                withContext(Dispatchers.Main){
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                    View.GONE.also { loading.visibility = it }
                    finish()
                }
            }
        }
    }
}