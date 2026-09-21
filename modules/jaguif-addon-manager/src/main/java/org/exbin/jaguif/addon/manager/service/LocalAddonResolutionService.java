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
package org.exbin.jaguif.addon.manager.service;

import java.net.URL;
import org.exbin.jaguif.addon.manager.api.AddonRecord;
import org.exbin.jaguif.addon.manager.api.AddonResolutionService;
import org.exbin.jaguif.addon.manager.api.AddonResolutionServiceException;
import org.exbin.jaguif.addon.update.api.AddonModification;
import org.jspecify.annotations.NullMarked;

/**
 * Local addon resolution service.
 */
@NullMarked
public class LocalAddonResolutionService implements AddonResolutionService {

    @Override
    public AddonModification getAddonFile(String moduleId) throws AddonResolutionServiceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public AddonRecord getAddonDependency(String moduleId) throws AddonResolutionServiceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public URL getFileDownloadUrl(String remoteFilePath) throws AddonResolutionServiceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public URL getLicenseDownloadUrl(String remoteFilePath) throws AddonResolutionServiceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
