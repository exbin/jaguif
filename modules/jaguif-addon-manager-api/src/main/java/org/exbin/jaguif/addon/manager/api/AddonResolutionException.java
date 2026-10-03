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

import org.jspecify.annotations.Nullable;
import org.jspecify.annotations.NullMarked;

/**
 * Addon operation resolution exception.
 */
@NullMarked
public class AddonResolutionException extends Exception {

    public AddonResolutionException() {
    }

    public AddonResolutionException(String string) {
        super(string);
    }

    public AddonResolutionException(String string, @Nullable Throwable thrwbl) {
        super(string, thrwbl);
    }

    public AddonResolutionException(@Nullable Throwable thrwbl) {
        super(thrwbl);
    }

    public AddonResolutionException(String string, @Nullable Throwable thrwbl, boolean enableSuppression, boolean writableStackTrace) {
        super(string, thrwbl, enableSuppression, writableStackTrace);
    }
}
