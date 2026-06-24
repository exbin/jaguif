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
package org.exbin.jaguif.menu;

import java.util.Map;
import java.util.ResourceBundle;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.jspecify.annotations.NullMarked;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import org.exbin.jaguif.App;
import org.exbin.jaguif.action.api.ActionConsts;
import org.exbin.jaguif.action.api.ActionType;
import org.exbin.jaguif.language.api.LanguageModuleApi;
import org.exbin.jaguif.action.api.ActionModuleApi;
import org.exbin.jaguif.contribution.api.GroupSequenceContributionRule;
import org.exbin.jaguif.contribution.api.PositionSequenceContributionRule;
import org.exbin.jaguif.contribution.api.SeparationSequenceContributionRule;
import org.exbin.jaguif.contribution.api.SequenceContribution;
import org.exbin.jaguif.menu.api.MenuModuleApi;
import org.exbin.jaguif.menu.api.MenuDefinitionManagement;
import org.exbin.jaguif.menu.api.MenuManagement;
import org.exbin.jaguif.context.api.ContextRegistration;
import org.exbin.jaguif.context.api.ContextStateProvider;
import org.exbin.jaguif.menu.api.MenuBuilder;
import org.exbin.jaguif.action.api.clipboard.ClipboardOperationActions;

/**
 * Implementation of menu module.
 */
@NullMarked
public class MenuModule implements MenuModuleApi {

    private MenuBuilder menuBuilder = null;
    private MenuManager mainMenuManager = null;
    private ResourceBundle resourceBundle;

    public MenuModule() {
    }

    public void unregisterModule(String moduleId) {
    }

    @NonNull
    @Override
    public ResourceBundle getResourceBundle() {
        if (resourceBundle == null) {
            resourceBundle = App.getModule(LanguageModuleApi.class).getBundle(MenuModule.class);
        }

        return resourceBundle;
    }

    @NonNull
    @Override
    public JMenuItem actionToMenuItem(Action action) {
        return actionToMenuItem(action, null);
    }

    @NonNull
    @Override
    public JMenuItem actionToMenuItem(Action action, @Nullable Map<String, ButtonGroup> buttonGroups) {
        return actionToMenuItemInt(action, buttonGroups);
    }

    @NonNull
    private JMenuItem actionToMenuItemInt(Action action, @Nullable Map<String, ButtonGroup> buttonGroups) {
        JMenuItem menuItem;
        ActionType actionType = (ActionType) action.getValue(ActionConsts.ACTION_TYPE);
        MenuBuilder builder = getMenuBuilder();
        if (actionType != null) {
            switch (actionType) {
                case CHECK: {
                    menuItem = builder.createCheckBoxMenuItem();
                    menuItem.setAction(action);
                    break;
                }
                case RADIO: {
                    menuItem = builder.createRadioButtonMenuItem();
                    menuItem.setAction(action);
                    String radioGroup = (String) action.getValue(ActionConsts.ACTION_RADIO_GROUP);
                    if (buttonGroups != null) {
                        ButtonGroup buttonGroup = buttonGroups.get(radioGroup);
                        if (buttonGroup == null) {
                            buttonGroup = new ButtonGroup();
                            buttonGroups.put(radioGroup, buttonGroup);
                        }
                        buttonGroup.add(menuItem);
                    }
                    break;
                }
                default: {
                    menuItem = builder.createMenuItem();
                    menuItem.setAction(action);
                }
            }
        } else {
            menuItem = builder.createMenuItem();
            menuItem.setAction(action);
        }

        Object dialogMode = action.getValue(ActionConsts.ACTION_DIALOG_MODE);
        if (dialogMode instanceof Boolean && ((Boolean) dialogMode)) {
            LanguageModuleApi languageModule = App.getModule(LanguageModuleApi.class);
            menuItem.setText(languageModule.getActionWithDialogText(menuItem.getText()));
        }
        return menuItem;
    }

    @NonNull
    @Override
    public MenuManager getMainMenuManager() {
        if (mainMenuManager == null) {
            mainMenuManager = new MenuManager();
        }

        return mainMenuManager;
    }

    @NonNull
    @Override
    public MenuManagement createMenuManager() {
        return new MenuManager();
    }

    @NonNull
    @Override
    public MenuDefinitionManagement getMainMenuDefinition(String moduleId) {
        return new MenuDefinitionManager(MenuModule.this.getMainMenuManager(), MAIN_MENU_ID, moduleId);
    }

    @NonNull
    @Override
    public MenuDefinitionManagement getMainMenuDefinition(String menuId, String moduleId) {
        return new MenuDefinitionManager(MenuModule.this.getMainMenuManager(), menuId, moduleId);
    }

