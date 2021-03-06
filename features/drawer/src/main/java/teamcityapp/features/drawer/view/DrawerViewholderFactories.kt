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

package teamcityapp.features.drawer.view

import android.view.ViewGroup
import teamcityapp.features.drawer.drawer.DrawerRouter
import teamcityapp.features.drawer.tracker.DrawerTracker

abstract class BaseDrawerViewHolderFactory {

    abstract fun create(parent: ViewGroup): BaseDrawerItemViewHolder
}

class AccountsDividerViewHolderFactory : BaseDrawerViewHolderFactory() {

    override fun create(parent: ViewGroup): BaseDrawerItemViewHolder {
        return AccountsDividerViewHolder(parent)
    }
}

class DividerViewHolderFactory : BaseDrawerViewHolderFactory() {

    override fun create(parent: ViewGroup): BaseDrawerItemViewHolder {
        return DividerViewHolder(parent)
    }
}

class BottomViewHolderFactory(
    private val router: DrawerRouter,
    private val tracker: DrawerTracker
) : BaseDrawerViewHolderFactory() {

    override fun create(parent: ViewGroup): BaseDrawerItemViewHolder {
        return BottomViewHolder(parent, router, tracker)
    }
}

class MenuViewHolderFactory(
    private val router: DrawerRouter,
    private val tracker: DrawerTracker
) : BaseDrawerViewHolderFactory() {

    override fun create(parent: ViewGroup): BaseDrawerItemViewHolder {
        return MenuViewHolder(parent, router, tracker)
    }
}

class AccountViewHolderFactory(
    private val router: DrawerRouter,
    private val tracker: DrawerTracker
) : BaseDrawerViewHolderFactory() {

    override fun create(parent: ViewGroup): BaseDrawerItemViewHolder {
        return AccountViewHolder(parent, router, tracker)
    }
}
