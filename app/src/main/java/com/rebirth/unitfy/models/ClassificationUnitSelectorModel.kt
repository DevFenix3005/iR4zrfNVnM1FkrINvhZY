package com.rebirth.unitfy.models

import com.rebirth.unitfy.domain.daos.UnitClassificationDao
import com.rebirth.unitfy.domain.entities.UnitClassification
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClassificationUnitSelectorModel @Inject constructor(val classificationDao: UnitClassificationDao) {

    fun getClassification(): List<UnitClassification> {
        return runBlocking {
            classificationDao.fetchAll()
        }
    }

}