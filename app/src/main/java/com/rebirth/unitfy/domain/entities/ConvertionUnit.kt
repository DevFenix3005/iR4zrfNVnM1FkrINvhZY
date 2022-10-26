package com.rebirth.unitfy.domain.entities

import androidx.room.*
import androidx.room.ForeignKey.CASCADE

@Entity(
    tableName = "convertion_unit",
    indices = [
        Index(
            name = "convertion_unit_convertion_unit_name_IDX",
            value = ["convertion_unit_name"],
            unique = true
        ),
        Index(
            name = "convertion_unit_convertion_unit_sufix_IDX",
            value = ["convertion_unit_sufix"],
            unique = true
        ),
        Index(
            name = "convertion_unit_unit_classification_id_IDX",
            value = ["unit_classification_id"]
        )
    ],
    foreignKeys = [
        ForeignKey(
            entity = UnitClassification::class,
            parentColumns = ["unit_classification_id"],
            childColumns = ["unit_classification_id"],
            onDelete = CASCADE,
            onUpdate = CASCADE
        )
    ]
)
data class ConvertionUnit(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "convertion_unit_id") var id: Long? = null,
    @ColumnInfo(name = "convertion_unit_name") val unitName: String,
    @ColumnInfo(name = "convertion_unit_sufix") val sufix: String,
    @ColumnInfo(name = "unit_classification_id") val classificationId: Long
)