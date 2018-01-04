/*
 * Copyright (C) 2015 The Android Open Source Project
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

package com.uc.android.camera.one.v2.common;

import android.hardware.camera2.CaptureRequest;

import com.uc.android.camera.one.OneCameraCharacteristics.FaceDetectMode;
import com.uc.android.camera.one.OneCameraCharacteristics.SupportedHardwareLevel;
import com.google.common.base.Supplier;

/**
 * Select a control mode based on the HdrSettings and face detection modes.
 */
public class ControlModeSelector implements Supplier<Integer> {
    private final Supplier<Boolean> mHdrSetting;
    private final Supplier<FaceDetectMode> mFaceDetectMode;
    private final SupportedHardwareLevel mSupportedHardwareLevel;

    public ControlModeSelector(
          Supplier<Boolean> hdrSetting,
          Supplier<FaceDetectMode> faceDetectMode,
          SupportedHardwareLevel supportedHardwareLevel) {
        mHdrSetting = hdrSetting;
        mFaceDetectMode = faceDetectMode;
        mSupportedHardwareLevel = supportedHardwareLevel;
    }

    @Override
    public Integer get() {
        if (mSupportedHardwareLevel == SupportedHardwareLevel.LEGACY) {
            if (mHdrSetting.get()) {
                return CaptureRequest.CONTROL_MODE_USE_SCENE_MODE;
            }
        }

        if (mFaceDetectMode.get() == FaceDetectMode.FULL ||
              mFaceDetectMode.get() == FaceDetectMode.SIMPLE) {
            return CaptureRequest.CONTROL_MODE_USE_SCENE_MODE;
        }

        return CaptureRequest.CONTROL_MODE_AUTO;
    }
}