package hf.edzesch

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import hf.edzesch.adapter.TrainingAdapter
import hf.edzesch.data.Training
import hf.edzesch.data.TrainingDatabase
import hf.edzesch.databinding.ActivityDayBinding
import hf.edzesch.fragments.NewTrainingDialogFragment
import kotlin.concurrent.thread

class DayActivity : AppCompatActivity(), TrainingAdapter.TrainingClickListener,
    NewTrainingDialogFragment.NewTrainingDialogListener {

    private lateinit var binding: ActivityDayBinding

    private lateinit var database: TrainingDatabase
    private lateinit var adapter: TrainingAdapter

    private lateinit var selectedDate: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        selectedDate = this.intent.getIntExtra("dayOfMonth", -1).toString() + "/" +
                (this.intent.getIntExtra("month", -1) + 1).toString() + "/" +
                this.intent.getIntExtra("year", -1).toString()

        binding.toolbar.title = selectedDate
        setSupportActionBar(binding.toolbar)

        database = TrainingDatabase.getDatabase(applicationContext)

        binding.fab.setOnClickListener {

            val newFrag = NewTrainingDialogFragment()
            val bundle = Bundle()
            bundle.putString("date", selectedDate)
            newFrag.arguments = bundle

            newFrag.show(
                supportFragmentManager,
                NewTrainingDialogFragment.TAG
            )
        }

        initRecyclerView()


    }


    private fun initRecyclerView() {
        adapter = TrainingAdapter(this)
        binding.rvMain.layoutManager = LinearLayoutManager(this)
        binding.rvMain.adapter = adapter
        loadItemsInBackground()
    }

    private fun loadItemsInBackground() {
        thread {
            val items = database.TrainingDao().getDay(selectedDate)
            runOnUiThread {
                adapter.update(items)
            }
        }
    }

    override fun onTrainingChanged(item: Training) {
        thread {
            database.TrainingDao().update(item)
            Log.d("DayActivity", "Training update was successful")
        }
    }

    override fun onTrainingDeleted(item: Training) {
        thread {
            database.TrainingDao().deleteItem(item)
        }
    }

    override fun onTrainingCreated(newItem: Training) {
        thread {
            val insertId = database.TrainingDao().insert(newItem)
            newItem.id = insertId
            runOnUiThread {
                adapter.addItem(newItem)
            }
        }
    }

}