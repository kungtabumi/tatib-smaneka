package com.example

import com.example.data.ai.TatibAiEngine
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {
    private val engine = TatibAiEngine()

    @Test
    fun testTypoSeragamSenin() = runBlocking {
        val answer = engine.askTatib("sragam senen pke apa")
        assertTrue(answer.isGrounded)
        assertTrue(answer.text.contains("Senin", ignoreCase = true))
        assertTrue(answer.text.contains("putih", ignoreCase = true))
    }

    @Test
    fun testTypoRambut221() = runBlocking {
        val answer = engine.askTatib("rmbut cwok brapa potongannya 221")
        assertTrue(answer.isGrounded)
        assertTrue(answer.text.contains("2-2-1", ignoreCase = true) || answer.text.contains("2 cm", ignoreCase = true))
    }

    @Test
    fun testTypoKeterlambatan() = runBlocking {
        val answer = engine.askTatib("klo terlmabat gmna sanksinya")
        assertTrue(answer.isGrounded)
        assertTrue(answer.text.contains("Terlambat", ignoreCase = true))
    }

    @Test
    fun testTypoSepatuDoff() = runBlocking {
        val answer = engine.askTatib("blh pke spatu glosi ga")
        assertTrue(answer.isGrounded)
        assertTrue(answer.text.contains("Sepatu", ignoreCase = true) || answer.text.contains("hitam", ignoreCase = true))
    }

    @Test
    fun testTypoVapeRokok() = runBlocking {
        val answer = engine.askTatib("hukuman bwa vpe di sklhan")
        assertTrue(answer.isGrounded)
        assertTrue(answer.text.contains("Vape", ignoreCase = true) || answer.text.contains("Rokok", ignoreCase = true))
    }
}

