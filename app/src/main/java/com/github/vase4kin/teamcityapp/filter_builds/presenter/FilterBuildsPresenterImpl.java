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
package com.github.vase4kin.teamcityapp.filter_builds.presenter;

import com.github.vase4kin.teamcityapp.account.create.data.OnLoadingListener;
import com.github.vase4kin.teamcityapp.filter_builds.router.FilterBuildsRouter;
import com.github.vase4kin.teamcityapp.filter_builds.view.FilterBuildsView;
import com.github.vase4kin.teamcityapp.runbuild.interactor.BranchesInteractor;
import com.github.vase4kin.teamcityapp.runbuild.view.BranchesComponentView;

import java.util.List;

import javax.inject.Inject;

/**
 * Impl of {@link FilterBuildsPresenter}
 */
public class FilterBuildsPresenterImpl implements FilterBuildsPresenter, FilterBuildsView.ViewListener {

    private FilterBuildsView mView;
    private FilterBuildsRouter mRouter;
    private BranchesInteractor mBranchesInteractor;
    private BranchesComponentView mBranchesComponentView;

    @Inject
    FilterBuildsPresenterImpl(FilterBuildsView view,
                              FilterBuildsRouter router,
                              BranchesInteractor branchesInteractor,
                              BranchesComponentView branchesComponentView) {
        this.mView = view;
        this.mRouter = router;
        this.mBranchesInteractor = branchesInteractor;
        this.mBranchesComponentView = branchesComponentView;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate() {
        mView.initViews(this);
        mBranchesComponentView.initViews();
        mBranchesInteractor.loadBranches(new OnLoadingListener<List<String>>() {
            @Override
            public void onSuccess(List<String> branches) {
                mBranchesComponentView.hideBranchesLoadingProgress();
                if (branches.size() == 1) {
                    // set this branch as default and disable the field
                    mBranchesComponentView.setupAutoCompleteForSingleBranch(branches);
                } else {
                    // for all other leave the hint as default
                    mBranchesComponentView.setupAutoComplete(branches);
                }
                mBranchesComponentView.showBranchesAutoComplete();
            }

            @Override
            public void onFail(String errorMessage) {
                mBranchesComponentView.hideBranchesLoadingProgress();
                mBranchesComponentView.showNoBranchesAvailable();
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onResume() {
        //track view
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDestroy() {
        mView.unbindViews();
        mBranchesComponentView.unbindViews();
        mBranchesInteractor.unsubscribe();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onBackPressed() {
        mRouter.closeOnBackButtonPressed();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onClick() {
        mRouter.closeOnCancel();
    }
}
