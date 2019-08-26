/*
 * Copyright 2019 Andrey Tolpeev
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

package com.github.vase4kin.teamcityapp.base.tabs.data

/**
 * Listener to receive callback on [OnTextTabChangeEvent]
 */
interface OnTextTabChangeEventListener {

    /**
     * Update tab with new title
     *
     * @param tabPosition - Tab position
     * @param newTitle - New tab title
     */
    fun onUpdateTabTitle(tabPosition: Int, newTitle: String)
}
