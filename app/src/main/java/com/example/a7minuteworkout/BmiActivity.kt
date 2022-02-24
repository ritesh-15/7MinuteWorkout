package com.example.a7minuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.a7minuteworkout.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BmiActivity : AppCompatActivity() {

    private var binding:ActivityBmiBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolBarBmi)

        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        // overide tool bar title
        supportActionBar?.title = "Calculate BMI"

        binding?.toolBarBmi?.setNavigationOnClickListener{
            // setting up back button
            onBackPressed()
        }

        calculationOfWeightAndHeight()
    }



    // display bmi result
    private fun displayBmiResult(bmi:Float){

        var bmiLabel:String
        var bmiDescription:String

        if (bmi.compareTo(15f) <= 0) {
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0
        ) {
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops!You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0
        ) {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0
        ) {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        } else if (java.lang.Float.compare(bmi, 25f) > 0 && java.lang.Float.compare(
                bmi,
                30f
            ) <= 0
        ) {
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0
        ) {
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0
        ) {
            bmiLabel = "Obese Class || (Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        } else {
            bmiLabel = "Obese Class ||| (Very Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }

        binding?.cnBmiInformation?.visibility = View.VISIBLE
        binding?.tvBmi?.text =  BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        binding?.tvBmiWeight?.text = bmiLabel

        binding?.tvMessage?.text = bmiDescription

    }

    // calculation of weight and height
    private fun calculationOfWeightAndHeight(){
        binding?.btnCalculate?.setOnClickListener {
            if(validateMetricUnits()){

                binding?.tvHeightErrorMessage?.visibility = View.INVISIBLE
                binding?.tvWeightErrorMessage?.visibility = View.INVISIBLE

                // getting height in meters
                val weight:Float = binding?.tvInputWeight?.text.toString().toFloat()
                val height:Float = binding?.tvInputHeight?.text.toString().toFloat() / 100

                val bmi = weight / (height * height)

                displayBmiResult(bmi)

            }else{

                binding?.tvHeightErrorMessage?.text = "Please enter valide height!"
                binding?.tvWeightErrorMessage?.text = "Please enter valide weight!"


                binding?.tvHeightErrorMessage?.visibility = View.VISIBLE
                binding?.tvWeightErrorMessage?.visibility = View.VISIBLE
            }


        }


    }

    // validate weight and height
    private fun validateMetricUnits():Boolean{

        var isValide = true;

        val weight = binding?.tvInputWeight?.text.toString()
        val height = binding?.tvInputHeight?.text.toString()


        if(weight?.isEmpty() || height?.isEmpty()){
            isValide = false;
        }

        return isValide
    }

}