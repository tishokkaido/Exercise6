package com.example.exercise6

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private val progressDialog: ProgressDialog = ProgressDialog.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 元画像
        val original = BitmapFactory.decodeResource(resources, R.drawable.cat)

        // 表示中の画像
        val visibleImage = Bitmap.createBitmap(original)

        val postButton = findViewById<Button>(R.id.button_post)

        // Postボタン押下時の処理
        postButton.setOnClickListener {
            showProgressDialog()
            colorToMonochrome(visibleImage)
        }

        // Resetボタン押下時の処理
        findViewById<Button>(R.id.button_reset).setOnClickListener {
            findViewById<ImageView>(R.id.image).setImageBitmap(original)
            postButton.isEnabled = true
        }
    }

    /**
     * ワーカースレッドでカラー画像をモノクロ変換する。
     *
     * @param srcImg
     */
    private fun colorToMonochrome(srcImg: Bitmap) {
        // handlerの生成
        val handler = Handler(mainLooper)

        // スレッドの作成
        val thread = Thread {

            val dstImg: Bitmap = Bitmap.createBitmap(srcImg.width, srcImg.height, Bitmap.Config.ARGB_8888)

            // 画像の高さ・幅を取得
            val width: Int = dstImg.width
            val height: Int = dstImg.height

            // 画素値を格納する変数
            var rgb: Int
            var mono: Int

            // モノクロ変換
            for (i in 0 until height step 1) {
                for (j in 0 until width step 1) {
                    rgb = srcImg.getPixel(j, i)
                    mono = (Color.red(rgb) * 0.3 + Color.green(rgb) * 0.59 + Color.blue(rgb) * 0.11).toInt()
                    dstImg.setPixel(j, i, Color.rgb(mono, mono, mono))
                }
            }

            handler.post {
                findViewById<ImageView>(R.id.image).setImageBitmap(dstImg)
                findViewById<Button>(R.id.button_post).isEnabled = false
                dismissProgressBar()
            }
        }

        thread.start()
    }

    /**
     * プログレスバーを表示する。
     *
     */
    private fun showProgressDialog() {
        progressDialog.show(supportFragmentManager, TAG)
    }

    /**
     * プログレスバーを非表示にする。
     *
     */
    private fun dismissProgressBar() {
        progressDialog.dismiss()
    }

    companion object {
        const val TAG = "tag"
    }
}