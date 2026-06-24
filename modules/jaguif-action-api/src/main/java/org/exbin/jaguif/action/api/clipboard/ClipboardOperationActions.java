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
package org.exbin.jaguif.action.api.clipboard;

import javax.swing.Action;
import org.exbin.jaguif.contribution.api.ActionSequenceContribution;
import org.jspecify.annotations.NullMarked;

/**
 * Interface for clipboard editing actions.
 */
@NullMarked
public interface ClipboardOperationActions {

    /**
     * Creates cut to clipboard action.
     *
     * @return action instance
     */
    Action createCutAction();

    /**
     * Creates copy to clipboard action.
     *
     * @return action instance
     */
    Action createCopyAction();

    /**
     * Creates paste from clipboard action.
     *
     * @return action instance
     */
    Action createPasteAction();

    /**
     * Creates delete selection action.
     *
     * @return action instance
     */
    Action createDeleteAction();

    /**
     * Creates select all action.
     *
     * @return action instance
     */
    Action createSelectAllAction();

    /**
     * Creates cut to clipboard contribution.
     *
     * @return contribution instance
     */
    ActionSequenceContribution createCutContribution();

    /**
     * Creates copy to clipboard contribution.
     *
     * @return contribution instance
     */
    ActionSequenceContribution createCopyContribution();

    /**
     * Creates paste from clipboard contribution.
     *
     * @return contribution instance
     */
    ActionSequenceContribution createPasteContribution();

    /**
     * Creates delete selection contribution.
     *
     * @return contribution instance
     */
    ActionSequenceContribution createDeleteContribution();

    /**
     * Creates select all contribution.
     *
     * @return contribution instance
     */
    ActionSequenceContribution createSelectAllContribution();
}
