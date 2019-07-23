package com.github.vase4kin.teamcityapp.app_navigation

import androidx.fragment.app.Fragment
import com.github.vase4kin.teamcityapp.navigation.view.NavigationListFragment
import com.github.vase4kin.teamcityapp.root.router.RootRouter

private const val FRAGMENTS_SIZE = 4

interface FragmentFactory {
    fun createFragment(index: Int): Fragment
    fun getSize(): Int
}

class FragmentFactoryImpl : FragmentFactory {
    override fun createFragment(index: Int): Fragment {
        when (index) {
            AppNavigationItem.HOME.ordinal -> return NavigationListFragment.newInstance("", RootRouter.ROOT_PROJECTS_ID)
            AppNavigationItem.FAVORITES.ordinal -> NavigationListFragment.newInstance("", RootRouter.ROOT_PROJECTS_ID)
            AppNavigationItem.RUNNING_BUILDS.ordinal -> NavigationListFragment.newInstance("", RootRouter.ROOT_PROJECTS_ID)
            AppNavigationItem.BUILD_QUEUE.ordinal -> return NavigationListFragment.newInstance("", RootRouter.ROOT_PROJECTS_ID)
        }
        throw IllegalStateException("Wrong index")
    }

    override fun getSize(): Int = FRAGMENTS_SIZE
}