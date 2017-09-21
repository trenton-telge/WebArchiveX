package com.trentontelge.webarchivex

fun main(args: Array<String>){
    if (args.isNotEmpty()){
        //TODO parse args array
    } else {
        printCopyright()
        printCommandTree()
        val inScan = System.`in`
        var command = ""
        while (command ==""){
            command = inScan.bufferedReader().readLine()
            //TODO parse command string
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
    println("Usage: wax domain={domain} save={save path} limit={Y/N}")
}