package com.example.weather

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import com.example.weather.databinding.ActivityWeatherBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class WeatherActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWeatherBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonRead.setOnClickListener {readData()}
        binding.buttonWrite.setOnClickListener {toggleFanState()}
        readFanState()
        binding.buttonSecond.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }


    }


    private fun readData(){
        database = FirebaseDatabase.getInstance().getReference("Weather")
        database.child("Temperature").get().addOnSuccessListener {
            if (it.exists()){
                val temperature:Float = it.value.toString().toFloat()//se citeste valoarea temperaturii
                Toast.makeText(this,"Successful sensors read", Toast.LENGTH_SHORT).show()
                binding.tvAverageTemperature.setText(temperature.toString())//afisare valoare in aplicatie
            } else{
                Toast.makeText(this,"Sensor path does not exist", Toast.LENGTH_SHORT).show()
            }}
            .addOnFailureListener{
                Toast.makeText(this,"FAILED", Toast.LENGTH_SHORT).show()
            }
        database.child("Humidity").get().addOnSuccessListener {
            if (it.exists()){
                val humidity:Float = it.value.toString().toFloat()//se citeste valoarea umiditatii
                binding.tvAverageHumidity.setText(humidity.toString())//afisare valoare
            } else{
                Toast.makeText(this,"Sensor path does not exist", Toast.LENGTH_SHORT).show()
            }}
            .addOnFailureListener{
                Toast.makeText(this,"FAILED", Toast.LENGTH_SHORT).show()
            }
        database.child("Air Quality").get().addOnSuccessListener {
            if (it.exists()){
                val airQuality:Float = it.value.toString().toFloat()// se citeste valoarea calitatii aerului
                binding.tvAverageQuality.setText(airQuality.toString())//afisare valoare
            } else{
                Toast.makeText(this,"Sensor path does not exist", Toast.LENGTH_SHORT).show()
            }}
            .addOnFailureListener{
                Toast.makeText(this,"FAILED", Toast.LENGTH_SHORT).show()
            }
    }
    private fun toggleFanState() {
        database = FirebaseDatabase.getInstance().getReference("Weather")
        database.child("FanState").get().addOnSuccessListener { dataSnapshot ->
            if (dataSnapshot.exists()) {
                val currentFanState = dataSnapshot.value.toString()
                val newFanState = if (currentFanState == "on") "off" else "on"

                // Update the fan state in Firebase
                database.child("FanState").setValue(newFanState)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Fan state toggled successfully", Toast.LENGTH_SHORT).show()
                        readFanState()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Failed to toggle fan state", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(this, "Fan state path does not exist", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to read fan state", Toast.LENGTH_SHORT).show()
        }
    }
    private fun updateFanStateUI(isFanOn: Boolean) {
        if (isFanOn) {
            binding.buttonWrite.text = "Turn Fan OFF"
        } else {
            binding.buttonWrite.text = "Turn Fan ON"
        }
    }

    private fun readFanState() {
        database = FirebaseDatabase.getInstance().getReference("Weather")
        database.child("FanState").get().addOnSuccessListener {
            if (it.exists()) {
                val fanState = it.value.toString()
                val isFanOn = fanState == "on"

                if (isFanOn) {
                    Toast.makeText(this, "Fan is ON", Toast.LENGTH_SHORT).show()
                    // Perform actions when the fan is ON
                } else {
                    Toast.makeText(this, "Fan is OFF", Toast.LENGTH_SHORT).show()
                    // Perform actions when the fan is OFF
                }
                updateFanStateUI(isFanOn)

            } else {
                Toast.makeText(this, "Fan state path does not exist", Toast.LENGTH_SHORT).show()
            }
        }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to read fan state", Toast.LENGTH_SHORT).show()
            }
    }

}