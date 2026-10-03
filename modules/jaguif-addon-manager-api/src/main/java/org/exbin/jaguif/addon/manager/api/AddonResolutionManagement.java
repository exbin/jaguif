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
package org.exbin.jaguif.addon.manager.api;

import java.net.URL;
import org.jspecify.annotations.NullMarked;

/**
 * Addon related resolution management.
 */
@NullMarked
public interface AddonResolutionManagement {

    /**
     * Resolves addon installation request.
     *
     * @param processing operations processing queue
     * @param moduleId module id
     * @throws AddonResolutionException when service fails
     */
    void installAddon(AddonOperationsProcessing processing, String moduleId) throws AddonResolutionException;

    /**
     * Resolves addon update request.
     *
     * @param processing operations processing queue
     * @param moduleId module id
     * @throws AddonResolutionException when service fails
     */
    void updateAddon(AddonOperationsProcessing processing, String moduleId) throws AddonResolutionException;

    /**
     * Resolves dependency installation request.
     *
     * @param processing operations processing queue
     * @param moduleId module id
     * @throws AddonResolutionException when service fails
     */
    void installDependency(AddonOperationsProcessing processing, String moduleId) throws AddonResolutionException;
    
    /**
     * Returns simplified record of specific addon with dependency / license
     * info only.
     *
     * @param moduleId module id
     * @return addon record
     * @throws AddonResolutionException when service fails
     */
    RepositoryAddonRecord getAddonDependency(String moduleId) throws AddonResolutionException;

    /**
     * Returns remote download URI for module file.
     *
     * @param remoteFilePath remote file path
     * @throws AddonResolutionException when service fails
     * @return download URL
     */
    URL getFileDownloadUrl(String remoteFilePath) throws AddonResolutionException;

    /**
     * Returns remote download URI for license file.
     *
     * @param remoteFilePath remote file path
     * @throws AddonResolutionException when service fails
     * @return download URL
     */
    URL getLicenseDownloadUrl(String remoteFilePath) throws AddonResolutionException;
}
