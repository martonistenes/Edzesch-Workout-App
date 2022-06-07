package hf.edzesch.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import hf.edzesch.R
import hf.edzesch.data.Training
import hf.edzesch.databinding.TrainingListBinding


class TrainingAdapter(private val listener: TrainingClickListener) :
    RecyclerView.Adapter<TrainingAdapter.TrainingViewHolder>() {

    private val items = mutableListOf<Training>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TrainingViewHolder(
        TrainingListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: TrainingViewHolder, position: Int) {
        val training = items[position]

        holder.binding.tvType.text = "${training.type}"
        holder.binding.tvDistance.text = "Distance: ${training.distance} km"
        holder.binding.tvLength.text = "Length: ${training.length} h"
        holder.binding.tvLocation.text = "Location: " + training.location
        holder.binding.tvDescription.text = "Description: " + training.description
        holder.binding.ivIcon.setImageResource(getImageResource(training.type))


        holder.binding.ibRemove.setOnClickListener {
            listener.onTrainingDeleted(training)
            deleteItem(training)
        }

    }

    @DrawableRes()
    private fun getImageResource(type: Training.Type): Int {
        return when (type) {
            Training.Type.KAYAKING -> R.drawable.kayaking
            Training.Type.RUNNING -> R.drawable.running
            Training.Type.SWIMMING -> R.drawable.swimming
            Training.Type.LIFTING -> R.drawable.lifting
            Training.Type.CYCLING -> R.drawable.cycling
            Training.Type.ELSE -> R.drawable.else_

        }
    }

    fun addItem(item: Training) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun deleteItem(item: Training) {
        items.remove(item)
        notifyDataSetChanged()
    }

    fun update(shoppingItems: List<Training>) {
        items.clear()
        items.addAll(shoppingItems)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    interface TrainingClickListener {
        fun onTrainingChanged(item: Training)
        fun onTrainingDeleted(item: Training)
    }

    inner class TrainingViewHolder(val binding: TrainingListBinding) :
        RecyclerView.ViewHolder(binding.root)
}
