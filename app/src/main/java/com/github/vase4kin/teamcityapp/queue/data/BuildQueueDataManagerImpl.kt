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

package com.github.vase4kin.teamcityapp.queue.data

import com.github.vase4kin.teamcityapp.account.create.data.OnLoadingListener
import com.github.vase4kin.teamcityapp.api.Repository
import com.github.vase4kin.teamcityapp.buildlist.data.BuildListDataManagerImpl
import com.github.vase4kin.teamcityapp.overview.data.BuildDetails
import com.github.vase4kin.teamcityapp.runningbuilds.data.RunningBuildsDataManager
import com.github.vase4kin.teamcityapp.storage.SharedUserStorage
import io.reactivex.Observable

/**
 * Data manager to handle build queue server operations
 */
class BuildQueueDataManagerImpl(
    repository: Repository,
    private val storage: SharedUserStorage
) : BuildListDataManagerImpl(repository, storage), RunningBuildsDataManager {

    /**
     * {@inheritDoc}
     */
    override fun load(loadingListener: OnLoadingListener<List<BuildDetails>>, update: Boolean) {
        val runningBuilds = getBuildDetailsObservable(repository.listQueueBuilds(null, null, update))
            .toSortedList { buildDetails, buildDetails2 ->
                buildDetails.buildTypeId.compareTo(buildDetails2.buildTypeId, ignoreCase = true)
            }
        loadBuildDetailsList(runningBuilds, loadingListener)
    }

    /**
     * {@inheritDoc}
     */
    override fun loadFavorites(loadingListener: OnLoadingListener<List<BuildDetails>>, update: Boolean) {
        val runningBuildsByBuildTypeIds = Observable.fromIterable(storage.favoriteBuildTypeIds)
            .flatMap { buildTypeId ->
                val locator = buildTypeIdLocator(buildTypeId)
                getBuildDetailsObservable(repository.listQueueBuilds(locator, null, update))
            }.toSortedList { buildDetails, buildDetails2 ->
                buildDetails.buildTypeId.compareTo(buildDetails2.buildTypeId, ignoreCase = true)
            }
        loadBuildDetailsList(runningBuildsByBuildTypeIds, loadingListener)
    }

    /**
     * {@inheritDoc}
     */
    override fun loadCount(loadingListener: OnLoadingListener<Int>) {
        loadCount(repository.listQueueBuilds(null, "count", false).map { it.count }, loadingListener)
    }

    /**
     * {@inheritDoc}
     */
    override fun loadFavoritesCount(loadingListener: OnLoadingListener<Int>) {
        val runningBuildsByBuildTypeIds = Observable.fromIterable(storage.favoriteBuildTypeIds)
            .flatMapSingle { buildTypeId ->
                val locator = buildTypeIdLocator(buildTypeId)
                repository.listQueueBuilds(locator, "count", false)
                    .map { it.count }
            }.toList().map { it.sum() }
        loadCount(runningBuildsByBuildTypeIds, loadingListener)
    }
}
