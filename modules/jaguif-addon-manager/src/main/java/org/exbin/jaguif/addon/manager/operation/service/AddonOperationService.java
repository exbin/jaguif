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
package org.exbin.jaguif.addon.manager.operation.service;

import java.awt.Component;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jspecify.annotations.NullMarked;
import org.exbin.jaguif.App;
import org.exbin.jaguif.addon.manager.api.RepositoryAddonRecord;
import org.exbin.jaguif.addon.manager.operation.AddonModificationsOperation;
import org.exbin.jaguif.language.api.LanguageModuleApi;
import org.exbin.jaguif.addon.manager.AddonManager;
import org.exbin.jaguif.addon.manager.api.operation.AddonOperation;
import org.exbin.jaguif.addon.manager.ApplicationModulesUsage;
import org.exbin.jaguif.addon.manager.api.AddonRecord;
import org.exbin.jaguif.addon.manager.api.AddonResolutionService;
import org.exbin.jaguif.addon.manager.api.AddonResolutionServiceException;
import org.exbin.jaguif.addon.manager.api.operation.CartOperation;
import org.jspecify.annotations.Nullable;
import org.exbin.jaguif.addon.update.api.AddonUpdateChangesManagement;

/**
 * Addon operation service.
 */
@NullMarked
public class AddonOperationService {

    protected java.util.ResourceBundle resourceBundle = App.getModule(LanguageModuleApi.class).getBundle(AddonOperationService.class);

    protected final AddonManager addonManager;
    protected @Nullable AddonResolutionService resolutionService;

    public AddonOperationService(AddonManager addonManager) {
        this.addonManager = addonManager;
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    public void setResolutionService(AddonResolutionService resolutionService) {
        this.resolutionService = resolutionService;
    }

    public AddonModificationsOperation performAddonOperations(List<CartOperation> operations) {
        AddonModificationsOperation modifications = createOperation();
        for (CartOperation operation : operations) {
            if (operation instanceof AddonOperation) {
                throw new IllegalStateException();
            }
            
            AddonOperation addonOperation = (AddonOperation) operation;
            switch (addonOperation.getVariant()) {
                case INSTALL:
                    modifications.installItem(addonOperation.getRecord());
                    break;
                case UPDATE:
                    AddonRecord item = addonOperation.getRecord();
                    modifications.updateItem(item, item);
                    break;
                case REMOVE:
                    modifications.removeItem(addonOperation.getRecord());
                    break;
            }
        }
        return modifications;
    }

    public AddonModificationsOperation installItem(AddonRecord item, Component parentComponent) {
        AddonModificationsOperation operation = createOperation();
        operation.installItem(item);
        return operation;
    }

    public AddonModificationsOperation updateItem(AddonRecord item) {
        AddonModificationsOperation operation = createOperation();
        RepositoryAddonRecord addonRecord;
        try {
            addonRecord = resolutionService.getAddonDependency(item.getId());
            operation.updateItem(addonRecord, item);
        } catch (AddonResolutionServiceException ex) {
            Logger.getLogger(AddonOperationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return operation;
    }

    public AddonModificationsOperation removeItem(AddonRecord item) {
        AddonModificationsOperation operation = createOperation();
        operation.removeItem(item);
        return operation;
    }

    public AddonModificationsOperation installAddons(Set<String> toInstall) {
        List<AddonRecord> installedAddons = addonManager.getInstalledAddons();
        AddonModificationsOperation operation = createOperation();
        if (toInstall.isEmpty()) {
            for (AddonRecord addon : installedAddons) {
                if (addon.isUpdateAvailable()) {
                    RepositoryAddonRecord addonRecord;
                    try {
                        addonRecord = resolutionService.getAddonDependency(addon.getId());
                        operation.updateItem(addonRecord, addon);
                    } catch (AddonResolutionServiceException ex) {
                        Logger.getLogger(AddonOperationService.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        } else {
            for (String addonId : toInstall) {
                RepositoryAddonRecord addonRecord;
                try {
                    addonRecord = resolutionService.getAddonDependency(addonId);
                    operation.installItem(addonRecord);
                } catch (AddonResolutionServiceException ex) {
                    Logger.getLogger(AddonOperationService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return operation;
    }

    public AddonModificationsOperation updateAddons(Set<String> toUpdate) {
        List<AddonRecord> installedAddons = addonManager.getInstalledAddons();
        AddonModificationsOperation operation = createOperation();
        if (toUpdate.isEmpty()) {
            for (AddonRecord addon : installedAddons) {
                if (addon.isUpdateAvailable()) {
                    RepositoryAddonRecord addonRecord;
                    try {
                        addonRecord = resolutionService.getAddonDependency(addon.getId());
                        operation.updateItem(addonRecord, addon);
                    } catch (AddonResolutionServiceException ex) {
                        Logger.getLogger(AddonOperationService.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        } else {
            for (AddonRecord addon : installedAddons) {
                if (toUpdate.contains(addon.getId())) {
                    RepositoryAddonRecord addonRecord;
                    try {
                        addonRecord = resolutionService.getAddonDependency(addon.getId());
                        operation.updateItem(addonRecord, addon);
                    } catch (AddonResolutionServiceException ex) {
                        Logger.getLogger(AddonOperationService.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return operation;
    }

    private AddonModificationsOperation createOperation() {
        AddonUpdateChangesManagement addonUpdateChanges = addonManager.getAddonUpdateChanges();
        ApplicationModulesUsage applicationModulesUsage = addonManager.getApplicationModulesUsage();
        return new AddonModificationsOperation(resolutionService, applicationModulesUsage, addonUpdateChanges);
    }
}
