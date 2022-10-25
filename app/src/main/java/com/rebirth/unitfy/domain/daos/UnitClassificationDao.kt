package com.rebirth.unitfy.domain.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.rebirth.unitfy.domain.entities.ClassificationWithUnits
import com.rebirth.unitfy.domain.entities.UnitClassification

@Dao
interface UnitClassificationDao {

    @Query("SELECT * FROM unit_classification")
    suspend fun fetchAll(): List<UnitClassification>

    @Insert
    suspend fun create(vararg unitClassification: UnitClassification)


    @Transaction
    @Query("SELECT * FROM unit_classification")
    suspend fun fetchAllWithUnits(): List<ClassificationWithUnits>

}