package com.trentontelge.webarchivex.util

import com.trentontelge.webarchivex.savePath
import org.apache.commons.io.FilenameUtils
import org.apache.commons.io.IOUtils.copy
import java.io.*
import java.net.URL

fun grabPageToTemp(url: String): TempWebArchivePage{
    println("PAGE - $url")
    val root = File(savePath)
    if (root.isFile){
        throw IOException()
    } else if (!root.exists()){
        root.mkdirs()
    }
    copy(URL("http://" + url).openStream(), File(root.toString() + (System.getProperty("file.separator")) + convertToPathArchitecture(url)).outputStream())
    return TempWebArchivePage(url, File(root.toString() + (System.getProperty("file.separator")) + convertToPathArchitecture(url)))
}

fun grabResource(url: String){
    val root = File(savePath)
    if (root.isFile){
        throw IOException()
    } else if (!root.exists()){
        root.mkdirs()
    }
    copy(URL("http://" + url).openStream(), File(root.toString() + (System.getProperty("file.separator")) + convertToPathArchitecture(url)).outputStream())
}

fun convertToPathArchitecture(url: String): String{
    url.replace(Regex("http(s)?://"), "")
    return url.substring(0, url.lastIndexOf("/")) + FilenameUtils.getName(url)
}