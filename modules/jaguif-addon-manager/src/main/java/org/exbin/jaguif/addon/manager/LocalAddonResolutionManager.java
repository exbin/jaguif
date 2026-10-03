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
package org.exbin.jaguif.addon.manager;

import java.net.URL;
import org.exbin.jaguif.addon.manager.api.AddonOperationsProcessing;
import org.exbin.jaguif.addon.manager.api.RepositoryAddonRecord;
import org.exbin.jaguif.addon.manager.api.AddonResolutionException;
import org.jspecify.annotations.NullMarked;
import org.exbin.jaguif.addon.manager.api.AddonResolutionManagement;
import org.exbin.jaguif.addon.manager.api.LocalAddonModificationType;

/**
 * Local addon resolution manager.
 */
@NullMarked
public class LocalAddonResolutionManager implements AddonResolutionManagement {

    @Override
    public void installAddon(AddonOperationsProcessing processing, String moduleId) throws AddonResolutionException {
        if (processing.isLocalModule(moduleId)) {
            processing.addModification(LocalAddonModificationType.INSTALL_ADDON, moduleId);
            return;
        }
        
        throw new AddonResolutionException("Missing module " + moduleId);
    }

    @Override
    public void updateAddon(AddonOperationsProcessing processing, String moduleId) throws AddonResolutionException {
        if (processing.isLocalModule(moduleId)) {
            return;
        }
        
        throw new AddonResolutionException("Missing module " + moduleId);
    }

    @Override
    public void installDependency(AddonOperationsProcessing processing, String moduleId) throws AddonResolutionException {
        if (processing.isLocalModule(moduleId)) {
            processing.addModification(LocalAddonModificationType.DEPENDENCY_ADDON, moduleId);
            return;
        }
        
        throw new AddonResolutionException("Missing module " + moduleId);
    }

    @Override
    public RepositoryAddonRecord getAddonDependency(String moduleId) throws AddonResolutionException {
        throw new AddonResolutionException("Dependency resolution not available");
    }

    @Override
    public URL getFileDownloadUrl(String remoteFilePath) throws AddonResolutionException {
        throw new AddonResolutionException("Download not supported");
    }

    @Override
    public URL getLicenseDownloadUrl(String remoteFilePath) throws AddonResolutionException {
        throw new AddonResolutionException("Download not supported");
    }
}
