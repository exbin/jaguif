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
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jspecify.annotations.NullMarked;
import org.exbin.jaguif.App;
import org.exbin.jaguif.addon.update.api.AddonUpdateChangesManagement;

/**
 * Addon update changes manager.
 */
@NullMarked
public class LocalAddonUpdateChangesManager implements AddonUpdateChangesManagement {

    public static final String ADDON_UPDATES_DIR = "addons_update";
    public static final String CHANGES_FILE = "changes.cfg";
    protected final List<String> installAddons = new ArrayList<>();
    protected final List<String> removeAddons = new ArrayList<>();
    protected final List<String> updateFiles = new ArrayList<>();
    protected final List<String> removeFiles = new ArrayList<>();

    @Override
    public List<String> getInstallAddons() {
        return installAddons;
    }

    @Override
    public List<String> getRemoveAddons() {
        return removeAddons;
    }

    @Override
    public List<String> getUpdateFiles() {
        return updateFiles;
    }

    @Override
    public List<String> getRemoveFiles() {
        return removeFiles;
    }

    @Override
    public void addInstallAddon(String addonId) {
        installAddons.add(addonId);
    }

    @Override
    public void removeInstallAddon(String addonId) {
        installAddons.remove(addonId);
    }

    @Override
    public boolean hasInstallAddon(String addonId) {
        return installAddons.contains(addonId);
    }

    @Override
    public void addRemoveAddon(String addonId) {
        removeAddons.add(addonId);
    }

    @Override
    public void removeRemoveAddon(String addonId) {
        removeAddons.remove(addonId);
    }

    @Override
    public boolean hasRemoveAddon(String addonId) {
        return removeAddons.contains(addonId);
    }

    @Override
    public void addUpdateFile(String fileName) {
        updateFiles.add(fileName);
    }

    @Override
    public void removeUpdateFile(String fileName) {
        updateFiles.remove(fileName);
    }

    @Override
    public boolean hasUpdateFile(String fileName) {
        return updateFiles.contains(fileName);
    }

    @Override
    public void addRemoveFile(String fileName) {
        removeFiles.add(fileName);
    }

    @Override
    public void removeRemoveFile(String fileName) {
        removeFiles.remove(fileName);
    }

    @Override
    public boolean hasRemoveFile(String fileName) {
        return removeFiles.contains(fileName);
    }

    @Override
    public void readConfig() {
        File targetDirectory = new File(App.getConfigDirectory(), ADDON_UPDATES_DIR);
        File changesConfigFile = new File(targetDirectory, CHANGES_FILE);
        installAddons.clear();
        removeAddons.clear();
        updateFiles.clear();
        removeFiles.clear();
        if (changesConfigFile.exists()) {
            String line = null;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(changesConfigFile)))) {
                do {
                    line = reader.readLine();
                    if (line != null && !line.isEmpty()) {
                        int prefixEnd = line.indexOf(":");
                        String prefix = line.substring(0, prefixEnd);
                        String value = line.substring(prefixEnd + 1);
                        if (ChangeType.INSTALL_ADDON.name().equals(prefix)) {
                            installAddons.add(value);
                        } else if (ChangeType.REMOVE_ADDON.name().equals(prefix)) {
                            removeAddons.add(value);
                        } else if (ChangeType.UPDATE_FILE.name().equals(prefix)) {
                            updateFiles.add(value);
                        } else if (ChangeType.REMOVE_FILE.name().equals(prefix)) {
                            removeFiles.add(value);
                        }
                    }
                } while (line != null);
            } catch (IOException ex) {
                Logger.getLogger(LocalAddonUpdateChangesManager.class.getName()).log(Level.SEVERE, "Failed to read config file " + line, ex);
            }
        }
    }

    @Override
    public void writeConfig() {
        File targetDirectory = new File(App.getConfigDirectory(), ADDON_UPDATES_DIR);
        File changesConfigFile = new File(targetDirectory, CHANGES_FILE);
        try (OutputStreamWriter writer = new FileWriter(changesConfigFile)) {
            String prefix = ChangeType.INSTALL_ADDON.name() + ":";
            for (String line : installAddons) {
                writer.write(prefix + line + "\r\n");
            }
            prefix = ChangeType.REMOVE_ADDON.name() + ":";
            for (String line : removeAddons) {
                writer.write(prefix + line + "\r\n");
            }
            prefix = ChangeType.UPDATE_FILE.name() + ":";
            for (String line : updateFiles) {
                writer.write(prefix + line + "\r\n");
            }
            prefix = ChangeType.REMOVE_FILE.name() + ":";
            for (String line : removeFiles) {
                writer.write(prefix + line + "\r\n");
            }
        } catch (IOException ex) {
            Logger.getLogger(LocalAddonUpdateChangesManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public enum ChangeType {
        INSTALL_ADDON,
        REMOVE_ADDON,
        UPDATE_FILE,
        REMOVE_FILE
    }
}
