package com.trentontelge.webarchivex.util

import com.trentontelge.webarchivex.savePath
import org.apache.commons.io.FilenameUtils
import org.apache.commons.io.IOUtils.copy
import java.io.*
import java.net.ConnectException
import java.net.URI
import java.net.URL

fun grabPageToTemp(url: String): TempWebArchivePage? {
    println("PAGE - $url")
    val root = File(savePath)
    if (root.isFile){
        throw IOException()
    } else if (!root.exists()){
        root.mkdirs()
    }
    try {
        File(root.toString() + (System.getProperty("file.separator")) + convertToPathArchitecture(url)).parentFile.mkdirs()
        File(root.toString() + (System.getProperty("file.separator")) + convertToPathArchitecture(url)).createNewFile()
        copy(URL("http://" + url).openStream(), File(root.toString() + (System.getProperty("file.separator")) + convertToPathArchitecture(url)).outputStream())
        return TempWebArchivePage(File(root.toString() + (System.getProperty("file.separator")) + convertToPathArchitecture(url)), URL(URL(url).protocol, URL(url).host, URL(url).port,File(URL(url).path).parent.toString()).toString())
    } catch (e: ConnectException){
        println("INCORRECT ADDRESS")
        e.printStackTrace()
        File(root.toString() + (System.getProperty("file.separator")) + convertToPathArchitecture(url)).delete()
    } catch (e: IOException) {
        println("FILE COULD NOT BE CREATED")
    }
    return null

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
    return url.substring(0, url.lastIndexOf("/") + 1) + FilenameUtils.getName(url)
}