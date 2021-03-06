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

package com.github.vase4kin.teamcityapp.changes.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.vase4kin.teamcityapp.R
import com.github.vase4kin.teamcityapp.base.extractor.BundleExtractorValues
import com.github.vase4kin.teamcityapp.changes.presenter.ChangesPresenterImpl
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

/**
 * Fragment to manage changes
 */
class ChangesFragment : Fragment() {

    @Inject
    lateinit var presenter: ChangesPresenterImpl

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AndroidSupportInjection.inject(this)
        presenter.onViewsCreated()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onViewsDestroyed()
    }

    companion object {

        fun newInstance(url: String): Fragment {
            val fragment = ChangesFragment()
            val args = Bundle()
            args.putString(BundleExtractorValues.URL, url)
            fragment.arguments = args
            return fragment
        }
    }
}
