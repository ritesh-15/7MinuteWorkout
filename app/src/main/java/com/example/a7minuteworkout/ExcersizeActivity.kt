package com.example.a7minuteworkout

import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.a7minuteworkout.databinding.ActivityExcersizeBinding
import org.w3c.dom.Text
import java.lang.Exception
import java.net.URI
import java.util.*
import kotlin.collections.ArrayList

class ExcersizeActivity : AppCompatActivity(),TextToSpeech.OnInitListener {

    private var binding:ActivityExcersizeBinding? = null

    private var restTimer:CountDownTimer? = null

    private var restProgress:Int = 0

    private var exerciseTimer:CountDownTimer? = null

    private var exerciseProgress:Int = 0

    private val REST_TIME:Int  = 10
    private val EXERCISE_TIME:Int  = 30

//    exercise list
    private var exerciseList:ArrayList<ExerciseModal>? = null
    private var currentExercisePosition:Int = -1

    // text to speech
    private  var ttl:TextToSpeech? = null

    // media player
    private var mediaPlayer:MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExcersizeBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarExercise)

        ttl = TextToSpeech(this,this)



        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        exerciseList = Constants.defaultExerciseList()

        binding?.toolbarExercise?.setNavigationOnClickListener{
            // setting up back button
            onBackPressed()
        }

        setupRestView()



    }

    override fun onInit(status: Int) {
        if(status == TextToSpeech.SUCCESS){
            // set up language
            val result = ttl?.setLanguage(Locale.ENGLISH)

            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TTS","The language not supported!")
            }


        }else{
            Log.e("TTS","Init failed!!")
        }
    }

    private fun speakOut(message:String){

        ttl?.speak(message,TextToSpeech.QUEUE_FLUSH,null,"")

    }

    private fun setProgressBarExercise(){
        binding?.progressBarExercise?.progress = exerciseProgress

        exerciseTimer = object:CountDownTimer(30000,1000){
            override fun onTick(p0: Long) {
                exerciseProgress++
                binding?.progressBarExercise?.progress = EXERCISE_TIME - exerciseProgress
                binding?.textViewTimerExercise?.text = (EXERCISE_TIME - exerciseProgress).toString()
            }

            override fun onFinish() {
                if(currentExercisePosition < exerciseList?.size!! - 1){
                    setupRestView()
                }else{
                    Toast.makeText(this@ExcersizeActivity,"Congratulations!",Toast.LENGTH_SHORT).show()
                }
            }

        }.start()

    }

    private fun setupExerciseView(){
        // hidding the rest timer
        binding?.flProgressBar?.visibility = View.INVISIBLE
        binding?.tvExerciseName?.visibility = View.VISIBLE
        binding?.flExcercizeView?.visibility = View.VISIBLE
        binding?.textViewTitle?.visibility = View.INVISIBLE
        binding?.ivImage?.visibility = View.VISIBLE
        binding?.tvupcomingLabel?.visibility = View.INVISIBLE
        binding?.tvUpcomingExerciseName?.visibility = View.INVISIBLE

        if(exerciseTimer != null){
            exerciseTimer?.cancel()
            exerciseProgress=0
        }

        speakOut(exerciseList!![currentExercisePosition].getName())

        // setting up the image
        binding?.ivImage?.setImageResource(exerciseList!![currentExercisePosition].getImage())
        binding?.tvExerciseName?.text = exerciseList!![currentExercisePosition].getName()

        setProgressBarExercise()

    }

    private fun setRestProgressBar(){
        binding?.progressBar?.progress = restProgress

        restTimer = object:CountDownTimer(10000,1000){
            override fun onTick(p0: Long) {
                restProgress++
                binding?.progressBar?.progress = REST_TIME - restProgress
                binding?.textViewTimer?.text = (REST_TIME - restProgress).toString()
            }

            override fun onFinish() {
                currentExercisePosition++;
                setupExerciseView()

            }

        }.start()

    }

    private fun setupRestView(){
        try {
            val soundURI = Uri.parse("android.resource://com.example.a7minuteworkout/"
                    +R.raw.app_src_main_res_raw_press_start)

            mediaPlayer = MediaPlayer.create(applicationContext,soundURI)

            mediaPlayer?.isLooping = false
            mediaPlayer?.start()
        }catch (e:Exception){
            e.printStackTrace()
        }


        binding?.flProgressBar?.visibility = View.VISIBLE
        binding?.tvExerciseName?.visibility = View.INVISIBLE
        binding?.flExcercizeView?.visibility = View.INVISIBLE
        binding?.textViewTitle?.visibility = View.VISIBLE
        binding?.ivImage?.visibility = View.INVISIBLE
        binding?.tvupcomingLabel?.visibility = View.VISIBLE
        binding?.tvUpcomingExerciseName?.visibility = View.VISIBLE


        if(restTimer != null){
            restTimer?.cancel()
            restProgress = 0;

        }

        binding?.tvUpcomingExerciseName?.text = exerciseList!![currentExercisePosition + 1].getName()

        setRestProgressBar()

    }

    override fun onDestroy() {
        super.onDestroy()

        if(restTimer != null){
            restTimer?.cancel()
            restProgress = 0;

        }

        if(mediaPlayer != null){
            mediaPlayer?.stop()
        }

        if(ttl != null){
            ttl?.stop()
            ttl?.shutdown()
        }

        if(exerciseTimer != null){
            exerciseTimer?.cancel()
            exerciseProgress=0
        }

        binding = null
    }

}