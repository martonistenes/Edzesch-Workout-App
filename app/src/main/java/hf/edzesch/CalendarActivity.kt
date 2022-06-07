package hf.edzesch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import hf.edzesch.databinding.ActivityCalendarBinding

class CalendarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCalendarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupCalendar()
    }

    private fun setupCalendar() {
        binding.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->

            //Snackbar.make(binding.root, msg, Snackbar.LENGTH_LONG).show()

            val intent = Intent(this, DayActivity::class.java)
            intent.putExtra("dayOfMonth",dayOfMonth)
            intent.putExtra("month",month)
            intent.putExtra("year",year)
            startActivity(intent)
        }
    }
}
