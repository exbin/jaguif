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
package org.exbin.jaguif.addon.manager.api.operation;

import org.exbin.jaguif.addon.manager.api.AddonRecord;
import org.jspecify.annotations.NullMarked;

/**
 * Addon operation record.
 */
@NullMarked
public class AddonOperation implements CartOperation {

    protected final AddonOperationVariant variant;
    protected final AddonRecord record;

    public AddonOperation(AddonOperationVariant variant, AddonRecord record) {
        this.variant = variant;
        this.record = record;
    }

    @Override
    public AddonOperationVariant getVariant() {
        return variant;
    }

    public AddonRecord getRecord() {
        return record;
    }
}
