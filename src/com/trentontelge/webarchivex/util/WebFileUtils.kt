package com.trentontelge.webarchivex.util

import com.trentontelge.webarchivex.savePath
import org.apache.commons.io.FilenameUtils
import org.apache.commons.io.IOUtils.copy
import java.io.File
import java.io.IOException
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.URL
import jdk.nashorn.internal.runtime.ScriptingFunctions.readLine
import org.apache.commons.io.FileUtils.copyURLToFile
import java.io.InputStreamReader
import java.io.BufferedReader



fun grabPage(url: String){
    val fullUrl: String = if (!url.contains("http://")){
        ("http://" + url)
    } else {
        url
    }
    try {
        val obj = URL(fullUrl)
        print("Connecting to $fullUrl... ")
        val conn = obj.openConnection() as HttpURLConnection
        conn.readTimeout = 5000
        conn.addRequestProperty("Accept-Language", "en-US,en;q=0.8")
        conn.addRequestProperty("User-Agent", "Mozilla Website Archiver")
        conn.addRequestProperty("Referer", "google.com")

        var redirect = false
        print("connected.\nHTTP request sent, awaiting response... ")

        // normally, 3xx is redirect
        val status = conn.responseCode
        print("$status\n")
        if (status != HttpURLConnection.HTTP_OK) {
            if (status == HttpURLConnection.HTTP_MOVED_TEMP
                    || status == HttpURLConnection.HTTP_MOVED_PERM
                    || status == HttpURLConnection.HTTP_SEE_OTHER)
                redirect = true
        }

        if (redirect) {

            // get redirect url from "location" header field
            val newUrl = conn.getHeaderField("Location")
            print("Redirecting... ")
            grabPage(newUrl)

        } else {
            print("Downloading to ${convertToPathArchitecture(fullUrl)}... ")
            copyURLToFile(URL(fullUrl), File(savePath + convertToPathArchitecture(fullUrl)))
            print("complete.\n")
        }

    } catch (e: Exception) {
        e.printStackTrace()
    }


}

fun convertToPathArchitecture(url: String): String{
    val f = url.replace("http://", "")
    return if (URL(url).file == ""){
        f+System.getProperty("file.separator")+"index.html"
    }else {
        f
    }
}