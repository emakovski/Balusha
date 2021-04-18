package com.egor.balusha

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_bottom_menu.*

class BottomNavFragment: BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_bottom_menu, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        navigation_view.setNavigationItemSelectedListener { menuItem ->
            // Bottom Navigation Drawer menu item clicks
            when (menuItem.itemId) {
                R.id.menu_my_pet -> context!!.toast(getString(R.string.nav1_clicked))
                R.id.menu_vaccination -> context!!.toast(getString(R.string.nav2_clicked))
                R.id.menu_helminths -> context!!.toast(getString(R.string.nav3_clicked))
                R.id.menu_fleas -> context!!.toast(getString(R.string.nav1_clicked))
                R.id.menu_reproduction -> context!!.toast(getString(R.string.nav2_clicked))
                R.id.menu_diet -> context!!.toast(getString(R.string.nav3_clicked))
                R.id.menu_places -> context!!.toast(getString(R.string.nav1_clicked))
                R.id.menu_moments -> context!!.toast(getString(R.string.nav2_clicked))
                R.id.menu_weather -> context!!.toast(getString(R.string.nav3_clicked))
                R.id.menu_navi -> context!!.toast(getString(R.string.nav3_clicked))
            }
            // Add code here to update the UI based on the item selected
            // For example, swap UI fragments here
            true
        }
    }

    // This is an extension method for easy Toast call
    private fun Context.toast(message: CharSequence) {
        val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.BOTTOM, 0, 600)
        toast.show()
    }

}