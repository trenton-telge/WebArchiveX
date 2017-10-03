package com.trentontelge.webarchivex.util

import org.apache.commons.io.FileUtils.readFileToString
import java.io.File
import java.nio.charset.Charset
import java.util.*
import java.util.regex.Pattern

class TempWebArchivePage(private val remoteLocation: String, private val tempFile: File){
    private var dependencies = Vector<String>()

    private fun parseLinksForDependancies(){
        dependencies = Vector()
        val contents = readFileToString(tempFile, Charset.defaultCharset())
        val urlMatcher = Pattern.compile("https?://(www\\.)?[-a-zA-Z0-9@:%._+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_+.~#?&/=]*)").matcher(contents)
        while (urlMatcher.find()){
            dependencies.add(urlMatcher.group())
        }
    }

    private fun grabDependancies(){
        for (link in dependencies){
            when {
                link.endsWith("htm") || link.endsWith("html") || link.endsWith("php") || link.endsWith("css") || link.endsWith("js") -> {
                    grabPageToTemp(link).processPage()
                }
                link.endsWith("/") -> {
                    grabPageToTemp(link + "index.php").processPage()
                    grabPageToTemp(link + "index.html").processPage()
                    grabPageToTemp(link + "index.htm").processPage()
                }
                else -> {
                    grabResource(link)
                }
            }
        }
    }

    fun processPage(){
        parseLinksForDependancies()
        grabDependancies()
    }
}