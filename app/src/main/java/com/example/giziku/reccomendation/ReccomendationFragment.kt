package com.example.giziku.reccomendation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.giziku.databinding.FragmentReccomendationBinding

class ReccomendationFragment : Fragment() {
    private lateinit var binding: FragmentReccomendationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReccomendationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val inputHeight: EditText = binding.inputHeight
        val inputWeight: EditText = binding.inputWeight
        val radioGroupGoal: RadioGroup = binding.radioGroupGoal
        val buttonGetRecommendation: Button = binding.buttonGetRecommendation
        val textRecommendation: TextView = binding.textRecommendation

        buttonGetRecommendation.setOnClickListener {
            val height = inputHeight.text.toString().toIntOrNull()
            val weight = inputWeight.text.toString().toIntOrNull()
            val selectedGoalId = radioGroupGoal.checkedRadioButtonId

            if (height != null && weight != null && selectedGoalId != -1) {
                val bmi = calculateBMI(height, weight)
                val recommendation = getRecommendation(bmi)
                textRecommendation.text = recommendation.joinToString(", ")
            } else {
                textRecommendation.text = "Harap isi semua input"
            }
        }
    }

    private fun calculateBMI(height: Int, weight: Int): Double {
        val heightInMeters = height / 100.0
        return weight / (heightInMeters * heightInMeters)
    }

    private fun getRecommendation(bmi: Double): List<String> {
        val recommendation = mutableListOf<String>()

        when {
            bmi < 18.5 -> {
                recommendation.addAll(foodRecommendations["rendah-protein-tinggi-lemak"]!!)
                recommendation.addAll(foodRecommendations["tinggi-protein-tinggi-lemak"]!!)
            }
            bmi in 18.5..24.9 -> {
                recommendation.addAll(foodRecommendations["rendah-protein-rendah-lemak"]!!)
                recommendation.addAll(foodRecommendations["rendah-protein-tinggi-lemak"]!!)
                recommendation.addAll(foodRecommendations["tinggi-protein-rendah-lemak"]!!)
            }
            bmi > 24.9 -> {
                recommendation.addAll(foodRecommendations["tinggi-protein-rendah-lemak"]!!)
            }
        }

        return recommendation
    }

    companion object {
        val foodRecommendations = mapOf(
            "rendah-protein-rendah-lemak" to listOf("Kerupuk Ikan berpati", "Gendar goreng", "Sapi daging dendeng mentah", "Sapi paru goreng", "Susu Kental Manis", "Kacang mentega kering"),
            "rendah-protein-tinggi-lemak" to listOf("Minyak Wijen", "Minyak Hati Hiu (Eulamia)", "Kacang Telur", "Margarin", "Minyak Kelapa Sawit", "Minyak kedelai", "Mentega"),
            "tinggi-protein-rendah-lemak" to listOf("Rendang sapi masakan", "Petis Udang", "Kluwih", "Nasi", "Getuk Lindri", "Daun Cincau"),
            "tinggi-protein-tinggi-lemak" to listOf("Kacang tanah kering", "Ikan Sale segar", "Bagea kelapa manis", "Kacang Tanah kacang sari", "Kacang Kapri Goreng", "Keripik singkong berbumbu")
        )
    }
}
