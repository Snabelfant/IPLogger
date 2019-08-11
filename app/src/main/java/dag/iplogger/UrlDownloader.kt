package dag.podkast.util

import java.io.*
import java.nio.charset.StandardCharsets

object UrlDownloader {
    private const val BUFFER_SIZE = 8192

    @Throws(IOException::class)
    fun download(url: String) : String {
        val length = UrlStream.getLength(url)
        val inputStream = UrlStream.getInputStream(url)
        val sink = ByteArrayOutputStream()
        copy(inputStream, sink)
        return sink.toString(StandardCharsets.UTF_8.toString())
    }

    @Throws(IOException::class)
    private fun copy(source: InputStream, sink: OutputStream): Int {
        var nread = 0
        val buf = ByteArray(BUFFER_SIZE)
        while (true) {
            val n = source.read(buf)
            if (n == -1) {
                break
            }

            sink.write(buf, 0, n)
            nread += n
        }

        sink.close()
        return nread
    }
}
