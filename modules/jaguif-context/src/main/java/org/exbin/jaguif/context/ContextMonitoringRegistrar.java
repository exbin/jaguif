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

import java.util.ArrayList;
import java.util.List;
import org.jspecify.annotations.NullMarked;
import org.exbin.jaguif.context.api.ContextChange;
import org.exbin.jaguif.context.api.ContextChangeListener;
import org.exbin.jaguif.context.api.ContextMonitoringManagement;
import org.exbin.jaguif.context.api.ContextValues;
import org.exbin.jaguif.context.api.ContextStateManagement;
import org.exbin.jaguif.context.api.ContextMonitoringRegistration;
import org.exbin.jaguif.context.api.StateUpdateType;
import org.jspecify.annotations.Nullable;

/**
 * Context monitoring registration.
 */
@NullMarked
public class ContextMonitoringRegistrar implements ContextMonitoringRegistration {

    protected final List<ContextValues> monitoringItems = new ArrayList<>();
    protected final ContextMonitoringManagement monitoringManagement;
    protected final ContextStateManagement stateManagement;

    public ContextMonitoringRegistrar(ContextStateManagement stateManagement) {
        this.stateManagement = stateManagement;
        this.monitoringManagement = new ContextMonitoringManager();
        init();
    }

    public ContextMonitoringRegistrar(ContextStateManagement stateManagement, ContextMonitoringManagement monitoringManagement) {
        this.stateManagement = stateManagement;
        this.monitoringManagement = monitoringManagement;
        init();
    }

    private void init() {
        stateManagement.addChangeListener(new ContextChangeListener() {
            @Override
            public <T> void notifyStateChanged(Class<T> stateClass, @Nullable T activeState) {
                monitoringManagement.notifyStateChanged(stateClass, activeState);
            }

            @Override
            public <T> void notifyStateUpdated(Class<T> stateClass, T activeState, StateUpdateType updateType) {
                monitoringManagement.notifyStateUpdated(stateClass, activeState, updateType);
            }
        });
    }

    @Override
    public void registerContextMonitoring(ContextChange contextChange) {
        monitoringManagement.addContextItem(contextChange);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void finish() {
        for (Class<?> stateClass : stateManagement.getStateClasses()) {
            Object instance = stateManagement.getActiveState(stateClass);
            monitoringManagement.notifyStateChanged((Class<Object>) stateClass, instance);
        }
    }
}
