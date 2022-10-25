package com.rebirth.unitfy.domain.entities

import androidx.room.Embedded
import androidx.room.Relation

data class MutationWithUnits(
    @Embedded val mutation: Mutation,
    @Relation(
        parentColumn = "convertion_unit_id",
        entityColumn = "convertion_unit_id"
    )
    val convertionUnit: ConvertionUnit,
    @Relation(
        parentColumn = "invertion_unit_id",
        entityColumn = "convertion_unit_id"
    )
    val invertionUnit: ConvertionUnit

)
