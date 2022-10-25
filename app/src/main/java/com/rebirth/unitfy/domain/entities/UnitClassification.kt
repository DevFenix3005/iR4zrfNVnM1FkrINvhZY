package com.rebirth.unitfy.domain.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "unit_classification",
    indices = [Index(
        name = "unit_classification_classification_name_IDX",
        value = ["classification_name"],
        unique = true
    )]
)
data class UnitClassification(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "unit_classification_id") val id: Long,
    @ColumnInfo(name = "classification_name") val name: String
)


