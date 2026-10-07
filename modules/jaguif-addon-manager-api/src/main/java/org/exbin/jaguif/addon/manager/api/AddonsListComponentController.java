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

import java.util.function.Consumer;
import org.exbin.jaguif.addon.manager.api.operation.AddonCartOperationType;
import org.jspecify.annotations.NullMarked;

/**
 * Addons list component controller.
 */
@NullMarked
public interface AddonsListComponentController {

    /**
     * Returns number of items.
     *
     * @return number of items
     */
    int getItemsCount();

    /**
     * Returns specific item.
     *
     * @param index order index
     * @return item record
     */
    AddonRecord getItem(int index);

    /**
     * Adds item to the cart.
     *
     * @param itemRecord item record
     * @param variant operation variant
     */
    void addToCart(AddonRecord itemRecord, AddonCartOperationType variant);

    /**
     * Checks whether item for specific addon is in cart.
     *
     * @param moduleId module id
     * @param variant operation variant
     * @return true if present in the cart
     */
    boolean isInCart(String moduleId, AddonCartOperationType variant);

    /**
     * Requests item record to be amended with additional details.
     *
     * @param itemRecord item record to update
     * @param detailOutput detail output
     */
    void requestModuleDetail(AddonRecord itemRecord, Consumer<String> detailOutput);
}
