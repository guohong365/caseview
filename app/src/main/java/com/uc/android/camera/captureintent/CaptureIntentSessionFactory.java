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

package com.uc.android.camera.captureintent;

import android.location.Location;

import com.uc.android.camera.session.CaptureSession;
import com.uc.android.camera.session.CaptureSessionFactory;
import com.uc.android.camera.session.CaptureSessionManager;
import com.uc.android.camera.session.SessionNotifier;

public class CaptureIntentSessionFactory implements CaptureSessionFactory {
    public CaptureIntentSessionFactory() {
    }

    @Override
    public CaptureSession createNewSession(CaptureSessionManager sessionManager,
                                           SessionNotifier sessionNotifier, String title, long sessionStartTime,
                                           Location location) {
        return new CaptureIntentSession(title, location, sessionManager, sessionNotifier);
    }
}
