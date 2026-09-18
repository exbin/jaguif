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
package org.exbin.jaguif.addon.catalog.operation;

import java.util.ResourceBundle;
import org.exbin.jaguif.App;
import org.jspecify.annotations.NullMarked;
import org.exbin.jaguif.addon.catalog.api.AddonCatalogService;
import org.exbin.jaguif.addon.manager.api.UpdateAvailabilityManagement;
import org.exbin.jaguif.language.api.LanguageModuleApi;
import org.exbin.jaguif.operation.api.CancellableOperation;
import org.exbin.jaguif.operation.api.TitledOperation;

/**
 * Operation to get available updates from catalog.
 */
@NullMarked
public class CatalogAvailableUpdatesOperation implements Runnable, CancellableOperation, TitledOperation {

    protected final ResourceBundle resourceBundle = App.getModule(LanguageModuleApi.class).getBundle(CatalogAvailableUpdatesOperation.class);
    protected final UpdateAvailabilityManagement updateAvailabilityManager;
    protected final AddonCatalogService addonCatalogService;
    protected final int catalogRevision;
    protected final Output output;
    protected boolean cancelled = false;

    public CatalogAvailableUpdatesOperation(AddonCatalogService addonCatalogService, UpdateAvailabilityManagement updateAvailabilityManager, int catalogRevision, Output output) {
        this.addonCatalogService = addonCatalogService;
        this.updateAvailabilityManager = updateAvailabilityManager;
        this.catalogRevision = catalogRevision;
        this.output = output;
    }

    @Override
    public void run() {
        if (catalogRevision > updateAvailabilityManager.getRevision()) {
            UpdateAvailabilityOperation availabilityOperation = new UpdateAvailabilityOperation(addonCatalogService);
            availabilityOperation.run();
            // TODO
            // updateAvailabilityManager.setLatestVersion(catalogRevision, availabilityOperation.getLatestVersions());
            // updateAvailabilityManager.writeConfigFile();
            output.latestVersionsChanged();
        }
    }

    @Override
    public void cancelOperation() {
        cancelled = true;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public String getTitle() {
        return resourceBundle.getString("operation.name");
    }

    @NullMarked
    public interface Output {

        void latestVersionsChanged();
    }
}
