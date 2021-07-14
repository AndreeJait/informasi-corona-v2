package student.if419014.informasicorona.repo.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import student.if419014.informasicorona.model.response.GetAllResponse

@Dao
interface AllDao {
    @Query("SELECT * FROM response LIMIT 1")
    suspend fun getAllResponse(): GetAllResponse
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResponse(getAllResponse: GetAllResponse)
    @Query("DELETE FROM response")
    suspend fun bulkDelete()
}