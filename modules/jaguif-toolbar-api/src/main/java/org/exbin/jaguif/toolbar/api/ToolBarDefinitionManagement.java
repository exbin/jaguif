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
import org.exbin.jaguif.contribution.api.GroupSequenceContribution;
import org.exbin.jaguif.contribution.api.SequenceContribution;
import org.exbin.jaguif.contribution.api.SequenceContributionRule;

/**
 * Interface for toolbar definition management.
 */
@NullMarked
public interface ToolBarDefinitionManagement {

    /**
     * Registers toolbar contribution.
     *
     * @param contribution toolbar contribution
     */
    void registerToolBarContribution(SequenceContribution contribution);

    /**
     * Registers group as a child item for given toolbar.
     *
     * @param groupId group id
     * @return toolbar contribution
     */
    GroupSequenceContribution registerToolBarGroup(String groupId);

    /**
     * Registers toolbar contribution rule.
     *
     * @param toolBarContribution toolbar contribution
     * @param rule toolbar contribution rule
     */
    void registerToolBarRule(SequenceContribution toolBarContribution, SequenceContributionRule rule);

    /**
     * Returns registered contributions.
     *
     * @return toolbar contributions
     */
    List<SequenceContribution> getContributions();
}
