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
package org.exbin.jaguif.sidebar.api;

import org.jspecify.annotations.NullMarked;
import org.exbin.jaguif.Module;
import org.exbin.jaguif.ModuleUtils;
import org.exbin.jaguif.context.api.ContextRegistration;
import org.exbin.jaguif.docking.api.SidePanelDocking;

/**
 * Interface for sidebar support module.
 */
@NullMarked
public interface SideBarModuleApi extends Module {

    public static String MODULE_ID = ModuleUtils.getModuleIdByApi(SideBarModuleApi.class);
    public static final String MAIN_SIDE_BAR_ID = "mainSideBar";

    /**
     * Returns main sidebar management interface.
     *
     * @return sidebar management interface
     */
    SideBarManagement getMainSideBarManager();

    /**
     * Creates sidebar manager.
     *
     * @return sidebar manager
     */
    SideBarManagement createSideBarManager();

    /**
     * Returns main sidebar management definition.
     *
     * @param moduleId module id
     * @return sidebar management interface
     */
    SideBarDefinitionManagement getMainSideBarDefinition(String moduleId);

    /**
     * Returns sidebar management definition.
     *
     * @param sideBarManagement sidebar management
     * @param sideBarId sidebar id
     * @param moduleId module id
     * @return sidebar management interface
     */
    SideBarDefinitionManagement createSideBarDefinition(SideBarManagement sideBarManagement, String sideBarId, String moduleId);

    /**
     * Registers sidebar associating it with given identification.
     *
     * @param sideBarId sidebar id
     * @param moduleId module id
     */
    void registerSideBar(String sideBarId, String moduleId);

    /**
     * Returns sidebar using given identification.
     *
     * @param targetSideBar target sidebar
     * @param sideBarId sidebar id
     * @param contextRegistration context registration
     */
    void buildSideBar(SideBar targetSideBar, String sideBarId, ContextRegistration contextRegistration);

    /**
     * Sets automatic showing.
     *
     * @param autoShow automatic showing
     */
    void setAutoShow(boolean autoShow);

    /**
     * Registers sidebar to docking.
     *
     * @param docking docking
     */
    void registerDockingSideBar(SidePanelDocking docking);

    /**
     * Registers sidebar to docking.
     *
     * @param sideBarPanelProvider sidebar panel provider
     * @param docking docking
     */
    void registerDockingSideBar(SideBar sideBarPanelProvider, SidePanelDocking docking);
}
