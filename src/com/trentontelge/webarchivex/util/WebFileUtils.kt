package com.trentontelge.webarchivex.util

import com.trentontelge.webarchivex.limit
import com.trentontelge.webarchivex.rootDomain
import com.trentontelge.webarchivex.savePath
import com.trentontelge.webarchivex.savedPaths
import org.apache.commons.io.FileUtils.copyURLToFile
import org.jsoup.Jsoup
import org.jsoup.UnsupportedMimeTypeException
import java.io.File
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

//wax domain="eventhorizonwebdesign.com" save="C:\Users\darkf\Desktop\ehwd\" limit=n internalize=y

fun grabPage(url: String){
    var fullUrl: String = if (!url.contains("http://")){
        ("http://$url")
    } else {
        url
    }
    fullUrl = fullUrl.replace(Regex("\\?[\\w\\d=.]+"), "")
    if (limit && !fullUrl.contains(rootDomain)){ return } //One-liner for the option not to span domains :D
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
            print("complete.")
            parseLinks(fullUrl)
        }

    } catch (e: Exception) {
        e.printStackTrace()
    }

}

fun parseLinks(url: String){
    val fullUrl: String = if (!url.contains("http://")){
        ("http://$url")
    } else {
        url
    }
    try {
        val doc = Jsoup.connect(fullUrl).get()
        print("\nParsing links in page... ")
        val links = doc.select("a[href]")
        val media = doc.select("[src]")
        val imports = doc.select("link[href]")
        val finalLinks = Vector<String>()
        for (src in media) {
            finalLinks.addElement(src.attr("abs:src"))
        }
        for (link in imports) {
            finalLinks.addElement(link.attr("abs:href"))
        }
        for (link in links) {
            finalLinks.addElement(link.attr("abs:href"))
        }
        print("done.\n")
        for (link in finalLinks) {
            val linkf = if (link.contains("?")){
                link.substring(0, link.indexOf("?"))
            } else {
                link
            }
            println(savedPaths)
            println(linkf)
            if(!savedPaths.contains(linkf)) {
                savedPaths.addElement(linkf)
                grabPage(linkf)
            }
        }
    } catch (e: UnsupportedMimeTypeException){
        println("\nResource saved.")
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