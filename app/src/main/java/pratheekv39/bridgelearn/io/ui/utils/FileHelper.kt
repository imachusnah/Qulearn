package pratheekv39.bridgelearn.io.ui.utils

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

object FileHelper {

    private const val PREF_NAME = "uploaded_prefs"
    private const val KEY_PATH = "uploaded_file_path"

    fun saveFile(context: Context, uri: Uri): String {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        val uploadDir = File(context.filesDir, "uploads")

        if (!uploadDir.exists()) uploadDir.mkdirs()

        val file = File(uploadDir, "uploaded_${System.currentTimeMillis()}.pdf")
        val outputStream = FileOutputStream(file)

        inputStream?.copyTo(outputStream)

        inputStream?.close()
        outputStream.close()

        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_PATH, file.absolutePath)
            .apply()

        return file.absolutePath
    }

    fun getSavedFilePath(context: Context): String? {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .getString(KEY_PATH, null)
    }

    fun deleteFile(context: Context): Boolean {
        val path = getSavedFilePath(context) ?: return false
        val file = File(path)
        val deleted = file.delete()

        if (deleted) {
            context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
                .edit().remove(KEY_PATH).apply()
        }

        return deleted
    }
}
