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
package org.exbin.jaguif.frame.action;

import java.util.ResourceBundle;
import org.jspecify.annotations.NullMarked;

/**
 * Basic frame actions.
 */
@NullMarked
public class FrameActions {

    private ResourceBundle resourceBundle;

    public FrameActions() {
    }

    public void init(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    public ViewToolBarAction createViewToolBarAction() {
        ViewToolBarAction viewToolBarAction = new ViewToolBarAction();
        viewToolBarAction.init(resourceBundle);
        return viewToolBarAction;
    }

    public ViewToolBarCaptionsAction createViewToolBarCaptionsAction() {
        ViewToolBarCaptionsAction viewToolBarCaptionsAction = new ViewToolBarCaptionsAction();
        viewToolBarCaptionsAction.init(resourceBundle);
        return viewToolBarCaptionsAction;
    }

    public ViewStatusBarAction createViewStatusBarAction() {
        ViewStatusBarAction viewStatusBarAction = new ViewStatusBarAction();
        viewStatusBarAction.init(resourceBundle);
        return viewStatusBarAction;
    }
}
