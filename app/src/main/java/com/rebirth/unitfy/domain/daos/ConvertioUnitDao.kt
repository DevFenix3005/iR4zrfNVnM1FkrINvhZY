package com.rebirth.unitfy.domain.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.rebirth.unitfy.domain.entities.ConvertionUnit

@Dao
interface ConvertioUnitDao {

    @Query("SELECT * FROM convertion_unit cu0 WHERE cu0.unit_classification_id = :ucid")
    suspend fun fetchAllByClassification(ucid:Long): List<ConvertionUnit>

    @Insert
    suspend fun create(vararg convertionUnit: ConvertionUnit)


}