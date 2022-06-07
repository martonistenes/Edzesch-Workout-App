package hf.edzesch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import hf.edzesch.data.Training
import hf.edzesch.data.TrainingDatabase
import hf.edzesch.databinding.ActivityStatisticsBinding
import kotlin.concurrent.thread

class StatisticsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStatisticsBinding

    private lateinit var database: TrainingDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatisticsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.spType.adapter = ArrayAdapter(
            this,
            R.layout.support_simple_spinner_dropdown_item,
            resources.getStringArray(R.array.type_items)
        )

        database = TrainingDatabase.getDatabase(applicationContext)

        binding.okButton.setOnClickListener {

            updateText(Training.Type.getByOrdinal(binding.spType.selectedItemPosition)
                ?: Training.Type.KAYAKING)
        }

    }

    private fun updateText(t: Training.Type) {
        thread {

            val totald = "Total Distance: ${database.TrainingDao().getTotalDistance(t)} km"
            val maxd = "Longest Distance: ${database.TrainingDao().getLongestDistance(t)} km"
            val avgd = "Average Distance: ${database.TrainingDao().getAverageDistance(t)} km"
            val totalt = "Total Time: ${database.TrainingDao().getTotalTime(t)} h"
            val maxt = "Longest Activity: ${database.TrainingDao().getLongest(t)} h"
            val avgt = "Average Length: ${database.TrainingDao().getAverageLength(t)} h"

            runOnUiThread {
                binding.tvTotalDistance.text =totald
                binding.tvLongestDistance.text =maxd
                binding.tvAverageDistance.text =avgd
                binding.tvTotalTime.text =totalt
                binding.tvLongestTime.text = maxt
                binding.tvAverageTime.text = avgt
            }
        }
    }
}