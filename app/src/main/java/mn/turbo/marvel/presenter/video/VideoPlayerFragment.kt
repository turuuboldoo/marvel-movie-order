package mn.turbo.marvel.presenter.video

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import mn.turbo.marvel.databinding.FragmentVideoPlayerBinding

@AndroidEntryPoint
class VideoPlayerFragment : Fragment() {

    private var _binding: FragmentVideoPlayerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVideoPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val videoUrl = arguments?.let { VideoPlayerFragmentArgs.fromBundle(it).trailerUrl }

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
