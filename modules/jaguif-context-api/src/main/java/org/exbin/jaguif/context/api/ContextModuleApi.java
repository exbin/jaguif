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
     * Returns main application context manager.
     *
     * @return context manager
     */
    ContextStateManagement getMainContextManager();

    /**
     * Creates new default context manager.
     *
     * @return context manager
     */
    ContextStateManagement createContextManager();

    /**
     * Creates new context registrator.
     *
     * @return context registrator
     */
    ContextMonitoringRegistration createContextRegistrator();

    /**
     * Creates new context registrator for specific context manager.
     *
     * @param contextMonitoringManager context monitoring manager
     * @return context registrator
     */
    ContextMonitoringRegistration createContextRegistrator(ContextStateManagement contextMonitoringManager);

    /**
     * Creates new context registrator for specific context manager.
     *
     * @param contextUpdateManagement context update management
     * @param contextMonitoringManagement context monitoring management
     * @return context registrator
     */
    ContextMonitoringRegistration createContextRegistrator(ContextMonitoringManagement contextUpdateManagement, ContextStateManagement contextMonitoringManagement);

    /**
     * Creates new context update manager.
     *
     * @return context update manager
     */
    ContextMonitoringManagement createContextUpdateManagement();

    /**
     * Creates new context update manager.
     *
     * @param contextManagement context management
     * @return context update manager
     */
    ContextMonitoringManagement createContextUpdateManagement(ContextStateManagement contextManagement);

    /**
     * Creates new child context manager for given parent context manager.
     *
     * @param parentContextManager parent context manager
     * @return context manager
     */
    ContextStateManagement createChildContextManager(ContextStateManagement parentContextManager);
}
