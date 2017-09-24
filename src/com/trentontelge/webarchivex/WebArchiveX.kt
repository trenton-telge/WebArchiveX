package com.trentontelge.webarchivex

import java.util.regex.Pattern

fun main(args: Array<String>){
        printCopyright()
        printCommandTree()
        val inScan = System.`in`
        var command = ""
        var rootDomain: String
        var savePath: String
        var limit: Boolean
        var internalize = true
        while (command == ""){
            command = inScan.bufferedReader().readLine()
            val domainMatcher = Pattern.compile("domain=['\"][\\w.]+['\"]").matcher(command)
            while (domainMatcher.find()){
                rootDomain = command.substring(domainMatcher.start() + 8, domainMatcher.end() - 1)
                println(rootDomain) //DEBUG
            }
            val savePathMatcher = Pattern.compile("save=['\"][\\w:/\\\\.()!@#\$%^\\-+=*~`&\\s]+['\"]").matcher(command)
            while (savePathMatcher.find()){
                savePath = command.substring(savePathMatcher.start() + 6, savePathMatcher.end() - 1)
                println(savePath) //DEBUG
            }
            val limitMatcher = Pattern.compile("limit=[yYnN]").matcher(command)
            when(limitMatcher.find()){
                command.substring(limitMatcher.start() + 6, limitMatcher.end()).contains("y", true) -> {
                    limit = true
                }
                command.substring(limitMatcher.start() + 6, limitMatcher.end()).contains("n", true) -> {
                    limit = false
                }
            }
            val internalMatcher = Pattern.compile("internalize=[yYnN]").matcher(command)
            when(internalMatcher.find()){
                command.substring(internalMatcher.start() + 12, internalMatcher.end()).contains("y", true) -> {
                    internalize = true
                }
                command.substring(internalMatcher.start() + 12, internalMatcher.end()).contains("n", true) -> {
                    internalize = false
                }
            }
        }
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