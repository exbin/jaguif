/*
 * Copyright (C) ExBin Project, https://exbin.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.exbin.jaguif.addon.update.service;

import java.net.URL;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.jspecify.annotations.NullMarked;
import org.exbin.jaguif.addon.update.api.VersionNumbers;

/**
 * Check for update service.
 */
@NullMarked
public interface CheckForUpdateService {

    /**
     * Performs check for updates.
     *
     * @return check for updates result
     */
    @NonNull
    CheckForUpdateResult checkForUpdate();

    /**
     * Returns version of the current application.
     *
     * @return version
     */
    @NonNull
    VersionNumbers getCurrentVersion();

    /**
     * Returns version of update if available.
     *
     * @return version of update
     */
    @Nullable
    VersionNumbers getUpdateVersion();

    /**
     * Performs check for updates.
     *
     * @param listener check listener
     */
    void performCheckForUpdates(BackgroundCheckListener listener);

    /**
     * Returns update download URL.
     *
     * @return URL
     */
    @Nullable
    URL getDownloadUrl();

    /**
     * Enumeration of result types.
     */
    public static enum CheckForUpdateResult {
        UPDATE_URL_NOT_SET,
        NO_CONNECTION,
        CONNECTION_ISSUE,
        NOT_FOUND,
        NO_UPDATE_AVAILABLE,
        UPDATE_FOUND
    }

    @NullMarked
    public interface BackgroundCheckListener {

        void checkFinished(CheckForUpdateResult result, VersionNumbers updateVersion);
    }
}
