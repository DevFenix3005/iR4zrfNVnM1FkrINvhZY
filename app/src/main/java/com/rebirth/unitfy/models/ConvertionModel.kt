package com.rebirth.unitfy.models

import com.rebirth.unitfy.domain.daos.ConvertioUnitDao
import com.rebirth.unitfy.domain.daos.MutationDao
import com.rebirth.unitfy.domain.daos.UnitClassificationDao
import org.mariuszgromada.math.mxparser.Argument
import org.mariuszgromada.math.mxparser.Expression
import org.mariuszgromada.math.mxparser.Function
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConvertionModel @Inject constructor(
    unitClassificationDao: UnitClassificationDao,
    convertionUnitDao: ConvertioUnitDao,
    mutationDao: MutationDao
) : BaseModel(unitClassificationDao, convertionUnitDao, mutationDao) {

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