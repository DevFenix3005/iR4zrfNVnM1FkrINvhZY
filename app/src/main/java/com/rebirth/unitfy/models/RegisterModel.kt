package com.rebirth.unitfy.models

import com.rebirth.unitfy.domain.daos.ConvertioUnitDao
import com.rebirth.unitfy.domain.daos.MutationDao
import com.rebirth.unitfy.domain.daos.UnitClassificationDao
import com.rebirth.unitfy.domain.entities.ConvertionUnit
import com.rebirth.unitfy.domain.entities.Mutation
import com.rebirth.unitfy.domain.entities.UnitClassification
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RegisterModel @Inject constructor(
    unitClassificationDao: UnitClassificationDao,
    convertionUnitDao: ConvertioUnitDao,
    mutationDao: MutationDao
) : BaseModel(unitClassificationDao, convertionUnitDao, mutationDao) {

    fun createNewClassification(name: String): UnitClassification {
        return runBlocking {
            val newClassification = UnitClassification(name = name)
            val newId = unitClassificationDao.create(newClassification)
            newClassification.id = newId
            return@runBlocking newClassification
        }

    }

    fun createNewUnit(name: String, sufix: String, classificationId: Long): ConvertionUnit {
        return runBlocking {
            val convertionUnit =
                ConvertionUnit(unitName = name, sufix = sufix, classificationId = classificationId)
            convertionUnit.id = convertionUnitDao.create(convertionUnit)
            return@runBlocking convertionUnit
        }
    }

    fun createMutation(
        fConvertion: String,
        fInvertion: String,
        unitOrigin: Long,
        unitDestiny: Long
    ): List<Mutation> {
        val mutationConvertion = Mutation(
            formulaConvertion = fConvertion,
            formulaInvertion = fInvertion,
            convertionUnitId = unitOrigin, invertionUnitId = unitDestiny
        )
        val mutationInvertion = Mutation(
            formulaConvertion = fInvertion,
            formulaInvertion = fConvertion,
            convertionUnitId = unitDestiny,
            invertionUnitId = unitOrigin
        )

        val newMutations = runBlocking {
            return@runBlocking mutationDao.create(mutationConvertion, mutationInvertion)
        }
        mutationConvertion.mutationId = newMutations[0]
        mutationInvertion.mutationId = newMutations[1]

        return listOf(mutationConvertion, mutationInvertion)
    }

}