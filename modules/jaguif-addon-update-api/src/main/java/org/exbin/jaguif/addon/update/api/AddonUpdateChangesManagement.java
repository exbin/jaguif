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
package org.exbin.jaguif.addon.update.api;

import java.util.List;
import org.jspecify.annotations.NullMarked;

/**
 * Addon update changes management.
 */
@NullMarked
public interface AddonUpdateChangesManagement {

    List<String> getInstallAddons();

    List<String> getRemoveAddons();

    List<String> getUpdateFiles();

    List<String> getRemoveFiles();

    void addInstallAddon(String addonId);

    void removeInstallAddon(String addonId);

    boolean hasInstallAddon(String addonId);

    void addRemoveAddon(String addonId);

    void removeRemoveAddon(String addonId);

    boolean hasRemoveAddon(String addonId);

    void addUpdateFile(String fileName);

    void removeUpdateFile(String fileName);

    boolean hasUpdateFile(String fileName);

    void addRemoveFile(String fileName);

    void removeRemoveFile(String fileName);

    boolean hasRemoveFile(String fileName);

    void readConfig();

    void writeConfig();
}
