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
package org.exbin.jaguif.search.area.action;

import java.util.ResourceBundle;
import org.jspecify.annotations.NullMarked;
import javax.swing.Action;
import org.exbin.jaguif.App;
import org.exbin.jaguif.contribution.api.ActionSequenceContribution;
import org.exbin.jaguif.contribution.api.GroupSequenceContributionRule;
import org.exbin.jaguif.contribution.api.SequenceContribution;
import org.exbin.jaguif.menu.api.MenuDefinitionManagement;
import org.exbin.jaguif.menu.api.MenuModuleApi;
import org.exbin.jaguif.search.area.SearchAreaModule;
import org.exbin.jaguif.search.api.SearchModuleApi;
import org.exbin.jaguif.toolbar.api.ToolBarDefinitionManagement;
import org.exbin.jaguif.toolbar.api.ToolBarModuleApi;

/**
 * Find/replace actions for searching.
 */
@NullMarked
public class SearchInAreaActions {

    private ResourceBundle resourceBundle;

    public SearchInAreaActions() {
    }

    public void init(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    public Action createFindInAreaAction() {
        FindInAreaAction findInAreaAction = new FindInAreaAction();
        findInAreaAction.init(resourceBundle);
        return findInAreaAction;
    }

    public Action createReplaceInAreaAction() {
        ReplaceInAreaAction replaceInAreaAction = new ReplaceInAreaAction();
        replaceInAreaAction.init(resourceBundle);
        return replaceInAreaAction;
    }

    public void registerEditFindMenuActions() {
        MenuModuleApi menuModule = App.getModule(MenuModuleApi.class);
        String groupId = SearchModuleApi.EDIT_FIND_MENU_GROUP_ID;
        MenuDefinitionManagement mgmt = menuModule.getMainMenuDefinition(SearchAreaModule.MODULE_ID).getSubMenu(MenuModuleApi.EDIT_SUBMENU_ID);
        SequenceContribution contribution = new FindInAreaContribution();
        mgmt.registerMenuContribution(contribution);
        mgmt.registerMenuRule(contribution, new GroupSequenceContributionRule(groupId));
        contribution = new ReplaceInAreaContribution();
        mgmt.registerMenuContribution(contribution);
        mgmt.registerMenuRule(contribution, new GroupSequenceContributionRule(groupId));
    }

    public void registerEditFindPopupMenuActions(String menuId) {
        MenuModuleApi menuModule = App.getModule(MenuModuleApi.class);
        MenuDefinitionManagement mgmt = menuModule.getMainMenuDefinition(menuId, SearchAreaModule.MODULE_ID);
        String groupId = SearchModuleApi.EDIT_FIND_MENU_GROUP_ID;
        SequenceContribution contribution = new FindInAreaContribution();
        mgmt.registerMenuContribution(contribution);
        mgmt.registerMenuRule(contribution, new GroupSequenceContributionRule(groupId));
        contribution = new ReplaceInAreaContribution();
        mgmt.registerMenuContribution(contribution);
        mgmt.registerMenuRule(contribution, new GroupSequenceContributionRule(groupId));
    }

    public void registerEditFindToolBarActions() {
        ToolBarModuleApi toolBarModule = App.getModule(ToolBarModuleApi.class);
        ToolBarDefinitionManagement mgmt = toolBarModule.getMainToolBarDefinition(SearchAreaModule.MODULE_ID);
        SequenceContribution contribution = new FindInAreaContribution();
        mgmt.registerToolBarContribution(contribution);
        mgmt.registerToolBarRule(contribution, new GroupSequenceContributionRule(SearchModuleApi.EDIT_FIND_TOOL_BAR_GROUP_ID));
    }

    public class FindInAreaContribution implements ActionSequenceContribution {

        public static final String CONTRIBUTION_ID = "findInArea";

        @Override
        public Action createAction() {
            return createFindInAreaAction();
        }

        @Override
        public String getContributionId() {
            return CONTRIBUTION_ID;
        }
    }

    public class ReplaceInAreaContribution implements ActionSequenceContribution {

        public static final String CONTRIBUTION_ID = "replaceInArea";

        @Override
        public Action createAction() {
            return createReplaceInAreaAction();
        }

        @Override
        public String getContributionId() {
            return CONTRIBUTION_ID;
        }
    }
}
