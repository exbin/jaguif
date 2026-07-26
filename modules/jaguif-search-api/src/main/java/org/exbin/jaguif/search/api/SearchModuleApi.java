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
package org.exbin.jaguif.search.api;

import org.jspecify.annotations.NullMarked;
import org.exbin.jaguif.Module;
import org.exbin.jaguif.ModuleUtils;

/**
 * Interface for framework search module.
 */
@NullMarked
public interface SearchModuleApi extends Module {

    public static String MODULE_ID = ModuleUtils.getModuleIdByApi(SearchModuleApi.class);
    public static final String SEARCH_MENU_GROUP_ID = MODULE_ID + ".searchMenuGroup";
    public static final String EDIT_FIND_MENU_GROUP_ID = MODULE_ID + ".editFindMenuGroup";
    public static final String EDIT_FIND_TOOL_BAR_GROUP_ID = MODULE_ID + ".editFindToolBarGroup";

    /**
     * Registers find and replace actions into main menu.
     */
    void registerEditFindMenuActions();

    /**
     * Registers find and replace actions into specific popup menu.
     *
     * @param menuId popup menu id
     */
    void registerEditFindPopupMenuActions(String menuId);

    /**
     * Registers find and replace actions into main toolbar.
     */
    void registerEditFindToolBarActions();
}
