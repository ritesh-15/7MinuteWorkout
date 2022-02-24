package com.example.a7minuteworkout

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a7minuteworkout.databinding.ActivityExcersizeBinding
import com.example.a7minuteworkout.databinding.CustomDialogConfirmationBinding
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class ExcersizeActivity : AppCompatActivity(),TextToSpeech.OnInitListener {

    private var binding:ActivityExcersizeBinding? = null

    private var restTimer:CountDownTimer? = null

    private var restProgress:Int = 0

    private var exerciseTimer:CountDownTimer? = null

    private var exerciseProgress:Int = 0

    private val REST_TIME:Long  = 1
    private val EXERCISE_TIME:Long  = 1

//    exercise list
    private var exerciseList:ArrayList<ExerciseModal>? = null
    private var currentExercisePosition:Int = -1

    // text to speech
    private  var ttl:TextToSpeech? = null

    // media player
    private var mediaPlayer:MediaPlayer? = null

    // exercise adapter
    private var exerciseAdapter : ExerciseStatusAdapter?= null

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
            customDialogForBackButton()
        }

        setupRestView()
        setUpExerciseStatusRecycleView()

    }

    override fun onBackPressed() {
        customDialogForBackButton()
    }

    private fun customDialogForBackButton(){
        val customDialog = Dialog(this)
        // name of xml file for finding
        val dialogBinding = CustomDialogConfirmationBinding.inflate(layoutInflater)
        // setting up custom dialog
        customDialog.setContentView(dialogBinding.root)
        customDialog.setCanceledOnTouchOutside(false)



        dialogBinding.btnYes.setOnClickListener {
                    this.finish()
                    customDialog.dismiss()
        }

        dialogBinding.btnNo.setOnClickListener {
            customDialog.dismiss()

        }

        customDialog.show()

    }

    private fun setUpExerciseStatusRecycleView(){
        // displaying items HORIZONTAL
        binding?.rvExerciseStatus?.layoutManager =
            LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)

        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!)
        binding?.rvExerciseStatus?.adapter = exerciseAdapter
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

        exerciseTimer = object:CountDownTimer(EXERCISE_TIME * 1000,1000){
            override fun onTick(p0: Long) {
                exerciseProgress++
                binding?.progressBarExercise?.progress = 30 - exerciseProgress
                binding?.textViewTimerExercise?.text = (30 - exerciseProgress).toString()
            }

            override fun onFinish() {
                if(currentExercisePosition < exerciseList?.size!! - 1){
                    exerciseList!![currentExercisePosition].setIsSelected(false)
                    exerciseList!![currentExercisePosition].setIsCompleted(true)
                    // update the recycle adapter data
                    exerciseAdapter!!.notifyDataSetChanged()
                    setupRestView()
                }else{
                    finish()
                    startActivity(Intent(this@ExcersizeActivity,
                        FinishActivity::class.java))
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

        restTimer = object:CountDownTimer(REST_TIME * 1000,1000){
            override fun onTick(p0: Long) {
                restProgress++
                binding?.progressBar?.progress = 10 - restProgress
                binding?.textViewTimer?.text = (10 - restProgress).toString()
            }

            override fun onFinish() {
                currentExercisePosition++

                exerciseList!![currentExercisePosition].setIsSelected(true)
                // update the recycle adapter data
                exerciseAdapter!!.notifyDataSetChanged()
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