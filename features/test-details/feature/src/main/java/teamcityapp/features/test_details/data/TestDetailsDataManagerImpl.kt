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

package teamcityapp.features.test_details.data

import android.text.Html
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import teamcityapp.features.test_details.repository.TestDetailsRepository
import javax.inject.Inject

class TestDetailsDataManagerImpl @Inject constructor(
    private val repository: TestDetailsRepository
) : TestDetailsDataManager {

    private val subscriptions = CompositeDisposable()

    /**
     * {@inheritDoc}
     */
    override fun loadData(
        onSuccess: (test: String) -> Unit,
        onError: () -> Unit,
        url: String
    ) {
        subscriptions.clear()
        repository.testOccurrence(url)
            .subscribeOn(Schedulers.io())
            .map { Html.escapeHtml(it.details) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { onSuccess(it) },
                onError = { onError() }
            )
            .addTo(subscriptions)
    }

    /**
     * {@inheritDoc}
     */
    override fun unsubscribe() {
        subscriptions.clear()
    }
}
