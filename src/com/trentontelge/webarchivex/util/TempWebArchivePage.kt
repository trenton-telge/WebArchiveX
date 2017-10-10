package com.trentontelge.webarchivex.util

import org.apache.commons.io.FileUtils.readFileToString
import java.io.File
import java.nio.charset.Charset
import java.util.*
import java.util.regex.Pattern
import jdk.nashorn.tools.ShellFunctions.input
import org.jsoup.Jsoup



class TempWebArchivePage(private val tempFile: File, private val uri: String){

    private fun grabDependancies(){
        val doc = Jsoup.parse(tempFile, "UTF-8", uri)
        val links = doc.select("a[href]")
        val media = doc.select("[src]")
        val imports = doc.select("link[href]")
    }

    fun processPage(){
        grabDependancies()
    }
}