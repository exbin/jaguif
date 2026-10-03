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
package org.exbin.jaguif.addon.manager.api;

import org.exbin.jaguif.addon.update.api.AddonModification;
import org.exbin.jaguif.addon.update.api.AddonModificationType;
import org.jspecify.annotations.NullMarked;

/**
 * Addon operations processing.
 */
@NullMarked
public interface AddonOperationsProcessing {

    /**
     * Adds new modification to processing queue.
     *
     * @param type addon modification operation type
     * @param moduleId module identifier
     */
    void addModification(AddonModificationType type, String moduleId);

    /**
     * Adds new modification to processing queue.
     *
     * @param modification addon modification operation
     */
    void addModification(AddonModification modification);

    /**
     * Returns true if module is included in local modules set.
     *
     * @param moduleId module identifier
     * @return true if module is local
     */
    boolean isLocalModule(String moduleId);
}