    @NonNull
    @Override
    public MenuDefinitionManagement createMenuDefinition(MenuManagement menuManagement, String menuId, String moduleId) {
        return new MenuDefinitionManager(menuManagement, menuId, moduleId);
    }

    @Override
    public void registerMenu(String menuId, String moduleId) {
        MenuModule.this.getMainMenuManager().registerMenu(menuId, moduleId);
    }

    @Override
    public void unregisterMenu(String menuId) {
        MenuModule.this.getMainMenuManager().unregisterMenu(menuId);
    }

    @Override
    public void buildMenu(JPopupMenu targetMenu, String menuId, ContextRegistration contextRegistration) {
        MenuModule.this.getMainMenuManager().buildMenu(targetMenu, menuId, contextRegistration, null);
    }

    @Override
    public void buildMenu(JPopupMenu targetMenu, String menuId, ContextRegistration contextRegistration, @Nullable ContextStateProvider creationContext) {
        MenuModule.this.getMainMenuManager().buildMenu(targetMenu, menuId, contextRegistration, creationContext);
    }

    @Override
    public void buildMenu(JMenuBar targetMenuBar, String menuId, ContextRegistration contextRegistration) {
        MenuModule.this.getMainMenuManager().buildMenu(targetMenuBar, menuId, contextRegistration, null);
    }

    @Override
    public void buildMenu(JMenuBar targetMenuBar, String menuId, ContextRegistration contextRegistration, @Nullable ContextStateProvider creationContext) {
        MenuModule.this.getMainMenuManager().buildMenu(targetMenuBar, menuId, contextRegistration, creationContext);
    }

    @NonNull
    @Override
    public MenuBuilder getMenuBuilder() {
        if (menuBuilder == null) {
            menuBuilder = new DefaultMenuBuilder();
        }

        return menuBuilder;
    }

    @Override
    public void setMenuBuilder(MenuBuilder menuBuilder) {
        this.menuBuilder = menuBuilder;
    }

    @Override
    public void registerClipboardMenuItems(String menuId, @Nullable String subMenuId, String moduleId, SeparationSequenceContributionRule.SeparationMode separationMode) {
        ActionModuleApi actionModule = App.getModule(ActionModuleApi.class);
        registerClipboardMenuItems(actionModule.getClipboardOperationActions(), menuId, subMenuId, moduleId, separationMode);
    }

    @Override
    public void registerClipboardMenuItems(ClipboardOperationActions actions, String menuId, @Nullable String subMenuId, String moduleId, SeparationSequenceContributionRule.SeparationMode separationMode) {
        MenuDefinitionManagement mgmt = MenuModule.this.getMainMenuDefinition(menuId, moduleId);
        if (subMenuId != null) {
            mgmt = mgmt.getSubMenu(subMenuId);
        }
        SequenceContribution contribution = mgmt.registerMenuGroup(CLIPBOARD_ACTIONS_MENU_GROUP_ID);
        mgmt.registerMenuRule(contribution, new PositionSequenceContributionRule(PositionSequenceContributionRule.PositionMode.TOP));
        mgmt.registerMenuRule(contribution, new SeparationSequenceContributionRule(separationMode));
        contribution = actions.createCutContribution();
        mgmt.registerMenuContribution(contribution);
        mgmt.registerMenuRule(contribution, new GroupSequenceContributionRule(CLIPBOARD_ACTIONS_MENU_GROUP_ID));
        contribution = actions.createCopyContribution();
        mgmt.registerMenuContribution(contribution);
        mgmt.registerMenuRule(contribution, new GroupSequenceContributionRule(CLIPBOARD_ACTIONS_MENU_GROUP_ID));
        contribution = actions.createPasteContribution();
        mgmt.registerMenuContribution(contribution);
        mgmt.registerMenuRule(contribution, new GroupSequenceContributionRule(CLIPBOARD_ACTIONS_MENU_GROUP_ID));
        contribution = actions.createDeleteContribution();
        mgmt.registerMenuContribution(contribution);
        mgmt.registerMenuRule(contribution, new GroupSequenceContributionRule(CLIPBOARD_ACTIONS_MENU_GROUP_ID));
        contribution = actions.createSelectAllContribution();
        mgmt.registerMenuContribution(contribution);
        mgmt.registerMenuRule(contribution, new GroupSequenceContributionRule(CLIPBOARD_ACTIONS_MENU_GROUP_ID));
    }

    @Override
    public void registerMenuClipboardActions() {
        registerClipboardMenuItems(MenuModuleApi.MAIN_MENU_ID, EDIT_SUBMENU_ID, MODULE_ID, SeparationSequenceContributionRule.SeparationMode.NONE);
    }
}
