package hf.edzesch.fragments

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import hf.edzesch.R
import hf.edzesch.data.Training
import hf.edzesch.databinding.DialogNewTrainingBinding

class NewTrainingDialogFragment() : DialogFragment() {
    interface NewTrainingDialogListener {
        fun onTrainingCreated(newItem: Training)
    }


    private lateinit var listener: NewTrainingDialogListener

    private lateinit var binding: DialogNewTrainingBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? NewTrainingDialogListener
            ?: throw RuntimeException("Activity must implement the NewTrainingDialogListener interface!")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogNewTrainingBinding.inflate(LayoutInflater.from(context))
        binding.spType.adapter = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            resources.getStringArray(R.array.type_items)
        )

        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.new_training)
            .setView(binding.root)
            .setPositiveButton(R.string.okButton) { dialogInterface, i ->
                listener.onTrainingCreated(getTraining())
            }

            .setNegativeButton(R.string.cancelButton, null)
            .create()
    }

    companion object {
        const val TAG = "NewTrainingDialogFragment"
    }

    private fun getTraining() = Training(
        type = Training.Type.getByOrdinal(binding.spType.selectedItemPosition)
            ?: Training.Type.KAYAKING,
        distance = binding.etDistance.text.toString().toDoubleOrNull() ?: 0.0,
        length = binding.etLength.text.toString().toDoubleOrNull() ?: 0.0,
        location = binding.etLocation.text.toString(),
        description = binding.etDescription.text.toString(),
        selectedDate = this.arguments?.get("date").toString()
    )

}
