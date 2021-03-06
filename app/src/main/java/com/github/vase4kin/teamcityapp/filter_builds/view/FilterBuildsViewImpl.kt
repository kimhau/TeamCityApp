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

package com.github.vase4kin.teamcityapp.filter_builds.view

import android.view.View
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import butterknife.Unbinder
import com.afollestad.materialdialogs.MaterialDialog
import com.github.vase4kin.teamcityapp.R
import com.github.vase4kin.teamcityapp.account.create.view.OnToolBarNavigationListenerImpl
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

/**
 * Impl of [FilterBuildsView]
 */
class FilterBuildsViewImpl(private val activity: FilterBuildsActivity) : FilterBuildsView {

    @BindView(R.id.fab_filter)
    lateinit var filterFab: ExtendedFloatingActionButton
    @BindView(R.id.selected_filter)
    lateinit var selectedFilterStatus: TextView
    @BindView(R.id.switcher_is_personal)
    lateinit var personalSwitch: Switch
    @BindView(R.id.switcher_is_pinned)
    lateinit var pinnedSwitch: Switch
    @BindView(R.id.divider_switcher_is_pinned)
    lateinit var pinnedSwitcherDivider: View
    lateinit var unbinder: Unbinder

    private lateinit var filterChooser: MaterialDialog

    private var selectedFilter = FilterBuildsView.FILTER_NONE

    @OnClick(R.id.chooser_filter)
    fun onFilterChooserClick() {
        filterChooser.show()
    }

    /**
     * {@inheritDoc}
     */
    override fun initViews(listener: FilterBuildsView.ViewListener) {
        unbinder = ButterKnife.bind(this, activity)

        val toolbar = activity.findViewById<Toolbar>(R.id.toolbar)
        activity.setSupportActionBar(toolbar)

        val actionBar = activity.supportActionBar
        actionBar?.setTitle(R.string.title_filter_builds)

        // For ui testing purpose
        toolbar.setNavigationContentDescription(R.string.navigate_up)
        toolbar.setNavigationIcon(R.drawable.ic_close_black_24dp)
        toolbar.setNavigationOnClickListener(OnToolBarNavigationListenerImpl(listener))

        filterFab.setOnClickListener {
            listener.onFilterFabClick(
                selectedFilter,
                personalSwitch.isChecked,
                pinnedSwitch.isChecked
            )
        }

        filterChooser = MaterialDialog.Builder(activity)
            .title(R.string.title_filter_chooser_dialog)
            .items(R.array.build_filters)
            .itemsCallback { _, _, position, text ->
                if (position == FilterBuildsView.FILTER_QUEUED) {
                    listener.onQueuedFilterSelected()
                } else {
                    listener.onOtherFiltersSelected()
                }
                selectedFilterStatus.text = text
                selectedFilter = position
            }
            .build()
    }

    /**
     * {@inheritDoc}
     */
    override fun unbindViews() {
        unbinder.unbind()
    }

    /**
     * {@inheritDoc}
     */
    override fun hideSwitchForPinnedFilter() {
        pinnedSwitch.visibility = View.GONE
        pinnedSwitcherDivider.visibility = View.GONE
    }

    /**
     * {@inheritDoc}
     */
    override fun showSwitchForPinnedFilter() {
        pinnedSwitch.visibility = View.VISIBLE
        pinnedSwitcherDivider.visibility = View.VISIBLE
    }
}
