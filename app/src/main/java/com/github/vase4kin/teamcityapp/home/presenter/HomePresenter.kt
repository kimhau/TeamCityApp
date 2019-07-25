/*
 * Copyright 2016 Andrey Tolpeev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.vase4kin.teamcityapp.home.presenter

import com.github.vase4kin.teamcityapp.drawer.presenter.DrawerPresenter
import com.github.vase4kin.teamcityapp.home.extractor.HomeBundleValueManager

/**
 * Custom [DrawerPresenter] for [com.github.vase4kin.teamcityapp.home.view.HomeActivity] management
 */
interface HomePresenter : DrawerPresenter {

    /**
     * On resume activity
     */
    fun onResume()

    /**
     * On pause activity
     */
    fun onPause()

    /**
     * On new intent activity
     */
    fun onNewIntent()

    /**
     * On account switch
     */
    fun onAccountSwitch()

    /**
     * Update HomeBundleValueManager with new one
     *
     * @param homeBundleValueManager - HomeBundleValueManager to update
     */
    fun updateRootBundleValueManager(homeBundleValueManager: HomeBundleValueManager)
}
