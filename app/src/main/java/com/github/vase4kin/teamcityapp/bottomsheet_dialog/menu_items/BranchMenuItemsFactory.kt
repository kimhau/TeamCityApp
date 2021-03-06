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

package com.github.vase4kin.teamcityapp.bottomsheet_dialog.menu_items

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.core.content.ContextCompat
import com.github.vase4kin.teamcityapp.R
import com.github.vase4kin.teamcityapp.bottomsheet_dialog.model.BottomSheetItem

/**
 * Impl of [MenuItemsFactory] for branch menu
 */
class BranchMenuItemsFactory(context: Context, descriptions: List<String>) :
    DefaultMenuItemsFactory(context, descriptions) {

    /**
     * {@inheritDoc}
     */
    override fun createMenuItems(): List<BottomSheetItem> {
        val list = super.createMenuItems().toMutableList()
        list.add(
            BottomSheetItem(
                BottomSheetItem.TYPE_BRANCH,
                getString(R.string.build_element_show_all_builds_built_branch),
                description,
                ContextCompat.getDrawable(context, R.drawable.ic_list_black_24dp) ?: ColorDrawable(
                    Color.TRANSPARENT
                )
            )
        )
        return list
    }
}
