package tech.bluespace.androidtestnetwork

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.FileOutputStream
import java.net.URL

class MainActivity : AppCompatActivity() {

    private val url = "https://www.bluespace.tech/android-chrome-256x256.png"
    private lateinit var file: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        file = File(applicationContext.dataDir, "download.png")
        button.setOnClickListener { loadImage() }
    }

    private fun loadImage() {
        Thread(Runnable {
            try {
                URL(url).openStream().use { input ->
                    FileOutputStream(file).use { output -> input.copyTo(output) }
                }
                runOnUiThread { showImage() }
            } catch (ex: Throwable) {
                runOnUiThread {
                    AlertDialog.Builder(this)
                        .setMessage(ex.toString())
                        .create()
                        .show()
                }
            }
        }).start()
    }

    private fun showImage() {
        try {
            BitmapFactory.decodeFile(file.absolutePath)?.let {
                imageView.setImageBitmap(it)
            }
        } catch (ex: Throwable) {
            ex.printStackTrace()
        }
    }
}
