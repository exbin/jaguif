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
package org.exbin.jaguif.addon.update;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jspecify.annotations.Nullable;
import org.jspecify.annotations.NullMarked;
import org.exbin.jaguif.App;
import org.exbin.jaguif.ModuleUtils;
import org.exbin.jaguif.addon.AddonApplication;
import org.exbin.jaguif.addon.AddonModuleFileLocation;
import org.exbin.jaguif.addon.update.api.AddonUpdateModuleApi;
import org.exbin.jaguif.language.api.LanguageModuleApi;
import org.exbin.jaguif.addon.update.api.AddonUpdateChangesManagement;

/**
 * Addon update module.
 */
@NullMarked
public class AddonUpdateModule implements AddonUpdateModuleApi {

    public static final String MODULE_ID = ModuleUtils.getModuleIdByApi(AddonUpdateModule.class);

    public static final String ADDONS_DIRECTORY = "addons";
    public static final String ADDONS_UPDATE_DIRECTORY = "addons_update";
    public static final String ADDONS_CHANGES_FILE = "changes.cfg";
    public static final String FILE_UPDATE = "UPDATE_FILE";
    public static final String FILE_REMOVAL = "REMOVE_FILE";

    private @Nullable ResourceBundle resourceBundle;

    public AddonUpdateModule() {
    }

    public void unregisterModule(String moduleId) {
    }

    public ResourceBundle getResourceBundle() {
        if (resourceBundle == null) {
            resourceBundle = App.getModule(LanguageModuleApi.class).getBundle(AddonUpdateModule.class);
        }

        return resourceBundle;
    }

    @Override
    public AddonUpdateChangesManagement createUpdateChangesManager() {
        return new LocalAddonUpdateChangesManager();
    }

    @Override
    public void setupAddons(AddonApplication application) {
        String configDirectoryPath = App.getConfigDirectory().getAbsolutePath();
        File addonsDirectory = new File(configDirectoryPath, ADDONS_DIRECTORY);
        File updateDirectory = new File(configDirectoryPath, ADDONS_UPDATE_DIRECTORY);
        if (updateDirectory.exists()) {
            File changesConfig = new File(updateDirectory, ADDONS_CHANGES_FILE);

            if (changesConfig.exists()) {
                if (!addonsDirectory.exists()) {
                    addonsDirectory.mkdirs();
                }
                // Perform update
                Logger.getLogger(AddonUpdateModule.class.getName()).log(Level.INFO, "Starting addons update");
                boolean success = false;
                String line = null;
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(changesConfig)))) {
                    do {
                        line = reader.readLine();
                        if (line != null) {
                            if (line.startsWith(FILE_UPDATE)) {
                                String fileName = line.substring(12);
                                File replacedFile = new File(addonsDirectory, fileName);
                                File sourceFile = new File(updateDirectory, fileName);
                                if (sourceFile.exists()) {
                                    if (replacedFile.exists()) {
                                        replacedFile.delete();
                                    }
                                }
                                sourceFile.renameTo(replacedFile);
                            } else if (line.startsWith(FILE_REMOVAL)) {
                                String fileName = line.substring(12);
                                File removedFile = new File(addonsDirectory, fileName);
                                if (removedFile.exists()) {
                                    removedFile.delete();
                                }
                            }
                        }
                    } while (line != null);
                    success = true;
                } catch (IOException ex) {
                    Logger.getLogger(AddonUpdateModule.class.getName()).log(Level.SEVERE, "Failed to move file " + line, ex);
                }
                if (success) {
                    changesConfig.delete();
                }

                Logger.getLogger(AddonUpdateModule.class.getName()).log(Level.INFO, "Finished addons update");
            }
        }

        // Load addons
        try {
            URL addonsPath = addonsDirectory.toURI().toURL();
            application.addModulesFromPath(addonsPath, AddonModuleFileLocation.ADDON);
        } catch (MalformedURLException ex) {
            Logger.getLogger(AddonUpdateModule.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
