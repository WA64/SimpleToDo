package com.example.simpletodo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText


//This class is only invoked by onActivityResult() in MainActivity.kt. Brings up a new window that collects a new string. Passes it back with position in list.
class EditTask : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_task)


        val task = getIntent().getStringExtra("task")
        val textView: EditText = findViewById<EditText>(R.id.editTask)
            textView.setText(task)

        findViewById<Button>(R.id.button2).setOnClickListener {
            val userInputtedTask = findViewById<EditText>(R.id.editTask).text.toString()
            if (userInputtedTask != "") {
                val data = Intent()
                val position = getIntent().getIntExtra("position", -1)
                data.putExtra("NewTask", userInputtedTask)
                data.putExtra("position", position)

                setResult(20, data)
                finish()
            }



        }

    }
}