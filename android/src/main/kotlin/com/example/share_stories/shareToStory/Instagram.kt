package com.example.share_stories.shareToStory

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.webkit.MimeTypeMap
import androidx.core.content.FileProvider
import java.io.*

class Instagram(private val context: Context) {
    var activity: Activity? = null

    fun share(
        backgroundUri: String?,
        stickerUri: String?,
        topColor: String?,
        bottomColor: String?,
    ): String {
        val background: Uri? = if (backgroundUri != null) Uri.parse(backgroundUri) else null
        val sticker: Uri? = if (stickerUri != null) Uri.parse(stickerUri) else null

        shareToStory(background, sticker, topColor, bottomColor)
        return "Tests: $background / $sticker / $topColor / $bottomColor"
    }

    private fun shareToStory(
        backgroundUri: Uri?,
        stickerUri: Uri?,
        topColor: String?,
        bottomColor: String?,
    ) {
        if (backgroundUri == null && stickerUri == null) {
            throw IllegalArgumentException("Background Asset Uri or Sticker Asset Uri must not be null")
        }

        val intent = Intent("com.instagram.share.ADD_TO_STORY")
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        intent.putExtra("source_application", context.packageName)

        if (backgroundUri != null) {
            val uri = getFileUri(backgroundUri.path.toString())
            context.grantUriPermission(
                "com.instagram.android",
                uri,
                Intent.FLAG_GRANT_READ_URI_PERMISSION,
            )
            val ext = MimeTypeMap.getFileExtensionFromUrl(uri.path)
            val mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(ext)
            intent.setDataAndType(uri, mimeType ?: "image/jpeg")
        }

        if (stickerUri != null) {
            if (backgroundUri == null) {
                intent.type = "image/jpeg"
            }
            val uri = getFileUri(stickerUri.path.toString())
            intent.putExtra("interactive_asset_uri", uri)
            context.grantUriPermission(
                "com.instagram.android",
                uri,
                Intent.FLAG_GRANT_READ_URI_PERMISSION,
            )
        }

        if (topColor != null) {
            intent.putExtra("top_background_color", topColor)
        }

        if (bottomColor != null) {
            intent.putExtra("bottom_background_color", bottomColor)
        }

        post(intent)
    }

    private fun getFileUri(path: String): Uri {
        var file = File(path)
        if (!fileIsOnExternal(file)) {
            file = copyToExternalFolder(file)
        }
        return FileProvider.getUriForFile(
            context,
            context.packageName + ".flutter.social_share",
            file,
        )
    }

    private fun fileIsOnExternal(file: File): Boolean {
        return try {
            val filePath = file.canonicalPath
            val externalDir = context.getExternalFilesDir(null)
            externalDir != null && filePath.startsWith(externalDir.canonicalPath)
        } catch (e: IOException) {
            false
        }
    }

    @Throws(IOException::class)
    private fun copyToExternalFolder(file: File): File {
        val folder: File = getExternalFolder()
        if (!folder.exists()) {
            folder.mkdirs()
        }
        val newFile = File(folder, file.name)
        copy(file, newFile)
        return newFile
    }

    private fun getExternalFolder(): File {
        return File(context.externalCacheDir, "share_to_stories")
    }

    @Throws(IOException::class)
    private fun copy(src: File, dst: File) {
        val stream: InputStream = FileInputStream(src)
        stream.use { input ->
            val out: OutputStream = FileOutputStream(dst)
            out.use { output ->
                // Transfer bytes from in to out
                val buf = ByteArray(1024)
                var len: Int
                while (input.read(buf).also { len = it } > 0) {
                    output.write(buf, 0, len)
                }
            }
        }
    }

    private fun post(intent: Intent): Boolean {
        try {
            activity?.startActivity(intent)
        } catch (e: Exception) {
            Log.e(TAG, "error", e)
            return false
        }
        return true
    }

    private companion object {
        private val TAG = Instagram::class.java.name
    }

}