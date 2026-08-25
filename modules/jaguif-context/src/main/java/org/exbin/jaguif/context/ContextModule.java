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
package org.exbin.jaguif.context;

import java.util.ResourceBundle;
import org.jspecify.annotations.NullMarked;
import org.exbin.jaguif.App;
import org.exbin.jaguif.context.api.ContextModuleApi;
import org.exbin.jaguif.language.api.LanguageModuleApi;
import org.exbin.jaguif.context.api.ContextChangeListener;
import org.exbin.jaguif.context.api.StateUpdateType;
import org.jspecify.annotations.Nullable;
import org.exbin.jaguif.context.api.ContextStateManagement;
import org.exbin.jaguif.context.api.ContextMonitoringManagement;
import org.exbin.jaguif.context.api.ContextMonitoringRegistration;

/**
 * Implementation of context module.
 */
@NullMarked
public class ContextModule implements ContextModuleApi {

    private @Nullable ResourceBundle resourceBundle;

    private @Nullable ContextStateManager applicationContextManager;

    public ContextModule() {
    }

    public void unregisterModule(String moduleId) {
    }

    public ResourceBundle getResourceBundle() {
        if (resourceBundle == null) {
            resourceBundle = App.getModule(LanguageModuleApi.class).getBundle(ContextModule.class);
        }

        return resourceBundle;
    }

    @Override
    public ContextStateManagement getMainStateManager() {
        if (applicationContextManager == null) {
            applicationContextManager = new ContextStateManager();
        }
        return applicationContextManager;
    }

    @Override
    public ContextStateManagement createStateManager() {
        return new ContextStateManager();
    }

    @Override
    public ContextMonitoringRegistration createMonitoringRegistrator() {
        return new ContextMonitoringRegistrar(getMainStateManager());
    }

    @Override
    public ContextMonitoringRegistration createMonitoringRegistrator(ContextStateManagement stateManagement) {
        return new ContextMonitoringRegistrar(stateManagement);
    }

    @Override
    public ContextMonitoringRegistration createMonitoringRegistrator(ContextMonitoringManagement monitoringMonitoring, ContextStateManagement stateManagement) {
        return new ContextMonitoringRegistrar(stateManagement, monitoringMonitoring);
    }

    @Override
    public ContextMonitoringManagement createMonitoringManager() {
        return new ContextMonitoringManager();
    }

    @Override
    public ContextMonitoringManagement createMonitoringManager(ContextStateManagement stateManagement) {
        ContextMonitoringManager monitoringManager = new ContextMonitoringManager();
        stateManagement.addChangeListener(new ContextChangeListener() {
            @Override
            public <T> void notifyStateChanged(Class<T> stateClass, @Nullable T activeState) {
                monitoringManager.notifyStateChanged(stateClass, activeState);
            }

            @Override
            public <T> void notifyStateUpdated(Class<T> stateClass, T activeState, StateUpdateType updateType) {
                monitoringManager.notifyStateUpdated(stateClass, activeState, updateType);
            }
        });
        return monitoringManager;
    }

    @Override
    public ContextStateManagement createChildStateManager(ContextStateManagement parentStateManagement) {
        return new ChildContextStateManager(parentStateManagement);
    }
}
