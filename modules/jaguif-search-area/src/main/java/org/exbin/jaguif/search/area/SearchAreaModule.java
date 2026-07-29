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
package org.exbin.jaguif.search.area;

import java.util.ResourceBundle;
import org.jspecify.annotations.NullMarked;
import org.exbin.jaguif.App;
import org.exbin.jaguif.language.api.LanguageModuleApi;
import org.exbin.jaguif.search.area.action.SearchInAreaActions;
import org.exbin.jaguif.search.area.api.SearchAreaModuleApi;

/**
 * Implementation of framework search module.
 */
@NullMarked
public class SearchAreaModule implements SearchAreaModuleApi {

    private java.util.ResourceBundle resourceBundle = null;

    private SearchInAreaActions findReplaceActions = null;

    public SearchAreaModule() {
    }

    public ResourceBundle getResourceBundle() {
        if (resourceBundle == null) {
            resourceBundle = App.getModule(LanguageModuleApi.class).getBundle(SearchAreaModule.class);
        }

        return resourceBundle;
    }

    public SearchInAreaActions getFindReplaceActions() {
        if (findReplaceActions == null) {
            findReplaceActions = new SearchInAreaActions();
        }

        return findReplaceActions;
    }
}
