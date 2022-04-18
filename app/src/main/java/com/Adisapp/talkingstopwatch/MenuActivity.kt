package com.Adisapp.talkingstopwatch

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MenuActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu)
        val setfrequency = findViewById<Button>(R.id.frequency)
        setfrequency.setOnClickListener(){
            val tp1 = findViewById<NumberPicker>(R.id.numberPicker1)
            val tp2 = findViewById<NumberPicker>(R.id.numberPicker2)
            val tv1 = findViewById<TextView>(R.id.minutes)
            val tv2 = findViewById<TextView>(R.id.seconds)
            val btn1 = findViewById<Button>(R.id.setbtn)
            val btn2 = findViewById<Button>(R.id.cancelbtn)
            tp1.visibility= View.VISIBLE
            tp2.visibility= View.VISIBLE
            tv1.visibility= View.VISIBLE
            tv2.visibility= View.VISIBLE
            btn1.visibility= View.VISIBLE
            btn2.visibility= View.VISIBLE
            if (tp1 != null) {
                tp1.minValue = 0
                tp1.maxValue = 60
                tp1.wrapSelectorWheel = true
            }
            if (tp2 != null) {
                tp2.minValue = 5
                tp2.maxValue = 59
                tp2.wrapSelectorWheel = true
            }

        }
    }

    fun setit(view: View) {
        val tp1 = findViewById<NumberPicker>(R.id.numberPicker1)
        val tp2 = findViewById<NumberPicker>(R.id.numberPicker2)
        val intent = Intent(this, MainActivity::class.java)
        val frequency = (tp1.value*60)+ tp2.value
        intent.putExtra("frequency", frequency)
        startActivity(intent)
        finish()
    }
    fun cancelit(view: View) {
        val tp1 = findViewById<NumberPicker>(R.id.numberPicker1)
        val tp2 = findViewById<NumberPicker>(R.id.numberPicker2)
        val tv1 = findViewById<TextView>(R.id.minutes)
        val tv2 = findViewById<TextView>(R.id.seconds)
        val btn1 = findViewById<Button>(R.id.setbtn)
        val btn2 = findViewById<Button>(R.id.cancelbtn)
        tp1.visibility = View.GONE
        tp2.visibility = View.GONE
        tv1.visibility = View.GONE
        tv2.visibility = View.GONE
        btn1.visibility = View.GONE
        btn2.visibility = View.GONE
    }

}