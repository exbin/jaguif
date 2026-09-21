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
package org.exbin.jaguif.addon.manager.operation;

import org.exbin.jaguif.addon.manager.api.operation.AddonModificationType;

/**
 * Local addon modification types.
 */
public enum LocalAddonModificationType implements AddonModificationType {

    NO_ACTION,
    INSTALL_ADDON,
    DEPENDENCY_ADDON,
    REMOVE_ADDON,
    DOWNLOAD_MODULE,
    DOWNLOAD_LIBRARY,
    DOWNLOAD_MAVEN_LIBRARY,
    REMOVE_LIBRARY
}
