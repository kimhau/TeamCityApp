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

package com.github.vase4kin.teamcityapp.runningbuilds.view

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.BundleMatchers.hasEntry
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtras
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.github.vase4kin.teamcityapp.R
import com.github.vase4kin.teamcityapp.TeamCityApplication
import com.github.vase4kin.teamcityapp.api.TeamCityService
import com.github.vase4kin.teamcityapp.base.extractor.BundleExtractorValues
import com.github.vase4kin.teamcityapp.buildlist.api.Builds
import com.github.vase4kin.teamcityapp.buildlist.view.BuildListActivity
import com.github.vase4kin.teamcityapp.dagger.components.AppComponent
import com.github.vase4kin.teamcityapp.dagger.components.RestApiComponent
import com.github.vase4kin.teamcityapp.dagger.modules.AppModule
import com.github.vase4kin.teamcityapp.dagger.modules.FakeTeamCityServiceImpl
import com.github.vase4kin.teamcityapp.dagger.modules.Mocks
import com.github.vase4kin.teamcityapp.dagger.modules.RestApiModule
import com.github.vase4kin.teamcityapp.helper.CustomIntentsTestRule
import com.github.vase4kin.teamcityapp.helper.RecyclerViewMatcher.withRecyclerView
import com.github.vase4kin.teamcityapp.helper.TestUtils
import com.github.vase4kin.teamcityapp.helper.TestUtils.hasItemsCount
import com.github.vase4kin.teamcityapp.helper.TestUtils.matchToolbarTitle
import com.github.vase4kin.teamcityapp.home.view.HomeActivity
import io.reactivex.Single
import it.cosenonjaviste.daggermock.DaggerMockRule
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.core.AllOf.allOf
import org.junit.BeforeClass
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Matchers.anyString
import org.mockito.Mockito.`when`
import org.mockito.Spy

@RunWith(AndroidJUnit4::class)
class RunningBuildsFragmentTest {

    @Rule
    @JvmField
    var daggerMockRule: DaggerMockRule<RestApiComponent> = DaggerMockRule(RestApiComponent::class.java, RestApiModule(Mocks.URL))
            .addComponentDependency(AppComponent::class.java, AppModule(InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as TeamCityApplication))
            .set { restApiComponent ->
                val app = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as TeamCityApplication
                app.restApiInjector = restApiComponent
            }

    @Rule
    @JvmField
    var activityRule = CustomIntentsTestRule(HomeActivity::class.java)

    @Spy
    private val teamCityService: TeamCityService = FakeTeamCityServiceImpl()

    companion object {
        @JvmStatic
        @BeforeClass
        fun disableOnboarding() {
            TestUtils.disableOnboarding()
        }
    }

    @Test
    fun testUserCanSeeUpdatedToolbar() {
        activityRule.launchActivity(null)

        // Click on running builds tab
        clickOnRunningbuildsTab()

        // Checking toolbar title
        matchToolbarTitle("Running builds")
    }

    @Ignore("FIX ME")
    @Test
    fun testUserCanSeeSuccessFullyLoadedRunningBuilds() {
        activityRule.launchActivity(null)

        // Click on running builds tab
        clickOnRunningbuildsTab()

        // List has item with header
        onView(withId(R.id.running_builds_recycler_view)).check(hasItemsCount(2))
        // Checking header
        onView(withId(R.id.section_text)).check(matches(withText("project name - build type name")))
        // Checking adapter item
        onView(withRecyclerView(R.id.running_builds_recycler_view).atPositionOnView(1, R.id.itemTitle)).check(matches(withText("Running tests")))
        onView(withRecyclerView(R.id.running_builds_recycler_view).atPositionOnView(1, R.id.itemSubTitle)).check(matches(withText("refs/heads/master")))
        onView(withRecyclerView(R.id.running_builds_recycler_view).atPositionOnView(1, R.id.buildNumber)).check(matches(withText("#2458")))
    }

    @Ignore("FIX ME")
    @Test
    fun testUserCanClickOnSection() {
        activityRule.launchActivity(null)

        // Click on running builds tab
        clickOnRunningbuildsTab()

        // Click on header header
        onView(withId(R.id.section_text))
                .check(matches(withText("project name - build type name")))
                .perform(click())

        // Check activity been opned
        intended(allOf(
                hasComponent(BuildListActivity::class.java.name),
                hasExtras(allOf(
                        hasEntry(equalTo(BundleExtractorValues.BUILD_LIST_FILTER), equalTo<Any>(null)),
                        hasEntry(equalTo(BundleExtractorValues.ID), equalTo("Checkstyle_IdeaInspectionsPullRequest")),
                        hasEntry(equalTo(BundleExtractorValues.NAME), equalTo("build type name"))))))
    }

    @Ignore("FIX ME")
    @Test
    fun testUserCanSeeFailureMessageIfSmthHappendsOnRunningBuildsLoading() {
        `when`(teamCityService.listRunningBuilds(anyString(), anyString())).thenReturn(Single.error(RuntimeException("smth bad happend!")))

        activityRule.launchActivity(null)

        // Click on running builds tab
        clickOnRunningbuildsTab()

        // Check badge count
        checkRunningTabBadgeCount("0")

        // Check error
        onView(withText(R.string.error_view_error_text)).check(matches(isDisplayed()))
    }

    @Test
    fun testUserCanSeeEmptyDataMessageIfRunningBuildListIsEmpty() {
        `when`(teamCityService.listRunningBuilds(anyString(), anyString())).thenReturn(Single.just(Builds(0, emptyList())))

        activityRule.launchActivity(null)

        // Click on running builds tab
        clickOnRunningbuildsTab()

        // Check empty view
        onView(withId(R.id.running_empty_title_view)).check(matches(isDisplayed())).check(matches(withText(R.string.empty_list_message_favorite_running_builds)))
    }

    private fun clickOnRunningbuildsTab() {
        onView(withChild(allOf(withId(R.id.bottom_navigation_small_item_title), withText(R.string.running_builds_drawer_item))))
                .perform(click())
    }

    private fun checkRunningTabBadgeCount(count: String) {
        onView(allOf(
                withChild(allOf(withId(R.id.bottom_navigation_notification), withText(count))),
                withChild(allOf(withId(R.id.bottom_navigation_small_item_title), withText(R.string.running_builds_drawer_item))))
        )
                .check(matches(isDisplayed()))
    }
}