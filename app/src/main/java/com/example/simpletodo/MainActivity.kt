package com.example.simpletodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.FileUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.IOException
import java.nio.charset.Charset
import android.content.Intent




class MainActivity : AppCompatActivity() {
    var listOfTasks = mutableListOf<String>()
    lateinit var adapter: TaskItemAdapter
    val REQUEST_CODE = 20



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener{
            override fun onItemLongClicked(position: Int) {
                //Remove item from list on long click and update
                listOfTasks.removeAt(position)
                adapter.notifyDataSetChanged()
                saveItems()


            }

        }

        //Begins the Intent for when a list item is tapped on, invokes EditTask.kt
        val onClickListener = object : TaskItemAdapter.OnClickListener{
            override fun onItemClicked(position: Int) {
                val i = Intent(this@MainActivity, EditTask::class.java)
                i.putExtra("task", listOfTasks.elementAt(position))
                i.putExtra("position", position)

                startActivityForResult(i, REQUEST_CODE)
            }

        }








//        findViewById<Button>(R.id.button).setOnClickListener{
//            Log.i("Caren","user clicked on button")
//        }


        loadItems()

        //Lookup recyclerView in layout
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        // Create adapter passing in the sample user data
        adapter = TaskItemAdapter(listOfTasks, onLongClickListener,onClickListener )
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val inputTextField = findViewById<EditText>(R.id.addTaskField)
        //Button and input field setup
        findViewById<Button>(R.id.button).setOnClickListener{
             val userInputtedTask = findViewById<EditText>(R.id.addTaskField).text.toString()
            if (userInputtedTask != "") {
                listOfTasks.add(userInputtedTask)

                adapter.notifyItemInserted(listOfTasks.size - 1)

                inputTextField.setText("")

                saveItems()
            }
        }




    }


    //Called by the conclusion of EditTask.kt, replaces String in given position in list with given string
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val newTask = data?.getExtras()?.getString("NewTask")
        val position = data?.getExtras()?.getInt("position")

        Log.d("error","newTask is " + newTask + " and position is " + position)


        if (newTask != null && position != -1) {
            listOfTasks[position!!] = newTask
            adapter.notifyDataSetChanged()
            saveItems()
        }

    }

    //Save all current data to a file

    //get data file
    fun getDataFile(): File{
        return File(filesDir, "data.txt")
        // in data.txt, each line represents one task
    }
    //load items from data file
    fun loadItems () {
        try {
            listOfTasks =
                org.apache.commons.io.FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
    }

    //Save items to data file
    fun saveItems (){
        try{
            org.apache.commons.io.FileUtils.writeLines(getDataFile(), listOfTasks)
        } catch (ioException: IOException){
            ioException.printStackTrace()
        }

    }


}
