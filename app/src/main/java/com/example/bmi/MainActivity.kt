package com.example.bmi

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import java.lang.Double.parseDouble


class MainActivity : AppCompatActivity() {
    private lateinit var etHeight: EditText
    private lateinit var etWeight: EditText
    private lateinit var calculateBtn: Button
    private lateinit var bmiResult: TextView
    private lateinit var status: TextView
    private lateinit var background: ConstraintLayout
    private lateinit var initMsg: TextView
    private lateinit var instructionCard: CardView
    private lateinit var insImageView: ImageView
    private lateinit var insTextView: TextView
    private lateinit var spinner: Spinner
    private lateinit var selectedUnit: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "BMI calculator"
        init()
        setUpCustomSpinner()
        calculateBtn.setOnClickListener {
            if (validateForm()) {
                dismissKeyboard()
                calculateBMI()
            }
        }
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setMessage("Are you sure you want to leave!!")
            .setPositiveButton("Yes") { _, _ -> finish() }
            .setNegativeButton("No", null)
            .setCancelable(false)
            .show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.reset -> {
//                Toast.makeText(this, "reset", Toast.LENGTH_SHORT).show()
                reset()
            }
            R.id.about -> {
                startActivity(Intent(this, About::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun reset() {
        etHeight.setText("")
        etWeight.setText("")
        instructionCard.visibility = View.GONE
        initMsg.visibility = View.VISIBLE
        bmiResult.visibility = View.GONE
        status.visibility = View.GONE
        setColor("#039BE5", "#1E88E5", "#64b5f6")
    }

    @SuppressLint("Range", "SetTextI18n")
    private fun calculateBMI() {
        val weight = etWeight.text.toString().toFloat()
        val height = (etHeight.text.toString().toFloat())
        val result = unitConversion(height, weight)
        val r = result.toFloat()
        bmiResult.text = result
        initMsg.visibility = View.GONE
        instructionCard.visibility = View.VISIBLE
        bmiResult.visibility = View.VISIBLE
        status.visibility = View.VISIBLE

        when {
            r < 15 -> {
                setColor("#d93326", "#dc2116", "#ef5350")
                status.text = "Very severely underweight"
                insImageView.setImageResource(R.drawable.ic_hamburger)
                insTextView.text = "Hey you should take proteins and fat you are very week"

            }
            r in 15.0..16.0 -> {
                setColor("#f0270f", "#bc1f0c", "#ef5350")
                status.text = "Severely underweight"
                insImageView.setImageResource(R.drawable.ic_hamburger)
                insTextView.text =
                    "Hey you should take proteins and fat you really have to focus on your diet"
            }
            r in 16.0..18.5 -> {
                setColor("#fbc02d", "#caa600", "#fdd835")
                status.text = "Underweight"
                insImageView.setImageResource(R.drawable.ic_diet)
                insTextView.text =
                    "Hey you should take proteins and do some exercise play outdoor games"
            }
            r in 18.5..25.0 -> {
                setColor("#8bc34a", "#689f38", "#aed581")
                status.text = "Normal (healthy weight)"
                insImageView.setImageResource(R.drawable.ic_diet)
                insTextView.text =
                    "Keep it up, you are in good shape but keep exercising and eat healthy."
            }
            r in 25.1..30.0 -> {
                setColor("#fbc02d", "#caa600", "#fdd835")
                status.text = "Overweight"
                insImageView.setImageResource(R.drawable.ic_exercise)
                insTextView.text =
                    "You need to cut some fat and sugar from your diet, do exercise you will be in shape in no time"
            }
            r in 30.1..35.0 -> {
                setColor("#f0270f", "#d3220d", "#ef5350")
                status.text = "Moderately obese"
                insImageView.setImageResource(R.drawable.ic_exercise)
                insTextView.text =
                    "You should focus on your diet and do some exercise start with light exercise and overtime do some serious exercise"
            }
            r in 35.1..40.0 -> {
                setColor("#f0270f", "#bc1f0c", "#ef5350")
                status.text = "Severely obese"
                insImageView.setImageResource(R.drawable.ic_exercise__1_)
                insTextView.text =
                    "You need to cut some fat and sugar from your diet, do some exercise start with light exercise and overtime do some serious exercise"
            }
            r > 40 -> {
                setColor("#d93326", "#dc2116", "#ef5350")
                status.text = "Very severely obese"
                insImageView.setImageResource(R.drawable.ic_exercise__1_)
                insTextView.text =
                    "obesity leads to many health problem look after your body, get some help, eat less sugar and do exercise"
            }
        }

    }

    private fun unitConversion(h: Float, w: Float): String {
        return if (selectedUnit == "cm/kg") {
            val nH = h / 100
            String.format("%.2f", w / (nH * nH))
        } else {
            val nH = (h * 2.54) / 100
            String.format("%.2f", w / (nH * nH))
        }
    }

    private fun setColor(color: String, darkColor: String, bgColor: String) {
        val actionBar: ActionBar? = supportActionBar
        val colorDrawable = ColorDrawable(Color.parseColor(color))
        actionBar!!.setBackgroundDrawable(colorDrawable)
        window.statusBarColor = Color.parseColor(darkColor)
        background.setBackgroundColor(Color.parseColor(bgColor))
        calculateBtn.setBackgroundColor(Color.parseColor(color))
    }

    private fun validateForm(): Boolean {
        return when {
            selectedUnit == "Select unit" -> {
                Toast.makeText(this, "Please select unit", Toast.LENGTH_SHORT).show()
                false
            }
            etHeight.text.isEmpty() -> {
                etHeight.error = "Enter height"
                false
            }
            etWeight.text.isEmpty() -> {
                etWeight.error = "Enter weight"
                false
            }
            !checkNumber(etHeight.text.toString()) -> {
                etHeight.error = "Enter valid height"
                return false
            }
            !checkNumber(etWeight.text.toString()) -> {
                etWeight.error = "Enter valid weight"
                return false
            }
            else -> true
        }
    }

    private fun checkNumber(string: String): Boolean {
        var numeric = true
        try {
            val num = parseDouble(string)
        } catch (e: NumberFormatException) {
            numeric = false
        }
        return numeric
    }

    private fun setUpCustomSpinner() {

        val unitSpinnerAdapter = UnitSpinner(this, Unit.ObjUnit.list!!)
        spinner.adapter = unitSpinnerAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedUnit = Unit.ObjUnit.list!![position].Unit
//                Toast.makeText(this@MainActivity, "$selectedUnit Selected", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Use as per your wish
            }
        }
    }

    private fun init() {
        etHeight = findViewById(R.id.et_height)
        etWeight = findViewById(R.id.et_weight)
        calculateBtn = findViewById(R.id.calculate_btn)
        bmiResult = findViewById(R.id.tv_result)
        status = findViewById(R.id.tv_status)
        background = findViewById(R.id.bg)
        initMsg = findViewById(R.id.init_msg)
        instructionCard = findViewById(R.id.instruction_card)
        insImageView = findViewById(R.id.ins_img)
        insTextView = findViewById(R.id.ins_tv)
        spinner = findViewById(R.id.spinner)
    }

    private fun dismissKeyboard() {
        val imm =
            this.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
        if (null != this.currentFocus) imm.hideSoftInputFromWindow(
            this.currentFocus!!
                .applicationWindowToken, 0
        )
    }

}