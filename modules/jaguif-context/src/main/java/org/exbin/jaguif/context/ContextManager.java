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

import java.util.List;
import org.jspecify.annotations.NullMarked;
import org.exbin.jaguif.context.api.ContextChange;
import org.exbin.jaguif.context.api.ContextStateChangeListener;
import org.exbin.jaguif.context.api.ContextStateManagement;
import org.exbin.jaguif.context.api.ContextMonitoringManagement;
import org.exbin.jaguif.context.api.ContextMonitoringRegistration;

/**
 * Context manager.
 */
@NullMarked
public class ContextManager implements ContextMonitoringRegistration {

    protected final String contextId;
    protected final ContextMonitoringManagement monitoringManagement;
    protected final ContextStateManagement stateManagement;

    public ContextManager(String contextId, ContextMonitoringManagement monitoringManagement, ContextStateManagement stateManagement) {
        this.contextId = contextId;
        this.monitoringManagement = monitoringManagement;
        this.stateManagement = stateManagement;
    }

    @Override
    public void registerContextMonitoring(ContextChange contextChange) {
        monitoringManagement.addContextItem(contextId, contextChange);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void finish() {
        for (Class<?> stateClass : stateManagement.getStateClasses()) {
            Object instance = stateManagement.getActiveState(stateClass);
            List<ContextStateChangeListener<?>> changeListeners = monitoringManagement.getChangeListeners(contextId, stateClass);
            for (ContextStateChangeListener changeListener : changeListeners) {
                changeListener.stateChanged(instance);
            }
        }
    }
}
