package com.example.yourvoice

import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import androidx.preference.PreferenceManager
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*


class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var tts: TextToSpeech
    private lateinit var navController:NavController
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tts = TextToSpeech(activity?.applicationContext){
            val status = 0
            if (status == TextToSpeech.SUCCESS) {
                val result = tts.setLanguage(Locale.ENGLISH)
                if (status == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("TTS", "Language not supported")
                }

            }

        }

        navController = findNavController()
        val navController = findNavController()

        addButton.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToAddMoreFragment2()
            navController.navigate(action)
        }
        btn1.setOnClickListener {

            tts.speak(btn1.text.toString(),TextToSpeech.QUEUE_FLUSH,null,null)
            btn1.text.toString()

                }

        btn2.setOnClickListener {
            tts.speak(btn2.text.toString(),TextToSpeech.QUEUE_FLUSH,null,null)
        }
        btn3.setOnClickListener {
            tts.speak(btn3.text.toString(),TextToSpeech.QUEUE_FLUSH,null,null)
        }
        btn4.setOnClickListener {
            tts.speak(btn4.text.toString(),TextToSpeech.QUEUE_FLUSH,null,null)
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        return when (item.itemId) {
            R.id.englishFragment -> {
                tts.language = Locale.ENGLISH
                return true
            }
            R.id.languagesFragment -> {
                tts.language = Locale("ur")
                return true
            }
            R.id.spanishFragment -> {
                tts.language = Locale("es")
                return true
            }
            R.id.aboutus -> {
                val action = MainNavGraphDirections.actionGlobalAboutusFragment()
                navController.navigate(action)
                return true
            }
            else -> {

                item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
            }
        }
    }


    }