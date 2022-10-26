package com.rebirth.unitfy.domain.entities

import androidx.room.*

@Entity(
    tableName = "mutation",
    indices = [
        Index(
            name = "mutation_convertion_unit_id_IDX",
            unique = true,
            value = ["convertion_unit_id", "invertion_unit_id"]
        )
    ],
    foreignKeys = [
        ForeignKey(
            entity = ConvertionUnit::class,
            parentColumns = ["convertion_unit_id"],
            childColumns = ["convertion_unit_id"],
            onDelete = ForeignKey.NO_ACTION,
            onUpdate = ForeignKey.NO_ACTION
        ),
        ForeignKey(
            entity = ConvertionUnit::class,
            parentColumns = ["convertion_unit_id"],
            childColumns = ["invertion_unit_id"],
            onDelete = ForeignKey.NO_ACTION,
            onUpdate = ForeignKey.NO_ACTION
        )
    ]
)
data class Mutation(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "mutation_id") var mutationId: Long? = null,
    @ColumnInfo(name = "formula_convertion") val formulaConvertion: String,
    @ColumnInfo(name = "formula_invertion") val formulaInvertion: String,
    @ColumnInfo(name = "convertion_unit_id") val convertionUnitId: Long,
    @ColumnInfo(name = "invertion_unit_id") val invertionUnitId: Long
)
