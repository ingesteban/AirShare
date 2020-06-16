package dev.estebanbarrios.airshare.presentation.send

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle,  private val  listFragment:List<Fragment>) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return listFragment.count()
    }

    override fun createFragment(position: Int): Fragment {
        return listFragment[position]
    }
}