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
package org.exbin.jaguif.toolbar.api;

import java.util.List;
import org.jspecify.annotations.NullMarked;
import javax.swing.JToolBar;
import org.exbin.jaguif.contribution.api.GroupSequenceContribution;
import org.exbin.jaguif.contribution.api.SequenceContribution;
import org.exbin.jaguif.contribution.api.SequenceContributionRule;
import org.exbin.jaguif.context.api.ContextRegistration;

/**
 * Interface for toolbar management.
 */
@NullMarked
public interface ToolBarManagement {

    /**
     * Builds toolbar from given definition id.
     *
     * @param targetToolBar output toolbar
     * @param toolBarId toolbar definition id
     * @param contextRegistration context registration
     */
    void buildToolBar(JToolBar targetToolBar, String toolBarId, ContextRegistration contextRegistration);

    /**
     * Builds toolbar with icons only from given definition id.
     *
     * @param targetToolBar output toolbar
     * @param toolBarId toolbar definition id
     * @param contextRegistration context registration
     */
    void buildIconToolBar(JToolBar targetToolBar, String toolBarId, ContextRegistration contextRegistration);

    /**
     * Registers toolbar.
     *
     * @param toolBarId toolbar id
     * @param pluginId plugin id
     */
    void registerToolBar(String toolBarId, String pluginId);

    /**
     * Registers toolbar contribution.
     *
     * @param toolBarId toolbar id
     * @param moduleId module id
     * @param contribution toolbar contribution
     */
    void registerToolBarContribution(String toolBarId, String moduleId, SequenceContribution contribution);

    /**
     * Registers toolbar group.
     *
     * @param toolBarId toolbar id
     * @param moduleId module id
     * @param groupId group id
     * @return group contribution
     */
    GroupSequenceContribution registerToolBarGroup(String toolBarId, String moduleId, String groupId);

    /**
     * Register toolbar contribution rule.
     *
     * @param contribution toolbar contribution
     * @param rule toolbar rule
     */
    void registerToolBarRule(SequenceContribution contribution, SequenceContributionRule rule);

    /**
     * Returns registered contributions.
     *
     * @return toolbar contributions
     */
    List<SequenceContribution> getContributions();
}
