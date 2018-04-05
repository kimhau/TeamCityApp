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

package com.github.vase4kin.teamcityapp.buildlog.dagger;

import android.content.Context;

import com.github.vase4kin.teamcityapp.buildlog.data.BuildLogInteractor;
import com.github.vase4kin.teamcityapp.buildlog.data.BuildLogInteractorImpl;
import com.github.vase4kin.teamcityapp.buildlog.view.BuildLogWebViewClient;
import com.github.vase4kin.teamcityapp.buildlog.view.UnsafeBuildLogWebViewClient;
import com.github.vase4kin.teamcityapp.storage.SharedUserStorage;

import dagger.Module;
import dagger.Provides;

@Module
public class BuildLogInteractorModule {

    @Provides
    BuildLogInteractor providesBuildLogInteractor(Context context, SharedUserStorage sharedUserStorage) {
        return new BuildLogInteractorImpl(context, sharedUserStorage.getActiveUser());
    }

    @Provides
    BuildLogWebViewClient providesBuildLogWebViewClient(SharedUserStorage sharedUserStorage) {
        return !sharedUserStorage.getActiveUser().isSslDisabled()
                ? new BuildLogWebViewClient()
                : new UnsafeBuildLogWebViewClient();
    }
}
