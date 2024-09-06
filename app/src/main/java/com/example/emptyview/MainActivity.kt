package com.example.emptyview

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.example.emptyview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val animals = mutableListOf(
        "🐶", "🐱", "🐭", "🐹", "🐰", "🦊", "🐻", "🐼", "🐨", "🐷", "🐒",
        "🐤", "🐣", "🐥", "🦆", "🦉", "🐺",
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityMainBinding.inflate(layoutInflater)
        val setAnimalCount = {
            binding.animalCount.text = "Animal Count: " + animals.size
        }
        setAnimalCount()
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, animals)
        binding.listview.adapter = adapter
        binding.listview.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val clickedAnimal = animals[position]
            adapter.remove(animals[position])
            binding.theText.text = ""
            binding.removeText.text = "You removed $clickedAnimal"
            binding.listview.isVisible = false
            binding.removeAnimal.isVisible = true
            setAnimalCount()
        }
        val noMoreAnimals = "There are no animals left \uD83D\uDE3F"
        val handleNoMoreAnimals = {
            binding.removeText.isVisible = false
            binding.theText.isVisible = true
            binding.theText.text = noMoreAnimals
            binding.theText.setTextSize(16.0F)
        }

        setContentView(binding.root)
        var animalIndex = 0
        binding.theBtn.setOnClickListener {
            if (animals.size == 0) {
                handleNoMoreAnimals()
                return@setOnClickListener
            }
            if (animalIndex >= animals.size) {
                animalIndex = 0
            }
            binding.removeText.text = ""
            val animal = animals[animalIndex]
            binding.theText.text = "$animal"
            animalIndex++
        }
        binding.removeAnimal.setOnClickListener{
            if (animals.size == 0) {
                handleNoMoreAnimals()
                return@setOnClickListener
            }
            binding.removeAnimal.isVisible = false
            binding.listview.isVisible = true
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }
}