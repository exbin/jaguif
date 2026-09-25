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

import java.util.ArrayList;
import java.util.List;
import org.jspecify.annotations.NullMarked;
import org.exbin.jaguif.App;
import org.exbin.jaguif.ModuleProvider;
import org.exbin.jaguif.addon.AddonModuleFileLocation;
import org.exbin.jaguif.addon.manager.api.AddonRecord;
import org.exbin.jaguif.addon.manager.api.RepositoryAddonRecord;
import org.exbin.jaguif.addon.manager.api.DependencyRecord;
import org.exbin.jaguif.addon.update.api.AddonUpdateModuleApi;
import org.exbin.jaguif.basic.BasicModuleProvider;
import org.exbin.jaguif.basic.ModuleRecord;
import org.exbin.jaguif.addon.update.api.AddonUpdateChangesManagement;

/**
 * Addons target state including queued changes.
 */
@NullMarked
public class AddonsState {

    protected final List<AddonRecord> installedAddons = new ArrayList<>();
    protected final UpdateAvailabilityManager availableModuleUpdates = new UpdateAvailabilityManager();
    protected final AddonUpdateChangesManagement addonUpdateChanges;
    protected ApplicationModulesUsage applicationModulesUsage;

    public AddonsState() {
        AddonUpdateModuleApi addonUpdateModule = App.getModule(AddonUpdateModuleApi.class);
        addonUpdateChanges = addonUpdateModule.createUpdateChangesManager();
    }

    public void init() {
        availableModuleUpdates.readConfigFile();
        addonUpdateChanges.readConfig();

        ModuleProvider moduleProvider = App.getModuleProvider();
        if (moduleProvider instanceof BasicModuleProvider) {
            List<ModuleRecord> basicModulesList = ((BasicModuleProvider) moduleProvider).getModulesList();
            for (ModuleRecord moduleRecord : basicModulesList) {
                RepositoryAddonRecord addonRecord = new RepositoryAddonRecord(moduleRecord.getModuleId(), moduleRecord.getName());
                addonRecord.setInstalled(true);
                addonRecord.setFileLocation(AddonModuleFileLocation.ADDON);
                addonRecord.setVersion(moduleRecord.getVersion());
                addonRecord.setProvider(moduleRecord.getProvider().orElse(null));
                addonRecord.setHomepage(moduleRecord.getHomepage().orElse(null));
                addonRecord.setDescription(moduleRecord.getDescription().orElse(null));
                addonRecord.setIcon(moduleRecord.getIcon().orElse(null));
                List<DependencyRecord> dependencyRecords = new ArrayList<>();
                for (String dependencyModuleId : moduleRecord.getDependencyModuleIds()) {
                    dependencyRecords.add(new DependencyRecord(dependencyModuleId));
                }
                for (String dependencyLibraryId : moduleRecord.getDependencyLibraries()) {
                    dependencyRecords.add(new DependencyRecord(DependencyRecord.Type.JAR_LIBRARY, dependencyLibraryId));
                }
                addonRecord.setDependencies(dependencyRecords);
                installedAddons.add(addonRecord);
                /*System.out.println(moduleRecord.getModuleId() + "," + moduleRecord.getName() + "," + moduleRecord.getDescription().orElse("") + "," + moduleRecord.getVersion() + "," + moduleRecord.getHomepage().orElse(""));
                for (DependencyRecord dependency : dependencyRecords) {
                    System.out.println("- " + dependency.getType().name() + ", " + dependency.getId());
                } */
            }
            applicationModulesUsage = new ApplicationModulesUsage() {
                @Override
                public boolean hasModule(String moduleId) {
                    return ((BasicModuleProvider) moduleProvider).hasModule(moduleId);
                }

                @Override
                public boolean hasLibrary(String libraryFileName) {
                    return ((BasicModuleProvider) moduleProvider).hasLibrary(libraryFileName);
                }
            };
        }
    }

    public List<AddonRecord> getInstalledAddons() {
        return installedAddons;
    }

    public UpdateAvailabilityManager getAvailableModuleUpdates() {
        return availableModuleUpdates;
    }

    public AddonUpdateChangesManagement getAddonUpdateChanges() {
        return addonUpdateChanges;
    }

    public ApplicationModulesUsage getApplicationModulesUsage() {
        return applicationModulesUsage;
    }

    public boolean isModuleInstalled(String moduleId) {
        return addonUpdateChanges.hasInstallAddon(moduleId) && !addonUpdateChanges.hasRemoveAddon(moduleId);
    }

    public boolean isModuleRemoved(String moduleId) {
        return addonUpdateChanges.hasRemoveAddon(moduleId) && !addonUpdateChanges.hasInstallAddon(moduleId);
    }

    public void addUpdateAvailabilityListener(UpdateAvailabilityManager.AvailableModulesChangeListener listener) {
        availableModuleUpdates.addChangeListener(listener);
    }
}
