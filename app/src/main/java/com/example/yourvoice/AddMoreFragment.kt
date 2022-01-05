package com.example.yourvoice

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_add_more.*
import java.util.*


class AddMoreFragment : Fragment(R.layout.fragment_add_more){
    private lateinit var tts : TextToSpeech
    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()


        tts = TextToSpeech(activity?.applicationContext) {
            val status = 0
            if (status == TextToSpeech.SUCCESS) {
                val result = tts.setLanguage(Locale.ENGLISH)
                if (status == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("TTS", "Language not supported")
                }
            }


            btnSpeak.setOnClickListener {
                speak()

            }
        }
    }

        private fun speak() {
            val text = txtspeech.text.toString()
            tts.speak(text,TextToSpeech.QUEUE_FLUSH,null,null)
        }

        override fun onDestroy() {
            tts.stop()
            tts.shutdown()
            super.onDestroy()
        }





 }


