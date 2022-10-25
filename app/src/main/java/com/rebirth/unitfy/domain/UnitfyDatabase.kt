package com.rebirth.unitfy.domain

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rebirth.unitfy.domain.daos.ConvertioUnitDao
import com.rebirth.unitfy.domain.daos.MutationDao
import com.rebirth.unitfy.domain.daos.UnitClassificationDao
import com.rebirth.unitfy.domain.entities.ConvertionUnit
import com.rebirth.unitfy.domain.entities.Mutation
import com.rebirth.unitfy.domain.entities.UnitClassification

@Database(
    entities = [UnitClassification::class, ConvertionUnit::class, Mutation::class],
    version = 4
)
abstract class UnitfyDatabase : RoomDatabase() {

    abstract fun unitClassificationDao(): UnitClassificationDao

    abstract fun mutationDao(): MutationDao

    abstract fun convertioUnitDao(): ConvertioUnitDao
}