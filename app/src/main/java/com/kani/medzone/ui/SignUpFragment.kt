package com.kani.medzone.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.firebase.firestore.SetOptions
import com.kani.medzone.Constants
import com.kani.medzone.MainActivity
import com.kani.medzone.R
import com.kani.medzone.vm.ActivityViewModel
import kotlinx.android.synthetic.main.fragment_signup.*

class SignUpFragment : Fragment() {

    private val homeViewModel by activityViewModels<ActivityViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,

        ): View? {

        val root = inflater.inflate(R.layout.fragment_signup, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        nextBtn.setOnClickListener {

            if (phNoET.text?.isNotBlank() == true) {
                if (nameEt.text?.isNotBlank() == true) {
                    if (patientNoEt.text?.isNotBlank() == true) {
                        if (illnessEt.text?.isNotBlank() == true) {
                            val data = hashMapOf(
                                Constants.PHONE_NUMBER to phNoET.text.toString(),
                                Constants.NAME to nameEt.text.toString(),
                                Constants.PATIENT_NO to patientNoEt.text.toString(),
                                Constants.ILLNESS to illnessEt.text.toString(),
                            )
                            homeViewModel.firestore.collection(Constants.users)
                                .document(phNoET.text.toString()).set(data, SetOptions.merge())

                            (requireActivity() as MainActivity).getPreferences()?.edit()
                                ?.putString(Constants.PHONE_NUMBER, phNoET.text.toString())?.apply()
                            (requireActivity() as MainActivity).getPreferences()?.edit()
                                ?.putString(Constants.NAME, nameEt.text.toString())?.apply()
                            (requireActivity() as MainActivity).getPreferences()?.edit()
                                ?.putString(Constants.PATIENT_NO, patientNoEt.text.toString())
                                ?.apply()
                            (requireActivity() as MainActivity).getPreferences()?.edit()
                                ?.putString(Constants.ILLNESS, illnessEt.text.toString())?.apply()

                            (requireActivity() as MainActivity).removeFrag(this)
                            (requireActivity() as MainActivity).addFrag(PreferenceFragment().also {
                                it.arguments = bundleOf(Pair("isFirst", true))
                            })
                        } else {
                            illness.error = "Enter illness"
                        }
                    } else {
                        patientNo.error = "enter patient no"
                    }
                } else {
                    ed_name.error = "Enter valid name"
                }

            } else {
                ed_phNo.error = "enter valid phone number"
            }


        }
    }
}