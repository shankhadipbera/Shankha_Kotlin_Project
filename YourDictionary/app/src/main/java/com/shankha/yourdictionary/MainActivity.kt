package com.shankha.yourdictionary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.chaquo.python.PyObject
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editText=findViewById<EditText>(R.id.editText)
        val button=findViewById<Button>(R.id.button)
        val txtV1=findViewById<TextView>(R.id.textView)
        val txtV2=findViewById<TextView>(R.id.textView2)
        val txtV3=findViewById<TextView>(R.id.textView3)
        val txtV4=findViewById<TextView>(R.id.textView4)
        val txtV5=findViewById<TextView>(R.id.textView5)

        button.setOnClickListener {
            val word = editText.text.toString()
            txtV1.text = "You searched for $word"

            if (!Python.isStarted()) {
                Python.start(AndroidPlatform(this))
            }
            val py = Python.getInstance()
            val pyObj = py.getModule("dictionary")
            val obj: PyObject = pyObj.callAttr("searchedFor", word)

            // Check if the Python function returned valid results
            if (obj!=null) {
                val (definition, partOfSpeech, synonym, antonym) = obj.asList()

                // Set the values to the TextViews
                txtV2.text = "Definition: $definition"
                txtV3.text = "Part Of Speech: $partOfSpeech"
                txtV4.text = "Synonym: $synonym"
                txtV5.text = "Antonym: $antonym"

            } else {

                txtV2.text = "No data found"

            }
        }
}}