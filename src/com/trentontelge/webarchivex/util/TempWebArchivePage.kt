package com.trentontelge.webarchivex.util

import com.trentontelge.webarchivex.limit
import com.trentontelge.webarchivex.rootDomain
import java.io.File
import org.jsoup.Jsoup
import java.net.URL


class TempWebArchivePage(private val tempFile: File, private val uri: String){

    private fun grabDependencies(){
        val doc = Jsoup.parse(tempFile, "UTF-8", uri)
        val links = doc.select("a[href]")
        val media = doc.select("[src]")
        val imports = doc.select("link[href]")
        for (link in links){
            if (!rootDomain.contains(URL(link.attr("abs:href")).host) && limit) {
                println("LINK SKIPPED - DOMAIN LIMITED")
            } else {
                when {
                    link.attr("abs:href").endsWith(".htm") ||
                            link.attr("abs:href").endsWith(".html") ||
                            link.attr("abs:href").endsWith(".php") ||
                            link.attr("abs:href").endsWith(".css") ||
                            link.attr("abs:href").endsWith(".js") -> {
                        grabPageToTemp(link.attr("abs:href"))?.processPage()
                    }
                    link.attr("abs:href").endsWith("/") -> {
                        grabPageToTemp(link.attr("abs:href") + "index.php")?.processPage()
                        grabPageToTemp(link.attr("abs:href") + "index.html")?.processPage()
                        grabPageToTemp(link.attr("abs:href") + "index.htm")?.processPage()
                    }
                    else -> {
                        grabResource(link.attr("abs:href"))
                    }
                }
            }
        }
        for (link in media){
            if (!rootDomain.contains(URL(link.attr("abs:href")).host) && limit) {
                println("LINK SKIPPED - DOMAIN LIMITED")
            } else {
                when {
                    link.attr("abs:href").endsWith(".htm") ||
                            link.attr("abs:href").endsWith(".html") ||
                            link.attr("abs:href").endsWith(".php") ||
                            link.attr("abs:href").endsWith(".css") ||
                            link.attr("abs:href").endsWith(".js") -> {
                        grabPageToTemp(link.attr("abs:href"))?.processPage()
                    }
                    link.attr("abs:href").endsWith("/") -> {
                        grabPageToTemp(link.attr("abs:href") + "index.php")?.processPage()
                        grabPageToTemp(link.attr("abs:href") + "index.html")?.processPage()
                        grabPageToTemp(link.attr("abs:href") + "index.htm")?.processPage()
                    }
                    else -> {
                        grabResource(link.attr("abs:href"))
                    }
                }
            }
        }
        for (link in imports){
            if (!rootDomain.contains(URL(link.attr("abs:href")).host) && limit) {
                println("LINK SKIPPED - DOMAIN LIMITED")
            } else {
                when {
                    link.attr("abs:href").endsWith(".htm") ||
                            link.attr("abs:href").endsWith(".html") ||
                            link.attr("abs:href").endsWith(".php") ||
                            link.attr("abs:href").endsWith(".css") ||
                            link.attr("abs:href").endsWith(".js") -> {
                        grabPageToTemp(link.attr("abs:href"))?.processPage()
                    }
                    link.attr("abs:href").endsWith("/") -> {
                        grabPageToTemp(link.attr("abs:href") + "index.php")?.processPage()
                        grabPageToTemp(link.attr("abs:href") + "index.html")?.processPage()
                        grabPageToTemp(link.attr("abs:href") + "index.htm")?.processPage()
                    }
                    else -> {
                        grabResource(link.attr("abs:href"))
                    }
                }
            }
        }
    }

    fun processPage(){
        grabDependencies()
    }
}