package com.apolis.wenzhao.bluetoothpractice

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast

class MainActivity : AppCompatActivity(), View.OnClickListener {

    //Buttons
    private var button: Button? = null
    private var button2: Button? = null
    private var button3: Button? = null
    private var button4: Button? = null

    //ListView
    private var listView: ListView? = null

    //BluetoothAdapter
    lateinit var ba: BluetoothAdapter

    //Set of paired devices
    private lateinit var pairedDevices: Set<BluetoothDevice>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeViews()
        setUpButtonClickListener()
    }

    override fun onResume() {
        super.onResume()
        ba = BluetoothAdapter.getDefaultAdapter()
    }

    private fun initializeViews() {
        button = findViewById(R.id.button)
        button2 = findViewById(R.id.button2)
        button3 = findViewById(R.id.button3)
        button4 = findViewById(R.id.button4)
        listView = findViewById(R.id.listView)
    }

    private fun setUpButtonClickListener() {
        button!!.setOnClickListener(this)
        button2!!.setOnClickListener(this)
        button3!!.setOnClickListener(this)
        button4!!.setOnClickListener(this)
    }

    private fun on() {
        if(!ba.isEnabled) {
            val turnOn = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(turnOn,0)
            Toast.makeText(this, "Turned on", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Already on", Toast.LENGTH_SHORT).show()
        }
    }

    private fun off() {
        ba.disable()
        Toast.makeText(this, "Turned off", Toast.LENGTH_SHORT).show()
    }

    private fun visible() {
        val getVisible = Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE)
        startActivityForResult(getVisible,0)
    }

    private fun list() {
        pairedDevices = ba.bondedDevices
        val list: ArrayList<String> = ArrayList()
        for(pd in pairedDevices)
            list.add(pd.name)
        Toast.makeText(this, "Showing Paired Devices", Toast.LENGTH_SHORT).show()
        val adapter: ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_list_item_1,list)
        listView!!.adapter = adapter
    }

    override fun onClick(v: View?) {
        when(v) {
            button -> {
                on()
            }
            button2 -> {
                visible()
            }
            button3 -> {
                list()
            }
            button4 -> {
                off()
            }
        }
    }

}