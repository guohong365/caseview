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

package com.uc.android.camera.async;

import com.uc.android.camera.util.Callback;

import java.util.concurrent.Executor;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.ThreadSafe;

/**
 * Wraps a not-necessarily-thread-safe callback into a thread-safe callback
 * which runs it on an executor.
 */
@ThreadSafe
@ParametersAreNonnullByDefault
public class ExecutorCallback<T> implements Updatable<T> {
    private final Callback<T> mCallback;
    private final Executor mExecutor;

    /**
     * @param callback The callback to wrap.
     * @param executor The executor on which to invoke the callback.
     */
    public ExecutorCallback(Callback<T> callback, Executor executor) {
        mCallback = callback;
        mExecutor = executor;
    }

    @Override
    public void update(final T t) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mCallback.onCallback(t);
            }
        });
    }
}