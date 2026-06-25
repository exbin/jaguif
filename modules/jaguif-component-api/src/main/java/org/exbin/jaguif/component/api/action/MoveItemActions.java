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

import javax.swing.Action;
import org.exbin.jaguif.contribution.api.SequenceContribution;
import org.exbin.jaguif.toolbar.api.ToolBarDefinitionManagement;
import org.jspecify.annotations.NullMarked;

/**
 * Interface for item movement action set.
 */
@NullMarked
public interface MoveItemActions {

    /**
     * Returns move up action.
     *
     * @return move up action
     */
    Action createMoveUpAction();

    /**
     * Returns move down action.
     *
     * @return move down action
     */
    Action createMoveDownAction();

    /**
     * Returns move top action.
     *
     * @return move top action
     */
    Action createMoveTopAction();

    /**
     * Returns move bottom action.
     *
     * @return move bottom action
     */
    Action createMoveBottomAction();

    /**
     * Returns move up contribution.
     *
     * @return move up contribution
     */
    SequenceContribution createMoveUpContribution();

    /**
     * Returns move down contribution.
     *
     * @return move down contribution
     */
    SequenceContribution createMoveDownContribution();

    /**
     * Returns move top contribution.
     *
     * @return move top contribution
     */
    SequenceContribution createMoveTopContribution();

    /**
     * Returns move bottom contribution.
     *
     * @return move bottom contribution
     */
    SequenceContribution createMoveBottomContribution();

    /**
     * Registers contributions to tool bar.
     *
     * @param toolBarDefinition tool bar definition
     */
    void registerToolBarContributions(ToolBarDefinitionManagement toolBarDefinition);
}
