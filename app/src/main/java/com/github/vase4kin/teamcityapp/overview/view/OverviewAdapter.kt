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

package com.github.vase4kin.teamcityapp.overview.view

import com.github.vase4kin.teamcityapp.base.list.adapter.BaseAdapter
import com.github.vase4kin.teamcityapp.base.list.view.BaseViewHolder
import com.github.vase4kin.teamcityapp.base.list.view.ViewHolderFactory
import com.github.vase4kin.teamcityapp.overview.data.OverviewDataModel

/**
 * Adapter to handle build elements
 */
class OverviewAdapter(viewHolderFactories: Map<Int, ViewHolderFactory<OverviewDataModel>>) :
    BaseAdapter<OverviewDataModel>(viewHolderFactories) {

    var viewListener: OverviewView.ViewListener? = null

    /**
     * {@inheritDoc}
     */
    override fun onBindViewHolder(holder: BaseViewHolder<OverviewDataModel>, position: Int) {
        super.onBindViewHolder(holder, position)
        val header = dataModel.getHeaderName(position)
        val description = dataModel.getDescription(position)
        when {
            dataModel.isBranchCard(position) -> (holder as OverviewViewHolder).itemView.setOnClickListener {
                viewListener?.onBranchCardClick(description)
            }
            dataModel.isBuildTypeCard(position) -> (holder as OverviewViewHolder).itemView.setOnClickListener {
                viewListener?.onBuildTypeCardClick(description)
            }
            dataModel.isProjectCard(position) -> (holder as OverviewViewHolder).itemView.setOnClickListener {
                viewListener?.onProjectCardClick(description)
            }
            else -> (holder as OverviewViewHolder).itemView.setOnClickListener {
                viewListener?.onCardClick(
                    header,
                    description
                )
            }
        }
    }
}
