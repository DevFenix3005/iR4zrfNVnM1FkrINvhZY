package com.rebirth.unitfy.domain.entities

import androidx.room.Embedded
import androidx.room.Relation

data class ClassificationWithUnits(
    @Embedded val classification: UnitClassification,
    @Relation(
        parentColumn = "unit_classification_id",
        entityColumn = "unit_classification_id"
    ) val units: List<ConvertionUnit>
)