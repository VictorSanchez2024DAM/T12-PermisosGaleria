package net.iesseveroochoa.victorsanchez.tareasv01.utils

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.core.net.toUri
import net.iesseveroochoa.victorsanchez.tareasv01.R
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Locale

val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"

/**
 * Crea un nombre de fichero con la fecha y hora actual
 */
fun nombreArchivo(): String {
    val timestamp = SimpleDateFormat(FILENAME_FORMAT, Locale.US)
        .format(System.currentTimeMillis())
    return "$timestamp.png"
}

/**
 * guarda una imagen en la galería de la aplicación y devuelve su uri
 */
suspend fun saveBitmapImage(context: Context, bitmap: Bitmap): Uri? {
    val TAG = context.getString(R.string.app_name)
    val timestamp = SimpleDateFormat(FILENAME_FORMAT, Locale.US)
        .format(System.currentTimeMillis())
    var uri: Uri? = null


    //Tell the media scanner about the new file so that it is immediately available to the user.
    val values = ContentValues()
    values.put(MediaStore.Images.Media.MIME_TYPE, "image/png")
    values.put(MediaStore.Images.Media.DATE_ADDED, timestamp)
    //mayor o igual a version 29
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        values.put(MediaStore.Images.Media.DATE_TAKEN, timestamp)
        values.put(
            MediaStore.Images.Media.RELATIVE_PATH,
            "Pictures/" + context.getString(R.string.app_name)
        )
        values.put(MediaStore.Images.Media.IS_PENDING, true)

        uri = context.contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            values
        )
        if (uri != null) {
            try {
                val outputStream = context.contentResolver.openOutputStream(uri)
                if (outputStream != null) {
                    try {
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                        outputStream.close()
                    } catch (e: Exception) {
                        Log.e(TAG, "saveBitmapImage: ", e)
                    }
                }
                values.put(MediaStore.Images.Media.IS_PENDING, false)
                context.contentResolver.update(uri, values, null, null)

                // Toast.makeText(requireContext(), "Saved...", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Log.e(TAG, "saveBitmapImage: ", e)
            }
        }
    } else {//no me funciona bien en versiones inferiores a la 29(Android 10)
        val imageFileFolder = File(
            Environment.getExternalStorageDirectory()
                .toString() + '/' + context.getString(R.string.app_name)
        )
        if (!imageFileFolder.exists()) {
            imageFileFolder.mkdirs()
        }
        val mImageName = "$timestamp.png"
        val imageFile = File(imageFileFolder, mImageName)
        try {
            val outputStream: OutputStream = FileOutputStream(imageFile)
            try {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                outputStream.close()
            } catch (e: Exception) {
                Log.e(TAG, "saveBitmapImage: ", e)
            }
            values.put(MediaStore.Images.Media.DATA, imageFile.absolutePath)

            context.contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                values
            )
            uri = imageFile.toUri()
            // Toast.makeText(requireContext(), "Saved...", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Log.e(TAG, "saveBitmapImage: ", e)
        }
    }
    return uri
}

/**
 * Carga una imagen desde una uri.
 * @return null si no se ha podido cargar la imagen. Devuelve la imagen
 */
fun loadFromUri(context: Context, photoUri: Uri?): Bitmap? {
    var image: Bitmap? = null
    try {
        // check version of Android on device
        image = if (Build.VERSION.SDK_INT > 27) {
            // on newer versions of Android, use the new decodeBitmap method
            val source = ImageDecoder.createSource(
                context.contentResolver,
                photoUri!!
            )
            ImageDecoder.decodeBitmap(source)
        } else {
            // support older versions of Android by using getBitmap
            MediaStore.Images.Media.getBitmap(context.contentResolver, photoUri)
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return image
}
/**
 * permite crear una uri para guardar la imagen
 * */
fun creaUri(context: Context): Uri? {
    val resolver = context.contentResolver
    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, nombreArchivo())
        put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
        put(MediaStore.Images.ImageColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
    }
    return resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
}
