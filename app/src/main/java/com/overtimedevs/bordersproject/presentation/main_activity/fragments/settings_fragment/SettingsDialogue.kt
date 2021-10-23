package com.overtimedevs.bordersproject.presentation.main_activity.fragments.settings_fragment

import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.overtimedevs.bordersproject.R
import com.overtimedevs.bordersproject.data.repository.UserRepository
import com.overtimedevs.bordersproject.databinding.FragmentSettingsBinding
import com.overtimedevs.bordersproject.domain.model.UserSettings

class SettingsDialogue : BottomSheetDialogFragment() {

    var onNewSettingsApplied : ((UserSettings) -> Unit)? = null

    companion object {
        fun newInstance() = SettingsDialogue()
    }

    private lateinit var viewModel: SettingsViewModel
    lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_settings, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.SettingsBottomSheetDialog)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = SettingsViewModel(UserRepository(requireContext()))

        val items = viewModel.getCountries()
        val adapter = ArrayAdapter(requireContext(), R.layout.item_list_country, items)
        (binding.menu.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        showSavedSettings(viewModel.savedUserSettings)

        binding.btnSubmit.setOnClickListener { onClickSubmit() }
        binding.vaccinationStatusButtons.addOnButtonCheckedListener { _, checkedId, _ ->
           onVaccineButtonClick(checkedId)
        }
    }

    private fun showSavedSettings(savedSettings: UserSettings) {
        showOriginCountry(savedSettings.originCountry)
        showVaccinationStatus(savedSettings.isVaccinated)
    }

    private fun showOriginCountry(originCountry: String){
        if(originCountry != UserSettings.defaultOriginCountry){
            (binding.menu.editText as AutoCompleteTextView).setText(originCountry, false)
        }
    }

    private fun showVaccinationStatus(isVaccinated: Boolean){
        if(isVaccinated){
            binding.vaccinationStatusButtons.check(R.id.vaccinated)
        } else {
            binding.vaccinationStatusButtons.check(R.id.not_vaccinated)
        }
    }

    private fun onClickSubmit(){
        val selectedCountry = binding.menu.editText?.text.toString()
        viewModel.setOriginCountry(selectedCountry)
        viewModel.onClickSubmit()
        onNewSettingsApplied?.invoke(viewModel.newUserSettings)
        dialog?.dismiss()
    }

    private fun onVaccineButtonClick(btnId: Int){
        when(btnId){
            R.id.vaccinated -> viewModel.setVaccinationsStatus(true)
            R.id.not_vaccinated -> viewModel.setVaccinationsStatus(false)
        }

    }

    override fun onDismiss(dialog: DialogInterface) {
        Log.d("SlvkLog", "dis")
        super.onDismiss(dialog)
    }
}