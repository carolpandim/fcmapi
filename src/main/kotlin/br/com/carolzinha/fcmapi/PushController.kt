package br.com.carolzinha.fcmapi

import org.json.JSONObject
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL


@RestController
@RequestMapping()
class PushController {

    val userDeviceID = "d-tuz72uCf8:APA91bHVs7oRSg15bSNn9N68z3UrKHD_xxO73F13I85WnZf8PrSNkmLHG_cI_411SCu6AlH-irjXd9dOZtmlGUPUxt-bdQiuKXIfZP47FdJHZtqBWP1iL90PKRRzNymX0r8O1zttOCKd"
    @PostMapping("/push")
    fun list(): String {
        pushFCMNotification(userDeviceID)
        return "OK"
    }

    // Method to send Notifications from server to client end.

    val AUTH_KEY_FCM = "AIzaSyDSpDr0BToUobYX8x5nOgOB0J-D8KrJLOw"
    val API_URL_FCM = "https://fcm.googleapis.com/fcm/send"

// userDeviceIdKey is the device id you will query from your database

    @Throws(Exception::class)
    fun pushFCMNotification(userDeviceIdKey: String) {

        val authKey = AUTH_KEY_FCM // You FCM AUTH key
        val FMCurl = API_URL_FCM

        val url = URL(FMCurl)
        val conn = url.openConnection() as HttpURLConnection

        conn.useCaches = false
        conn.doInput = true
        conn.doOutput = true

        conn.requestMethod = "POST"
        conn.setRequestProperty("Authorization", "key=$authKey")
        conn.setRequestProperty("Content-Type", "application/json")

        val data = JSONObject()
        data.put("id", "123")
        data.put("outroparam", "teste")


        val json = JSONObject()
        json.put("to", userDeviceIdKey.trim { it <= ' ' })
        val info = JSONObject()
        info.put("title", "Notificatoin Title") // Notification title
        info.put("body", "Hello Test notification") // Notification body
        info.put("click_action", "br.com.carolzinha.detalhe")

        json.put("notification", info)
        json.put("data", data)

        val wr = OutputStreamWriter(conn.outputStream)
        wr.write(json.toString())
        wr.flush()
        conn.inputStream
    }
}
