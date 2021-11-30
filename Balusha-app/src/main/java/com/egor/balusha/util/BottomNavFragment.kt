package com.egor.balusha.util

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.egor.balusha.R
import com.egor.balusha.activities.PetBio
import com.egor.balusha.activities.Settings
import com.egor.balusha.activities.fleasticks.view.FleasTicksList
import com.egor.balusha.activities.helminths.view.HelminthTreatList
import com.egor.balusha.activities.reproduction.view.ReproductionList
import com.egor.balusha.activities.vaccination.view.VaccinationList
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_bottom_menu.*

class BottomNavFragment: BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_bottom_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigation_view.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_my_pet -> activity?.let{it.startActivity(Intent(it, PetBio::class.java))}
                R.id.menu_vaccination -> activity?.let{it.startActivity(Intent(it, VaccinationList::class.java))}
                R.id.menu_helminths -> activity?.let{it.startActivity(Intent(it, HelminthTreatList::class.java))}
                R.id.menu_fleas -> activity?.let{it.startActivity(Intent(it, FleasTicksList::class.java))}
                R.id.menu_reproduction -> activity?.let{it.startActivity(Intent(it, ReproductionList::class.java))}
                R.id.menu_settings -> activity?.let{it.startActivity(Intent(it, Settings::class.java))}
            }
            true
        }
    }

}