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

import java.util.ResourceBundle;
import org.jspecify.annotations.Nullable;
import org.jspecify.annotations.NullMarked;
import org.exbin.jaguif.App;
import org.exbin.jaguif.ModuleUtils;
import org.exbin.jaguif.addon.update.api.AddonUpdateModuleApi;
import org.exbin.jaguif.language.api.LanguageModuleApi;
import org.exbin.jaguif.addon.update.api.AddonUpdateChangesManagement;

/**
 * Addon update module.
 */
@NullMarked
public class AddonUpdateModule implements AddonUpdateModuleApi {

    public static final String MODULE_ID = ModuleUtils.getModuleIdByApi(AddonUpdateModule.class);

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
}
