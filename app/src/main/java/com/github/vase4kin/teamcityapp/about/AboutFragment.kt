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

package com.github.vase4kin.teamcityapp.about

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.danielstone.materialaboutlibrary.ConvenienceBuilder
import com.danielstone.materialaboutlibrary.MaterialAboutFragment
import com.danielstone.materialaboutlibrary.items.MaterialAboutActionItem
import com.danielstone.materialaboutlibrary.model.MaterialAboutCard
import com.danielstone.materialaboutlibrary.model.MaterialAboutList
import com.github.vase4kin.teamcityapp.BuildConfig
import com.github.vase4kin.teamcityapp.R
import com.github.vase4kin.teamcityapp.api.Repository
import dagger.android.support.AndroidSupportInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AboutFragment : MaterialAboutFragment() {

    @Inject
    lateinit var repository: Repository

    private var listener: AboutActivityLoadingListener? = null
    private val subscriptions: CompositeDisposable = CompositeDisposable()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
        if (context is AboutActivityLoadingListener) {
            listener = context
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadServerInfo()
    }

    override fun onDestroyView() {
        subscriptions.clear()
        super.onDestroyView()
    }

    override fun getMaterialAboutList(context: Context): MaterialAboutList = MaterialAboutList()

    override fun getTheme(): Int = R.style.AppTheme_MaterialAboutActivity_Fragment

    private fun loadServerInfo() {
        repository.serverInfo().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { listener?.showLoader() }
            .doFinally { listener?.hideLoader() }
            .subscribeBy(
                onSuccess = {
                    val serverInfo = createServerInfoCard(it.version, it.webUrl)
                    val materialAboutList = createMaterialAboutList(requireActivity(), serverInfo)
                    list.cards.clear()
                    list.cards.addAll(materialAboutList.cards)
                    refreshMaterialAboutList()
                },
                onError = {
                    val materialAboutList = createMaterialAboutList(requireActivity())
                    list.cards.clear()
                    list.cards.addAll(materialAboutList.cards)
                    refreshMaterialAboutList()
                }
            )
            .addTo(subscriptions)
    }

    private fun createServerInfoCard(
        version: String,
        serverUrl: String
    ): MaterialAboutCard {
        val activity = requireActivity()
        val serverInfo = MaterialAboutCard.Builder()
        serverInfo.title(R.string.about_app_text_server_info)
        serverInfo.addItem(
            MaterialAboutActionItem.Builder()
                .text(getString(R.string.version))
                .icon(R.drawable.ic_info_outline_black_24dp)
                .subText(version)
                .build()
        )
        val serverUrlItem = MaterialAboutActionItem.Builder()
            .text(R.string.about_app_text_server_url)
            .subText(serverUrl)
            .icon(R.drawable.ic_web_black_24dp)
            .setOnClickAction {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(serverUrl)
                activity.startActivity(intent)
            }
        serverInfo.addItem(serverUrlItem.build())
        return serverInfo.build()
    }

    private fun createMaterialAboutList(
        activity: Activity,
        serverCard: MaterialAboutCard? = null
    ): MaterialAboutList {
        val appCardBuilder = MaterialAboutCard.Builder()
        appCardBuilder.title(R.string.about_app_text_app)
        appCardBuilder.addItem(
            MaterialAboutActionItem.Builder()
                .text(getString(R.string.version))
                .icon(R.drawable.ic_info_outline_black_24dp)
                .subText(BuildConfig.VERSION_NAME)
                .build()
        )
            .addItem(
                ConvenienceBuilder.createRateActionItem(
                    activity,
                    activity.getDrawable(R.drawable.ic_star_border_black_24dp),
                    getString(R.string.about_app_text_rate_app),
                    null
                )
            )
            .addItem(MaterialAboutActionItem.Builder()
                .text(R.string.about_app_text_found_issue)
                .subText(R.string.about_app_subtext_found_issue)
                .icon(R.drawable.ic_question_answer_black_24dp)
                .setOnClickAction {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(getString(R.string.about_app_url_found_issue))
                    activity.startActivity(intent)
                }
                .build())

        val miscCardBuilder = MaterialAboutCard.Builder()
        miscCardBuilder.title(R.string.about_app_text_dev)
        miscCardBuilder
            .addItem(MaterialAboutActionItem.Builder()
                .text(R.string.about_app_text_source_code)
                .icon(R.drawable.ic_github_circle)
                .setOnClickAction {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(getString(R.string.about_app_url_source_code))
                    activity.startActivity(intent)
                }
                .build())
            .addItem(MaterialAboutActionItem.Builder()
                .text(R.string.about_app_text_libraries)
                .icon(R.drawable.ic_github_circle)
                .setOnClickAction { AboutLibrariesActivity.start(activity) }
                .build())

        val authorCardBuilder = MaterialAboutCard.Builder()
        authorCardBuilder.title(R.string.about_app_text_contacts)
        authorCardBuilder.addItem(MaterialAboutActionItem.Builder()
            .text(R.string.about_app_text_web)
            .subText(R.string.about_app_url_web)
            .icon(R.drawable.ic_web_black_24dp)
            .setOnClickAction {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(getString(R.string.about_app_url_web))
                activity.startActivity(intent)
            }
            .build())
            .addItem(
                ConvenienceBuilder.createEmailItem(
                    activity,
                    activity.getDrawable(R.drawable.ic_email_black_24dp),
                    getText(R.string.about_app_text_email),
                    true,
                    getString(R.string.about_app_email),
                    getString(R.string.about_app_email_title)
                )
            )
            .addItem(MaterialAboutActionItem.Builder()
                .text(R.string.about_app_text_privacy)
                .icon(R.drawable.ic_web_black_24dp)
                .setOnClickAction {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(getString(R.string.about_app_url_privacy))
                    activity.startActivity(intent)
                }
                .build())
        return if (serverCard == null) {
            MaterialAboutList(
                appCardBuilder.build(),
                miscCardBuilder.build(),
                authorCardBuilder.build()
            )
        } else {
            MaterialAboutList(
                serverCard,
                appCardBuilder.build(),
                miscCardBuilder.build(),
                authorCardBuilder.build()
            )
        }
    }
}
