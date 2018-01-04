/*
 * Copyright (C) 2013 The Android Open Source Project
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

package com.uc.android.camera.module;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;

import com.uc.android.camera.CaptureModule;
import com.uc.android.camera.PhotoModule;
import com.uc.android.camera.VideoModule;
import com.uc.android.camera.app.AppController;
import com.uc.android.camera.app.ModuleManager;
import com.uc.android.camera.captureintent.CaptureIntentModule;
import com.uc.android.camera.debug.Log;
import com.uc.android.camera.one.OneCamera;
import com.uc.android.camera.one.OneCameraException;
import com.uc.android.camera.one.config.OneCameraFeatureConfig;
import com.uc.android.camera.one.config.OneCameraFeatureConfig.HdrPlusSupportLevel;
import com.uc.android.camera.settings.SettingsScopeNamespaces;
import com.uc.android.camera.util.GcamHelper;
import com.uc.android.camera.util.PhotoSphereHelper;
import com.uc.android.camera.util.RefocusHelper;
import com.uc.caseview.R;

/**
 * A class holding the module information and registers them to
 * {@link com.uc.android.camera.app.ModuleManager}.
 */
public class ModulesInfo {
    private static final Log.Tag TAG = new Log.Tag("ModulesInfo");

    public static void setupModules(Context context, ModuleManager moduleManager,
            OneCameraFeatureConfig config) {
        Resources res = context.getResources();
        int photoModuleId = context.getResources().getInteger(R.integer.camera_mode_photo);
        registerPhotoModule(moduleManager, photoModuleId, SettingsScopeNamespaces.PHOTO,
                config.isUsingCaptureModule());
        moduleManager.setDefaultModuleIndex(photoModuleId);
        registerVideoModule(moduleManager, res.getInteger(R.integer.camera_mode_video),
                SettingsScopeNamespaces.VIDEO);
        if (PhotoSphereHelper.hasLightCycleCapture(context)) {
            registerWideAngleModule(moduleManager, res.getInteger(R.integer.camera_mode_panorama),
                    SettingsScopeNamespaces.PANORAMA);
            registerPhotoSphereModule(moduleManager,
                    res.getInteger(R.integer.camera_mode_photosphere),
                    SettingsScopeNamespaces.PANORAMA);
        }
        if (RefocusHelper.hasRefocusCapture(context)) {
            registerRefocusModule(moduleManager, res.getInteger(R.integer.camera_mode_refocus),
                    SettingsScopeNamespaces.REFOCUS);
        }
        if (GcamHelper.hasGcamAsSeparateModule(config)) {
            registerGcamModule(moduleManager, res.getInteger(R.integer.camera_mode_gcam),
                    SettingsScopeNamespaces.PHOTO,
                    config.getHdrPlusSupportLevel(OneCamera.Facing.BACK));
        }
        int imageCaptureIntentModuleId = res.getInteger(R.integer.camera_mode_capture_intent);
        registerCaptureIntentModule(moduleManager, imageCaptureIntentModuleId,
                SettingsScopeNamespaces.PHOTO, config.isUsingCaptureModule());
    }

    private static void registerPhotoModule(ModuleManager moduleManager, final int moduleId,
            final String namespace, final boolean enableCaptureModule) {
        moduleManager.registerModule(new ModuleManager.ModuleAgent() {

            @Override
            public int getModuleId() {
                return moduleId;
            }

            @Override
            public boolean requestAppForCamera() {
                // The PhotoModule requests the old app camere, while the new
                // capture module is using OneCamera. At some point we'll
                // refactor all modules to use OneCamera, then the new module
                // doesn't have to manage it itself.
                return !enableCaptureModule;
            }

            @Override
            public String getScopeNamespace() {
                return namespace;
            }

            @Override
            public ModuleController createModule(AppController app, Intent intent) {
                Log.v(TAG, "EnableCaptureModule = " + enableCaptureModule);
                return enableCaptureModule ? new CaptureModule(app) : new PhotoModule(app);
            }
        });
    }

    private static void registerVideoModule(ModuleManager moduleManager, final int moduleId,
            final String namespace) {
        moduleManager.registerModule(new ModuleManager.ModuleAgent() {
            @Override
            public int getModuleId() {
                return moduleId;
            }

            @Override
            public boolean requestAppForCamera() {
                return true;
            }

            @Override
            public String getScopeNamespace() {
                return namespace;
            }

            @Override
            public ModuleController createModule(AppController app, Intent intent) {
                return new VideoModule(app);
            }
        });
    }

    private static void registerWideAngleModule(ModuleManager moduleManager, final int moduleId,
            final String namespace) {
        moduleManager.registerModule(new ModuleManager.ModuleAgent() {
            @Override
            public int getModuleId() {
                return moduleId;
            }

            @Override
            public boolean requestAppForCamera() {
                return true;
            }

            @Override
            public String getScopeNamespace() {
                return namespace;
            }

            @Override
            public ModuleController createModule(AppController app, Intent intent) {
                return PhotoSphereHelper.createWideAnglePanoramaModule(app);
            }
        });
    }

    private static void registerPhotoSphereModule(ModuleManager moduleManager, final int moduleId,
            final String namespace) {
        moduleManager.registerModule(new ModuleManager.ModuleAgent() {
            @Override
            public int getModuleId() {
                return moduleId;
            }

            @Override
            public boolean requestAppForCamera() {
                return true;
            }

            @Override
            public String getScopeNamespace() {
                return namespace;
            }

            @Override
            public ModuleController createModule(AppController app, Intent intent) {
                return PhotoSphereHelper.createPanoramaModule(app);
            }
        });
    }

    private static void registerRefocusModule(ModuleManager moduleManager, final int moduleId,
            final String namespace) {
        moduleManager.registerModule(new ModuleManager.ModuleAgent() {
            @Override
            public int getModuleId() {
                return moduleId;
            }

            @Override
            public boolean requestAppForCamera() {
                return true;
            }

            @Override
            public String getScopeNamespace() {
                return namespace;
            }

            @Override
            public ModuleController createModule(AppController app, Intent intent) {
                return RefocusHelper.createRefocusModule(app);
            }
        });
    }

    private static void registerGcamModule(ModuleManager moduleManager, final int moduleId,
            final String namespace, final HdrPlusSupportLevel hdrPlusSupportLevel) {
        moduleManager.registerModule(new ModuleManager.ModuleAgent() {
            @Override
            public int getModuleId() {
                return moduleId;
            }

            @Override
            public boolean requestAppForCamera() {
                return false;
            }

            @Override
            public String getScopeNamespace() {
                return namespace;
            }

            @Override
            public ModuleController createModule(AppController app, Intent intent) {
                return GcamHelper.createGcamModule(app, hdrPlusSupportLevel);
            }
        });
    }

    private static void registerCaptureIntentModule(ModuleManager moduleManager, final int moduleId,
            final String namespace, final boolean enableCaptureModule) {
        moduleManager.registerModule(new ModuleManager.ModuleAgent() {
            @Override
            public int getModuleId() {
                return moduleId;
            }

            @Override
            public boolean requestAppForCamera() {
                return !enableCaptureModule;
            }

            @Override
            public String getScopeNamespace() {
                return namespace;
            }

            @Override
            public ModuleController createModule(AppController app, Intent intent) {
                if(enableCaptureModule) {
                    try {
                        return new CaptureIntentModule(app, intent, namespace);
                    } catch (OneCameraException ignored) {
                    }
                }
                return new PhotoModule(app);
            }
        });
    }
}
