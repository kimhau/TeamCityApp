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

import com.github.vase4kin.teamcityapp.api.Repository
import com.github.vase4kin.teamcityapp.app_navigation.AppNavigationInteractor
import com.github.vase4kin.teamcityapp.app_navigation.AppNavigationInteractorImpl
import com.github.vase4kin.teamcityapp.app_navigation.BottomNavigationView
import com.github.vase4kin.teamcityapp.app_navigation.BottomNavigationViewImpl
import com.github.vase4kin.teamcityapp.app_navigation.FragmentFactory
import com.github.vase4kin.teamcityapp.app_navigation.FragmentFactoryImpl
import com.github.vase4kin.teamcityapp.buildlog.data.BuildLogInteractor
import com.github.vase4kin.teamcityapp.buildlog.data.BuildLogInteractorImpl
import com.github.vase4kin.teamcityapp.filter_bottom_sheet_dialog.filter.FilterProvider
import com.github.vase4kin.teamcityapp.home.data.HomeDataManager
import com.github.vase4kin.teamcityapp.home.data.HomeDataManagerImpl
import com.github.vase4kin.teamcityapp.home.router.HomeRouter
import com.github.vase4kin.teamcityapp.home.router.HomeRouterImpl
import com.github.vase4kin.teamcityapp.home.tracker.HomeTracker
import com.github.vase4kin.teamcityapp.home.tracker.HomeTrackerImpl
import com.github.vase4kin.teamcityapp.home.view.HomeActivity
import com.github.vase4kin.teamcityapp.home.view.HomeView
import com.github.vase4kin.teamcityapp.home.view.HomeViewImpl
import com.github.vase4kin.teamcityapp.storage.SharedUserStorage
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.Module
import dagger.Provides
import org.greenrobot.eventbus.EventBus
import teamcityapp.libraries.cache_manager.CacheManager
import teamcityapp.libraries.chrome_tabs.ChromeCustomTabs
import teamcityapp.libraries.chrome_tabs.ChromeCustomTabsImpl
import teamcityapp.libraries.storage.Storage

@Module
object HomeModule {

    @JvmStatic
    @Provides
    fun providesRootDrawerView(activity: HomeActivity): HomeView {
        return HomeViewImpl(activity)
    }

    @JvmStatic
    @Provides
    fun providesRootDataManager(
        repository: Repository,
        sharedUserStorage: SharedUserStorage,
        cacheManager: CacheManager,
        eventBus: EventBus
    ): HomeDataManager {
        return HomeDataManagerImpl(repository, sharedUserStorage, cacheManager, eventBus)
    }

    @JvmStatic
    @Provides
    fun providesFirebaseRootTracker(firebaseAnalytics: FirebaseAnalytics): HomeTracker {
        return HomeTrackerImpl(firebaseAnalytics)
    }

    @JvmStatic
    @Provides
    fun providesFragmentFactory(): FragmentFactory = FragmentFactoryImpl()

    @JvmStatic
    @HomeActivityScope
    @Provides
    fun providesAppNavigationInteractor(
        activity: HomeActivity,
        fragmentFactory: FragmentFactory
    ): AppNavigationInteractor {
        return AppNavigationInteractorImpl(activity.supportFragmentManager, fragmentFactory)
    }

    @JvmStatic
    @Provides
    fun providesBottomNavigationView(
        appNavigationInteractor: AppNavigationInteractor,
        activity: HomeActivity
    ): BottomNavigationView {
        return BottomNavigationViewImpl(appNavigationInteractor, activity)
    }

    @JvmStatic
    @HomeActivityScope
    @Provides
    fun provideFilterProvider(): FilterProvider = FilterProvider()

    @HomeActivityScope
    @Provides
    fun provideChromeTabs(activity: HomeActivity): ChromeCustomTabs =
        ChromeCustomTabsImpl(activity)

    @JvmStatic
    @Provides
    fun providesBuildLogInteractor(
        activity: HomeActivity,
        storage: Storage
    ): BuildLogInteractor {
        return BuildLogInteractorImpl(
            storage,
            activity,
            activity.intent.extras
        )
    }

    @JvmStatic
    @HomeActivityScope
    @Provides
    fun provideHomeRouter(activity: HomeActivity): HomeRouter {
        return HomeRouterImpl(activity)
    }
}
