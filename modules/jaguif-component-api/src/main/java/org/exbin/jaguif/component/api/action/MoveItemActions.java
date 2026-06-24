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
package org.exbin.jaguif.component.api.action;

import org.jspecify.annotations.NonNull;
import javax.swing.Action;
import org.exbin.jaguif.contribution.api.SequenceContribution;
import org.exbin.jaguif.toolbar.api.ToolBarDefinitionManagement;
import org.exbin.jaguif.toolbar.api.ToolBarManagement;

/**
 * Interface for item movement action set.
 */
public interface MoveItemActions {

    /**
     * Returns move up action.
     *
     * @return move up action
     */
    @NonNull
    Action createMoveUpAction();

    /**
     * Returns move down action.
     *
     * @return move down action
     */
    @NonNull
    Action createMoveDownAction();

    /**
     * Returns move top action.
     *
     * @return move top action
     */
    @NonNull
    Action createMoveTopAction();

    /**
     * Returns move bottom action.
     *
     * @return move bottom action
     */
    @NonNull
    Action createMoveBottomAction();

    /**
     * Returns move up contribution.
     *
     * @return move up contribution
     */
    @NonNull
    SequenceContribution createMoveUpContribution();

    /**
     * Returns move down contribution.
     *
     * @return move down contribution
     */
    @NonNull
    SequenceContribution createMoveDownContribution();

    /**
     * Returns move top contribution.
     *
     * @return move top contribution
     */
    @NonNull
    SequenceContribution createMoveTopContribution();

    /**
     * Returns move bottom contribution.
     *
     * @return move bottom contribution
     */
    @NonNull
    SequenceContribution createMoveBottomContribution();

    /**
     * Registers contributions to tool bar.
     *
     * @param toolBarDefinition tool bar definition
     */
    void registerToolBarContributions(ToolBarDefinitionManagement toolBarDefinition);
}
