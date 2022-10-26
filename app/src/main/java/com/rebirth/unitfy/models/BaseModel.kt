package com.rebirth.unitfy.models

import androidx.lifecycle.MutableLiveData
import com.rebirth.unitfy.domain.daos.ConvertioUnitDao
import com.rebirth.unitfy.domain.daos.MutationDao
import com.rebirth.unitfy.domain.daos.UnitClassificationDao
import com.rebirth.unitfy.domain.entities.ConvertionUnit
import com.rebirth.unitfy.domain.entities.Mutation
import com.rebirth.unitfy.domain.entities.UnitClassification
import kotlinx.coroutines.runBlocking

open class BaseModel constructor(
    protected val unitClassificationDao: UnitClassificationDao,
    protected val convertionUnitDao: ConvertioUnitDao,
    protected val mutationDao: MutationDao
) {

    private fun fetchClassification(): List<UnitClassification> {
        return runBlocking {
            unitClassificationDao.fetchAll()
        }
    }

    private fun fetchConvertionUnit(classId: Long): List<ConvertionUnit> {
        return runBlocking {
            convertionUnitDao.fetchAllByClassification(classId)
        }
    }

    fun fetchMutationByUnits(p0: Long, p1: Long): Mutation {
        return runBlocking {
            mutationDao.fetchMutationByUnits(p0, p1)
        }
    }

    fun createContentToClassificationUnits(): MutableLiveData<List<UnitClassification>> {
        return fetchClassification().let {
            val placeholder = UnitClassification(-1, "Selecciona")
            val classificationListWithHint = mutableListOf(placeholder)
            classificationListWithHint.addAll(it)
            return@let MutableLiveData<List<UnitClassification>>(classificationListWithHint)
        }
    }

    fun createEmptyConvertionUnitList(): MutableLiveData<List<ConvertionUnit>> {
        return MutableLiveData<List<ConvertionUnit>>(
            emptyList()
        )
    }

    fun createConvertionUnitsByClassification(classId: Long, msg: String): List<ConvertionUnit> {
        val convertionUnits = fetchConvertionUnit(classId)
        val placeholder = ConvertionUnit(-1, msg, "?", -1)
        val convertionUnitMutableList = mutableListOf(placeholder)
        convertionUnitMutableList.addAll(convertionUnits)
        return convertionUnitMutableList
    }

}