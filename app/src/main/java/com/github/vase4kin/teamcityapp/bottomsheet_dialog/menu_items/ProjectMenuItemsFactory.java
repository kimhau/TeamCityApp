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

package com.github.vase4kin.teamcityapp.bottomsheet_dialog.menu_items;

import android.content.Context;

import com.github.vase4kin.teamcityapp.R;
import com.github.vase4kin.teamcityapp.bottomsheet_dialog.model.BottomSheetItem;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.MaterialCommunityIcons;

import java.util.List;

/**
 * Impl of {@link MenuItemsFactory} for project menu
 */
public class ProjectMenuItemsFactory extends DefaultMenuItemsFactory {

    public ProjectMenuItemsFactory(Context context, List<String> descriptions) {
        super(context, descriptions);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BottomSheetItem> createMenuItems() {
        List<BottomSheetItem> list = super.createMenuItems();
        list.add(new BottomSheetItem(BottomSheetItem.TYPE_PROJECT, getString(R.string.build_element_open_project), getDescription(), new IconDrawable(getContext(), MaterialCommunityIcons.mdi_open_in_app)));
        return list;
    }
}
