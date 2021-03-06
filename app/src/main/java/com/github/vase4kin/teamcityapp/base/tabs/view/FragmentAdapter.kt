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

package com.github.vase4kin.teamcityapp.base.tabs.view

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import java.util.ArrayList

/**
 * Simple implementation of FragmentPagerAdapter
 */
class FragmentAdapter(fm: FragmentManager, private val context: Context) :
    FragmentPagerAdapter(fm) {

    private val fragmentContainers = ArrayList<FragmentContainer>()

    override fun getItem(position: Int): Fragment {
        return fragmentContainers[position].fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentContainers[position].name
    }

    override fun getCount(): Int {
        return fragmentContainers.size
    }

    fun add(@StringRes nameRes: Int, fragment: Fragment) {
        fragmentContainers.add(FragmentContainer(context.getString(nameRes), fragment))
    }

    fun getFragmentContainers(): List<FragmentContainer> {
        return fragmentContainers
    }

    /**
     * Container for fragment
     */
    class FragmentContainer(val name: String, val fragment: Fragment)
}
