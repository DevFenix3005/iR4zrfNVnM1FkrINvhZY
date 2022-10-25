package com.rebirth.unitfy.models

import androidx.lifecycle.MutableLiveData
import com.rebirth.unitfy.domain.daos.ConvertioUnitDao
import com.rebirth.unitfy.domain.daos.MutationDao
import com.rebirth.unitfy.domain.daos.UnitClassificationDao
import com.rebirth.unitfy.domain.entities.ConvertionUnit
import com.rebirth.unitfy.domain.entities.Mutation
import com.rebirth.unitfy.domain.entities.UnitClassification
import kotlinx.coroutines.runBlocking
import org.mariuszgromada.math.mxparser.Argument
import org.mariuszgromada.math.mxparser.Expression
import org.mariuszgromada.math.mxparser.Function
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConvertionModel @Inject constructor(
    private val unitClassificationDao: UnitClassificationDao,
    private val convertionUnitDao: ConvertioUnitDao,
    private val mutationDao: MutationDao
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

    fun solveFormula(value: String, formula: String): String {
        val mathFunction = Function(formula)
        val argument = Argument("x = $value")
        val expression = Expression("f(x)", mathFunction, argument)
        val calc = expression.calculate()
        return if (calc.isNaN()) "0" else calc.toString()
    }

    fun tapButtonHandler(key: String, currentValue: String): String {
        when (key) {
            "ClearAll" -> {
                return EMPTY_SPACE
            }
            "ClearOne" -> {
                return if (currentValue.isNotEmpty()) {
                    currentValue.substring(0, currentValue.length - 1)
                } else {
                    EMPTY_SPACE
                }
            }
            "FlipSign" -> {
                return if (currentValue.isNotEmpty() && currentValue != ZERO) {
                    val currentSign = currentValue[0]
                    if (currentSign == HYPHEN) {
                        currentValue.substring(1)
                    } else {
                        "$HYPHEN$currentValue"
                    }
                } else {
                    EMPTY_SPACE
                }
            }
            "Dot" -> {
                return if (currentValue.contains(DOT)) currentValue else "$currentValue$DOT"
            }
            else -> {
                return if ((currentValue == ZERO) && (key == ZERO)) ZERO else currentValue + key
            }
        }
    }


    companion object {
        private const val EMPTY_SPACE = ""
        private const val ZERO = "0"
        private const val HYPHEN = '-'
        private const val DOT = '.'
    }

}