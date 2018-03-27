/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.uc.caseview.layout;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.uc.android.camera.CaptureLayoutHelper;
import com.uc.android.camera.ShutterButton;
import com.uc.android.camera.debug.Log;
import com.uc.android.camera.ui.PreviewOverlay;
import com.uc.android.camera.ui.TouchCoordinate;
import com.uc.caseview.R;

/**
 * ModeOptionsOverlay is a FrameLayout which positions mode options in
 * in the bottom of the preview that is visible above the bottom bar.
 */
public class CompareItemActionsOverlay extends FrameLayout
    implements CompareItemOverlay.OnCompareItemTouchedListener{

    private final static Log.Tag TAG = new Log.Tag("ModeOptionsOverlay");

    private static final int BOTTOMBAR_OPTIONS_TIMEOUT_MS = 2000;
    private static final int BOTTOM_RIGHT = Gravity.BOTTOM | Gravity.RIGHT;
    private static final int TOP_RIGHT = Gravity.TOP | Gravity.RIGHT;

    private CompareItemActionsBar mActionsBar;
    // need a reference to set the onClickListener and fix the layout gravity on orientation change
    private ImageView mActionsBarToggle;
    private CaptureLayoutHelper mCaptureLayoutHelper = null;

    public CompareItemActionsOverlay(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Whether the mode options are hidden.
     */
    public boolean isActionsBarHidden() {
        return mActionsBar.isHiddenOrHiding();
    }

    /**
     * Gets the current width of the mode options toggle including the three dots and various mode
     * option indicators.
     */
    public float getActionsBarToggleWidth() {
        return mActionsBarToggle.getWidth();
    }

    /**
     * Sets a capture layout helper to query layout rect from.
     */
    public void setCaptureLayoutHelper(CaptureLayoutHelper helper) {
        mCaptureLayoutHelper = helper;
    }

    public void setToggleClickable(boolean clickable) {
        mActionsBarToggle.setClickable(clickable);
    }

    public void showExposureOptions() {
        mActionsBar.showExposureOptions();
    }

    /**
     * Sets the mode options listener.
     *
     * @param listener The listener to be set.
     */
    public void setItemActionListener(CompareItemActionsBar.Listener listener) {
        mActionsBar.setListener(listener);
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
        mActionsBar =  findViewById(R.id.compare_item_options);
        mActionsBar.setClickable(true);
        mActionsBar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                closeActionsBar();
            }
        });

        mActionsBarToggle = findViewById(R.id.tool_box_toggle);
        mActionsBarToggle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mActionsBar.animateVisible();
            }
        });
        mActionsBar.setViewToShowHide(mActionsBarToggle);
    }

    @Override
    public void onItemTouched(MotionEvent ev) {
        closeActionsBar();
    }

    /**
     * Schedule (or re-schedule) the options menu to be closed after a number
     * of milliseconds.  If the options menu is already closed, nothing is
     * scheduled.
     */
    public void closeActionsBar() {
        mActionsBar.animateHidden();
    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        checkOrientation(configuration.orientation);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mCaptureLayoutHelper == null) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            Log.e(TAG, "Capture layout helper needs to be set first.");
        } else {
            RectF uncoveredPreviewRect = mCaptureLayoutHelper.getUncoveredPreviewRect();
            super.onMeasure(MeasureSpec.makeMeasureSpec(
                            (int) uncoveredPreviewRect.width(), MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec((int) uncoveredPreviewRect.height(),
                            MeasureSpec.EXACTLY)
            );
        }
    }

    /**
     * Set the layout gravity of the child layout to be bottom or top right
     * depending on orientation.
     */
    private void checkOrientation(int orientation) {
        final boolean isPortrait = (Configuration.ORIENTATION_PORTRAIT == orientation);

        final int modeOptionsDimension = (int) getResources()
            .getDimension(R.dimen.mode_options_height);

        LayoutParams modeOptionsParams
            = (LayoutParams) mActionsBar.getLayoutParams();
        LayoutParams modeOptionsToggleParams
            = (LayoutParams) mActionsBarToggle.getLayoutParams();

        if (isPortrait) {
            modeOptionsParams.height = modeOptionsDimension;
            modeOptionsParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            modeOptionsParams.gravity = Gravity.BOTTOM;

            modeOptionsToggleParams.gravity = BOTTOM_RIGHT;
        } else {
            modeOptionsParams.width = modeOptionsDimension;
            modeOptionsParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
            modeOptionsParams.gravity = Gravity.RIGHT;

            modeOptionsToggleParams.gravity = TOP_RIGHT;
        }

        requestLayout();
    }
}
