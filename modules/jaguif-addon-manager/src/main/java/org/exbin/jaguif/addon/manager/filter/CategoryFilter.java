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
package org.exbin.jaguif.addon.manager.filter;

import java.util.ArrayList;
import java.util.List;
import org.exbin.jaguif.addon.manager.api.AddonCategory;
import org.exbin.jaguif.addon.manager.api.filter.CategoryPageFilter;
import org.jspecify.annotations.NullMarked;
import org.exbin.jaguif.addon.manager.gui.CategoryRecord;
import org.jspecify.annotations.Nullable;

/**
 * Addons category filter.
 */
@NullMarked
public class CategoryFilter implements CategoryPageFilter {

    protected CategoryPageFilter.Mode mode = Mode.INCLUDE;
    protected final List<CategoryRecord> addonCategories = new ArrayList<>();

    public List<CategoryRecord> getAddonCategories() {
        return addonCategories;
    }

    public void addCategory(AddonCategory category) {
        addonCategories.add(new CategoryRecord(category));
    }

    @Override
    public List<String> getCategoryIds() {
        List<String> categoryIds = new ArrayList<>();
        for (CategoryRecord addonCategory : addonCategories) {
            if (addonCategory.isSelected()) {
                categoryIds.add(addonCategory.getId());
            }
        }
        return categoryIds;
    }

    @Override
    public boolean isEmpty() {
        if (mode == Mode.EXCLUDE) {
            return false;
        }

        for (CategoryRecord addonCategory : addonCategories) {
            if (addonCategory.isSelected()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public boolean isSelected(String id) {
        return getRecord(id).isSelected();
    }

    public void setSelected(String id, boolean selected) {
        getRecord(id).setSelected(selected);
    }

    public @Nullable
    CategoryRecord getRecord(String id) {
        for (CategoryRecord addonCategory : addonCategories) {
            if (id.equals(addonCategory.getId())) {
                return addonCategory;
            }
        }

        return null;
    }
}
