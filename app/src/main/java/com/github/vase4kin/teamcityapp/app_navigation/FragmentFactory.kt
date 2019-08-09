package com.github.vase4kin.teamcityapp.app_navigation

import androidx.fragment.app.Fragment
import com.github.vase4kin.teamcityapp.favorites.view.FavoritesFragment
import com.github.vase4kin.teamcityapp.home.router.HomeRouter
import com.github.vase4kin.teamcityapp.navigation.view.NavigationListFragment
import com.github.vase4kin.teamcityapp.queue.view.BuildQueueFragment
import com.github.vase4kin.teamcityapp.runningbuilds.view.RunningBuildsFragment

private const val FRAGMENTS_SIZE = 4

interface FragmentFactory {
    fun createFragment(index: Int): Fragment
    fun getSize(): Int
}

class FragmentFactoryImpl : FragmentFactory {
    override fun createFragment(index: Int): Fragment {
        when (index) {
            // TODO: provide a resource here
            AppNavigationItem.PROJECTS.ordinal -> return NavigationListFragment.newInstance("Projects", HomeRouter.ROOT_PROJECTS_ID)
            AppNavigationItem.FAVORITES.ordinal -> return FavoritesFragment()
            AppNavigationItem.RUNNING_BUILDS.ordinal -> return RunningBuildsFragment()
            AppNavigationItem.BUILD_QUEUE.ordinal -> return BuildQueueFragment()
        }
        throw IllegalStateException("Wrong index")
    }

    override fun getSize(): Int = FRAGMENTS_SIZE
}