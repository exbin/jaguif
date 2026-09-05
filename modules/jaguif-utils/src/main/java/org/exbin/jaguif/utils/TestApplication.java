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
package org.exbin.jaguif.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jspecify.annotations.NullMarked;
import org.exbin.jaguif.App;
import org.exbin.jaguif.LauncherModule;
import org.exbin.jaguif.Module;
import org.exbin.jaguif.ModuleProvider;

/**
 * Some simple static methods usable for testing windows and dialogs.
 */
@NullMarked
public class TestApplication {

    private final Map<String, Module> modules = new HashMap<>();
    
    TestApplication() {
    }

    public void launch(Runnable runnable) {
        attachModuleProvider();
        App.launch(runnable);
    }

    public void launch(String launcherModuleId, String[] args) {
        attachModuleProvider();
        App.launch(launcherModuleId, args);
    }

    private void attachModuleProvider() {
        if (App.hasModuleProvider()) {
            ModuleProvider moduleProvider = App.getModuleProvider();
            if (moduleProvider instanceof TestModuleProvider) {
                ((TestModuleProvider) moduleProvider).setModules(modules);
                return;
            }
        }

        TestModuleProvider testModuleProvider = new TestModuleProvider();
        testModuleProvider.setModules(modules);
        App.setModuleProvider(testModuleProvider);
    }

    public static void run(Runnable runnable) {
        new TestApplication().launch(runnable);
    }

    public void addModule(String moduleId, Module module) {
        modules.put(moduleId, module);

        if (App.hasModuleProvider()) {
            ModuleProvider moduleProvider = App.getModuleProvider();
            if (moduleProvider instanceof TestModuleProvider) {
                ((TestModuleProvider) moduleProvider).addModule(moduleId, module);
            }
        }
    }
}
