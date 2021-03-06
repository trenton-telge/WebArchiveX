package com.trentontelge.webarchivex

import com.trentontelge.webarchivex.util.grabPage
import java.io.File
import java.util.*
import java.util.regex.Pattern

var rootDomain: String = ""
var savePath: String = ""
var limit: Boolean = false
var internalize = true
val savedPaths = Vector<String>()

fun main(args: Array<String>){
        printCopyright()
        printCommandTree()
        val inScan = System.`in`
        var command = ""

        while (command == ""){
            try {
                command = inScan.bufferedReader().readLine()
                val domainMatcher = Pattern.compile("domain=['\"][\\w.]+['\"]").matcher(command)
                while (domainMatcher.find()) {
                    rootDomain = command.substring(domainMatcher.start() + 8, domainMatcher.end() - 1)
                }
                val savePathMatcher = Pattern.compile("save=['\"][\\w:/\\\\.()!@#\$%^\\-+=*~`&\\s]+['\"]").matcher(command)
                while (savePathMatcher.find()) {
                    savePath = command.substring(savePathMatcher.start() + 6, savePathMatcher.end() - 1)
                    File(savePath).mkdirs()
                    if(!savePath.endsWith("\\")){
                        savePath += "\\"
                    }
                }
                val limitMatcher = Pattern.compile("limit=[yYnN]").matcher(command)
                when (limitMatcher.find()) {
                    command.substring(limitMatcher.start() + 6, limitMatcher.end()).contains("y", true) -> {
                        limit = true
                    }
                    command.substring(limitMatcher.start() + 6, limitMatcher.end()).contains("n", true) -> {
                        limit = false
                    }
                }
                val internalMatcher = Pattern.compile("internalize=[yYnN]").matcher(command)
                when (internalMatcher.find()) {
                    command.substring(internalMatcher.start() + 12, internalMatcher.end()).contains("y", true) -> {
                        internalize = true
                    }
                    command.substring(internalMatcher.start() + 12, internalMatcher.end()).contains("n", true) -> {
                        internalize = false
                    }
                }
            } catch (e: IllegalStateException){
                println("You have entered a malformed command. Try Again.")
                printCommandTree()
                command = ""
            }
        }
    grabPage(rootDomain)
}

fun printCopyright(){
    println("WebArchiveX  Copyright (C) 2017  Trenton Telge\n" +
            "    This program comes with ABSOLUTELY NO WARRANTY.\n" +
            "    This is free software, and you are welcome to redistribute it\n" +
            "    under certain conditions.")
}

fun printCommandTree(){
    /*
    * Domain defines starting point
    * Save defines WAX save location
    * Limit defines whether to limit to primary domain
    * Internalize defines whether to convert links in webpage to relative links
    * */
    println("Usage: wax domain={domain} save={save path} limit={Y/N} internalize={Y/N}")
}