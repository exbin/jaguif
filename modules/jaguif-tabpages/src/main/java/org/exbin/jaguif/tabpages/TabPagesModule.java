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
package org.exbin.jaguif.tabpages;

import java.util.ResourceBundle;
import org.jspecify.annotations.NullMarked;
import org.exbin.jaguif.App;
import org.exbin.jaguif.language.api.LanguageModuleApi;
import org.exbin.jaguif.context.api.ContextRegistration;
import org.exbin.jaguif.tabpages.api.TabPages;
import org.exbin.jaguif.tabpages.api.TabPagesDefinitionManagement;
import org.exbin.jaguif.tabpages.api.TabPagesManagement;
import org.exbin.jaguif.tabpages.api.TabPagesModuleApi;
import org.exbin.jaguif.tabpages.gui.OptTabbedPagesPanel;
import org.exbin.jaguif.tabpages.gui.TabbedPagesPanel;

/**
 * Implementation of tab pages module.
 */
@NullMarked
public class TabPagesModule implements TabPagesModuleApi {

    private TabPagesManager mainTabPagesManager = null;
    private ResourceBundle resourceBundle;

    public TabPagesModule() {
    }

    public void unregisterModule(String moduleId) {
    }

    public ResourceBundle getResourceBundle() {
        if (resourceBundle == null) {
            resourceBundle = App.getModule(LanguageModuleApi.class).getBundle(TabPagesModule.class);
        }

        return resourceBundle;
    }

    @Override
    public TabPagesManagement getMainTabPagesManager() {
        if (mainTabPagesManager == null) {
            mainTabPagesManager = new TabPagesManager();
            mainTabPagesManager.registerTabPages(MAIN_TAB_PAGES_ID, MODULE_ID);
        }
        return mainTabPagesManager;
    }

    @Override
    public TabPagesManagement createTabPagesManager() {
        return new TabPagesManager();
    }

    @Override
    public void buildTabPages(TabPages targetTabPages, String tabPagesId, ContextRegistration contextRegistration) {
        TabPagesModule.this.getMainTabPagesManager().buildTabPages(targetTabPages, tabPagesId, contextRegistration);
    }

    @Override
    public void registerTabPages(String tabPagesId, String moduleId) {
        TabPagesModule.this.getMainTabPagesManager().registerTabPages(tabPagesId, moduleId);
    }

    @Override
    public TabPagesDefinitionManagement getMainTabPagesDefinition(String moduleId) {
        return new TabPagesDefinitionManager(getMainTabPagesManager(), MAIN_TAB_PAGES_ID, moduleId);
    }

    @Override
    public TabPagesDefinitionManagement getMainTabPagesDefinition(String tabPagesId, String moduleId) {
        return new TabPagesDefinitionManager(getMainTabPagesManager(), tabPagesId, moduleId);
    }

    @Override
    public TabPagesDefinitionManagement createTabPagesDefinition(TabPagesManagement tabPagesManagement, String tabPagesId, String moduleId) {
        return new TabPagesDefinitionManager(tabPagesManagement, tabPagesId, moduleId);
    }

    @Override
    public TabbedPagesPanel createTabbedPagesPanel() {
        return new TabbedPagesPanel();
    }

    @Override
    public OptTabbedPagesPanel createOptTabbedPagesPanel() {
        return new OptTabbedPagesPanel();
    }
}
