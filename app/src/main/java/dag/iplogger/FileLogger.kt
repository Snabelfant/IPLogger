package dag.iplogger

import android.content.Context

import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar


class FileLogger(val logFile : File){
    private fun now(): String {
        val format = SimpleDateFormat("yyyy.MM.dd HH:mm:ss")
        return format.format(Calendar.getInstance().time)
    }

    @Throws(IOException::class)
    fun log(s: String) {
        var log: FileWriter? = null
        try {
            log = FileWriter(logFile, true)
            log.append(now() + ": " + s + "\n")
        } catch (e: IOException) {
            throw RuntimeException("skriv: ", e)
        } finally {
            log?.close()
        }

    }

 }
