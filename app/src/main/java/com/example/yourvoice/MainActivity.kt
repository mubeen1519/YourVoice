package com.example.yourvoice

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.zeugmasolutions.localehelper.LocaleHelper
import com.zeugmasolutions.localehelper.LocaleHelperActivityDelegate
import com.zeugmasolutions.localehelper.LocaleHelperActivityDelegateImpl
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_add_more.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private val localeDelegate: LocaleHelperActivityDelegate = LocaleHelperActivityDelegateImpl()
    private lateinit var navController: NavController
    private lateinit var tts : TextToSpeech
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolBar)
        localeDelegate.onCreate(this)
        findViewById<Toolbar>(R.id.toolBar)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.homeFragment,R.id.settingsFragment,R.id.aboutusFragment,R.id.addMoreFragment2),
            drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        nav_drawer.setupWithNavController(navController)

        tts = TextToSpeech(this) {
            val status = 0
            if (status == TextToSpeech.SUCCESS) {
                val result = tts.setLanguage(Locale.ENGLISH)
                if (status == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("TTS", "Language not supported")
                }

            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        return when (item.itemId) {
            R.id.englishFragment ->{
                updateLocale(Locale.ENGLISH)
                tts.language = Locale.ENGLISH
                return true
            }
            R.id.urduFragment->{
                updateLocale(Locale("ur"))
                tts.language = Locale("ur")
                tts.speak(btn1.text.toString(),TextToSpeech.QUEUE_FLUSH,null,null)
                tts.speak(btn2.text.toString(),TextToSpeech.QUEUE_FLUSH,null,null)
                tts.speak(btn3.text.toString(),TextToSpeech.QUEUE_FLUSH,null,null)
                tts.speak(btn4.text.toString(),TextToSpeech.QUEUE_FLUSH,null,null)

                return true
            }
            R.id.spanishFragment ->{
                updateLocale(Locale("es"))
                tts.language = Locale("es")
                return true
            }
            R.id.aboutus -> {
                val action = MainNavGraphDirections.actionGlobalAboutusFragment()
                navController.navigate(action)
                return true
            }   else -> {

                item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
            }
        }


    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun getDelegate() = localeDelegate.getAppCompatDelegate(super.getDelegate())
    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(localeDelegate.attachBaseContext(newBase))
    }
    override fun onResume() {
        super.onResume()
        localeDelegate.onResumed(this)
    }
    override fun onPause() {
        super.onPause()
        localeDelegate.onPaused()
    }
    override fun createConfigurationContext(overrideConfiguration: Configuration): Context {
        val context = super.createConfigurationContext(overrideConfiguration)
        return LocaleHelper.onAttach(context)
    }
    override fun getApplicationContext(): Context =
        localeDelegate.getApplicationContext(super.getApplicationContext())


    private  fun updateLocale(locale: Locale) {
        localeDelegate.setLocale(this, locale)
    }


}