package com.Adisapp.talkingstopwatch

import android.content.Intent

import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.*
import android.widget.*
import java.util.*


class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var running: Boolean = false
    private var resume: Boolean = true
    private var paused: Long =0
    private var startingtimeelapsed: Long = 0
    private var tts :TextToSpeech?= null
    private var speak:Boolean?= null
    private var freq = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tts = TextToSpeech(this,this)

        val menu= findViewById<ImageView>(R.id.menu)
        menu.setOnClickListener(){
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            resume = false
        }
        freq = intent.getIntExtra("frequency",5)


    }


    fun start(view: View) {
        if(findViewById<TextView>(R.id.start).text=="start")
            running = true
        else
            running=false

        if (running==true) {
            findViewById<TextView>(R.id.start).text="stop"
            var meter = findViewById<Chronometer>(R.id.meter)
            meter.base = SystemClock.elapsedRealtime()-paused
            startingtimeelapsed = meter.base
            meter.start()
        }
        else{
            findViewById<TextView>(R.id.start).text="start"
            paused=SystemClock.elapsedRealtime()-startingtimeelapsed
            findViewById<Chronometer>(R.id.meter).stop()
        }

    }

    fun reset(view: View) {
        findViewById<TextView>(R.id.start).text="start"
        findViewById<Chronometer>(R.id.meter).base=SystemClock.elapsedRealtime()
        startingtimeelapsed=SystemClock.elapsedRealtime()
        running=false
        findViewById<Chronometer>(R.id.meter).stop()
        paused=0

    }

    fun listen(view: View) {

        if(findViewById<Switch>(R.id.switch1).isChecked){
            speak= true
            val mainHandler = Handler(Looper.getMainLooper())

            mainHandler.post(object : Runnable {
                override fun run() {
                    if(speak==true ) {

                        speakOut()
                        mainHandler.postDelayed(this, 1000)
                    }
                }
            })
        }
        else
            speak=false
    }

    override fun onInit(p0: Int){

        if (p0 == TextToSpeech.SUCCESS) {
            // set US English as language for tts
            val result = tts!!.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this,"The Language specified is not supported!",Toast.LENGTH_SHORT).show()
            }

        } else {
            Toast.makeText(this, "Initilization Failed!",Toast.LENGTH_SHORT).show()
        }

    }

    private fun speakOut() {

        var seconds = (SystemClock.elapsedRealtime() - startingtimeelapsed)/1000
        Log.i("seconds",seconds.toString())
        var minutes = seconds/60
        var secs = seconds-(minutes*60)
        Log.i("minutes",minutes.toString())
        var hours = minutes/60
        var mins = minutes -(hours*60)
        var text = if(seconds.toInt()!=0) seconds.toString()+"seconds" else ""
        if(minutes>=1)
            text = minutes.toString()+"minutes"+ if(secs.toInt()!=0) secs.toString()+"seconds" else ""
        if(hours >=1)
            text = hours.toString()+"Hours"+mins.toString()+"minutes"+if(secs.toInt()!=0) secs.toString()+"seconds" else ""

        if(running==true && seconds.toInt()%freq==0 && resume==true)
            tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")

    }

    public override fun onDestroy() {
        // Shutdown TTS
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        resume = true
    }

}