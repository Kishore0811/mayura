package com.primemover.mayura

import com.primemover.mayura.emiCalculator.EmiCalculatorActivity
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class EmiCalculatorActivityTest {
    private val emiCalculator = EmiCalculatorActivity()

    @Test
    fun `Principal should return correct value`() {
        val actualPrincipal = emiCalculator.calculatePrincipal("1200", "12")
        assertEquals(100, actualPrincipal)
    }

    @Test
    fun `Principal should return wrong value`() {
        val actualPrincipal = emiCalculator.calculatePrincipal("1200", "12")
        assertNotEquals(90, actualPrincipal)
    }

    @Test
    fun `Interest should return correct value`() {
        val actualInterest = emiCalculator.calculateInterest("2000", "100")
        val expectedInterest = 2000.toFloat()
        assertEquals(expectedInterest, actualInterest)
    }

    @Test
    fun `Interest should return wrong value`() {
        val actualInterest = emiCalculator.calculateInterest("2000", "100")
        val expectedInterest = 200.toFloat()
        assertNotEquals(expectedInterest, actualInterest)
    }

    @Test
    fun `Total should return correct value`() {
        val actualTotal = emiCalculator.calculateTotal("2000", "2000")
        assertEquals(4000, actualTotal)
    }
    @Test
    fun `Total should return wrong value`() {
        val actualTotal = emiCalculator.calculateTotal("2000", "2000")
        assertNotEquals(3000, actualTotal)
    }
}