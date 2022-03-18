package com.swtecnn.contentproviderlesson

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.swtecnn.contentproviderlesson.databinding.ActivityAudioListBinding

class AudioListActivity : AppCompatActivity() {
    private var _binding: ActivityAudioListBinding? = null
    private val binding: ActivityAudioListBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAudioListBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}