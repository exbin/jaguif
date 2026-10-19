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
package org.exbin.jaguif.addon.manager.api;

import java.util.List;
import java.util.Optional;
import org.jspecify.annotations.NullMarked;
import javax.swing.Action;
import org.exbin.jaguif.Module;
import org.exbin.jaguif.ModuleUtils;
import org.exbin.jaguif.addon.manager.api.filter.CategoryPageFilter;

/**
 * Interface of the addon manager module.
 */
@NullMarked
public interface AddonManagerModuleApi extends Module {

    public static final String MODULE_ID = ModuleUtils.getModuleIdByApi(AddonManagerModuleApi.class);
    public static final String INSTALLED_ADDONS_PAGE_ID = "installedAddons";
    public static final String SETTINGS_PAGE_ID = "addonManager";
    public static final String ADDON_MANAGER_TABPAGES_ID = "addonManager";
    public static final String LOOK_AND_FEEL_CATEGORY_ID = "laf";
    public static final String LANGUAGE_CATEGORY_ID = "language";
    public static final String ICON_SET_CATEGORY_ID = "iconSet";

    /**
     * Creates addon manager action.
     *
     * @return addon manager action
     */
    Action createAddonManagerAction();

    /**
     * Creates addon manager action.
     *
     * @param categoryId category identifier
     * @return addon manager action
     */
    Action createAddonManagerCategoryAction(String categoryId);

    /**
     * Registers addon manager menu item.
     */
    void registerAddonManagerMenuItem();

    /**
     * Registers settings.
     */
    void registerSettings();

    /**
     * Registers basic addon manager.
     */
    void registerBasicAddonManager();

    /**
     * Returns link URL for manual catalog access.
     *
     * @return link
     */
    Optional<String> getManualCatalogUrl();

    /**
     * Sets manual catalog link URL.
     *
     * @param manualCatalogUrl link
     */
    void setManualCatalogUrl(String manualCatalogUrl);

    /**
     * Creates addons list component.
     *
     * @return addons list component
     */
    AddonsListComponent createAddonsListComponent();

    /**
     * Returns addon categories.
     *
     * @return addon categories
     */
    List<AddonCategory> getAddonCategories();

    /**
     * Adds addon category.
     *
     * @param category addon category
     */
    void addAddonCategory(AddonCategory category);

    /**
     * Registers basic addon categories.
     */
    void registerBasicCategories();

    /**
     * Creates new category filter.
     *
     * @return category filter
     */
    CategoryPageFilter createCategoryFilter();
}
