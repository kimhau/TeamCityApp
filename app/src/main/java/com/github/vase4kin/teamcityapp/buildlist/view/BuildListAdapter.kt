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

package com.github.vase4kin.teamcityapp.buildlist.view

import com.github.vase4kin.teamcityapp.base.list.adapter.BaseLoadMoreAdapter
import com.github.vase4kin.teamcityapp.base.list.view.BaseViewHolder
import com.github.vase4kin.teamcityapp.base.list.view.ViewHolderFactory
import com.github.vase4kin.teamcityapp.buildlist.data.BuildListDataModel
import com.github.vase4kin.teamcityapp.buildlist.data.OnBuildListPresenterListener

/**
 * Adapter for builds
 */
class BuildListAdapter(viewHolderFactories: Map<Int, ViewHolderFactory<BuildListDataModel>>) :
    BaseLoadMoreAdapter<BuildListDataModel>(viewHolderFactories) {

    private var onBuildListPresenterListener: OnBuildListPresenterListener? = null

    /**
     * Set [OnBuildListPresenterListener]
     *
     * @param onBuildListPresenterListener - listener to set
     */
    fun setOnBuildListPresenterListener(onBuildListPresenterListener: OnBuildListPresenterListener) {
        this.onBuildListPresenterListener = onBuildListPresenterListener
    }

    override fun onBindViewHolder(holder: BaseViewHolder<BuildListDataModel>, position: Int) {
        super.onBindViewHolder(holder, position)
        // Find the way how to make it through DI
        if (holder is BuildViewHolder) {
            holder.itemView.setOnClickListener {
                onBuildListPresenterListener?.onBuildClick(dataModel.getBuild(position))
            }
        }
    }
}
