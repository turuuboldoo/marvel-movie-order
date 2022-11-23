package mn.turbo.marvel.presenter.video

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import mn.turbo.marvel.databinding.ActivityTrailerBinding

@AndroidEntryPoint
class TrailerActivity : AppCompatActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityTrailerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val videoUrl = intent.extras?.let {
            TrailerActivityArgs.fromBundle(it).trailerUrl ?: ""
        }

        if (videoUrl != null) {
            binding.webView.apply {
                webChromeClient = WebChromeClient()
                settings.apply {
                    cacheMode = WebSettings.LOAD_NO_CACHE
                    javaScriptEnabled = true
                    domStorageEnabled = true
                }
                loadUrl(videoUrl)
            }
        }
    }
}
