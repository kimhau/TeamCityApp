/*
 * Copyright 2020 Andrey Tolpeev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.vase4kin.teamcityapp.home.dagger

import com.github.vase4kin.teamcityapp.agents.dagger.AgentFragmentScope
import com.github.vase4kin.teamcityapp.agents.dagger.AgentModule
import com.github.vase4kin.teamcityapp.agents.view.AgentListFragment
import com.github.vase4kin.teamcityapp.buildlist.dagger.BuildListAdapterModule
import com.github.vase4kin.teamcityapp.dagger.modules.drawer.DrawerRouterModule
import com.github.vase4kin.teamcityapp.favorites.dagger.FavoritesFragmentModule
import com.github.vase4kin.teamcityapp.favorites.dagger.FavoritesFragmentScope
import com.github.vase4kin.teamcityapp.favorites.view.FavoritesFragment
import com.github.vase4kin.teamcityapp.filter_bottom_sheet_dialog.dagger.FilterBottomSheetDialogScope
import com.github.vase4kin.teamcityapp.filter_bottom_sheet_dialog.dagger.FilterBottomSheetModule
import com.github.vase4kin.teamcityapp.filter_bottom_sheet_dialog.view.FilterBottomSheetDialogFragment
import com.github.vase4kin.teamcityapp.navigation.dagger.NavigationBaseModule
import com.github.vase4kin.teamcityapp.navigation.dagger.NavigationFragmentModule
import com.github.vase4kin.teamcityapp.navigation.dagger.NavigationFragmentScope
import com.github.vase4kin.teamcityapp.navigation.view.NavigationListFragment
import com.github.vase4kin.teamcityapp.queue.dagger.BuildQueueFragmentModule
import com.github.vase4kin.teamcityapp.queue.dagger.BuildQueueFragmentScope
import com.github.vase4kin.teamcityapp.queue.view.BuildQueueFragment
import com.github.vase4kin.teamcityapp.runningbuilds.dagger.RunningBuildsFragmentModule
import com.github.vase4kin.teamcityapp.runningbuilds.dagger.RunningBuildsFragmentScope
import com.github.vase4kin.teamcityapp.runningbuilds.view.RunningBuildsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector
import teamcityapp.features.drawer.dagger.DrawerBottomSheetDialogModule
import teamcityapp.features.drawer.dagger.DrawerBottomSheetDialogScope
import teamcityapp.features.drawer.view.DrawerBottomSheetDialogFragment

@Module
abstract class HomeActivityBindingModule {

    @NavigationFragmentScope
    @ContributesAndroidInjector(modules = [NavigationFragmentModule::class, NavigationBaseModule::class])
    abstract fun navigationListFragment(): NavigationListFragment

    @FavoritesFragmentScope
    @ContributesAndroidInjector(modules = [FavoritesFragmentModule::class])
    abstract fun favoritesFragment(): FavoritesFragment

    @RunningBuildsFragmentScope
    @ContributesAndroidInjector(modules = [RunningBuildsFragmentModule::class, BuildListAdapterModule::class])
    abstract fun runningBuildsFragment(): RunningBuildsFragment

    @BuildQueueFragmentScope
    @ContributesAndroidInjector(modules = [BuildQueueFragmentModule::class, BuildListAdapterModule::class])
    abstract fun buildQueueFragment(): BuildQueueFragment

    @AgentFragmentScope
    @ContributesAndroidInjector(modules = [AgentModule::class])
    abstract fun agentListFragment(): AgentListFragment

    @DrawerBottomSheetDialogScope
    @ContributesAndroidInjector(modules = [DrawerBottomSheetDialogModule::class, DrawerRouterModule::class])
    abstract fun drawerBottomSheetDialog(): DrawerBottomSheetDialogFragment

    @FilterBottomSheetDialogScope
    @ContributesAndroidInjector(modules = [FilterBottomSheetModule::class])
    abstract fun filterBottomSheetDialog(): FilterBottomSheetDialogFragment
}
