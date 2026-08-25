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
package org.exbin.jaguif.context.api;

import org.jspecify.annotations.NullMarked;
import org.exbin.jaguif.Module;
import org.exbin.jaguif.ModuleUtils;

/**
 * Interface for context support module.
 */
@NullMarked
public interface ContextModuleApi extends Module {

    public static final String MODULE_ID = ModuleUtils.getModuleIdByApi(ContextModuleApi.class);

    /**
     * Returns main application context state manager.
     *
     * @return context state manager
     */
    ContextStateManagement getMainStateManager();

    /**
     * Creates new default context state manager.
     *
     * @return context state manager
     */
    ContextStateManagement createStateManager();

    /**
     * Creates new context monitoring registrator.
     *
     * @return context monitoring registrator
     */
    ContextMonitoringRegistration createMonitoringRegistrator();

    /**
     * Creates new context registrator for specific context manager.
     *
     * @param monitoringManager context monitoring manager
     * @return context monitoring registrator
     */
    ContextMonitoringRegistration createMonitoringRegistrator(ContextStateManagement monitoringManager);

    /**
     * Creates new context registrator for specific context manager.
     *
     * @param monitoringManagement context monitoring management
     * @param stateManagement context state management
     * @return context monitoring registrator
     */
    ContextMonitoringRegistration createMonitoringRegistrator(ContextMonitoringManagement monitoringManagement, ContextStateManagement stateManagement);

    /**
     * Creates new context monitoring manager.
     *
     * @return context monitoring manager
     */
    ContextMonitoringManagement createMonitoringManager();

    /**
     * Creates new context monitoring manager.
     *
     * @param stateManagement context state management
     * @return context monitoring manager
     */
    ContextMonitoringManagement createMonitoringManager(ContextStateManagement stateManagement);

    /**
     * Creates new child context manager for given parent context manager.
     *
     * @param parentStateManager parent context manager
     * @return context manager
     */
    ContextStateManagement createChildStateManager(ContextStateManagement parentStateManager);
}
