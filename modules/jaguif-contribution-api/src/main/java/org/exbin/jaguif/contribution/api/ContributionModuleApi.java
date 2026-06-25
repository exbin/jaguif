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
package org.exbin.jaguif.contribution.api;

import org.jspecify.annotations.NullMarked;
import org.exbin.jaguif.Module;
import org.exbin.jaguif.ModuleUtils;

/**
 * Interface for contribution support module.
 */
@NullMarked
public interface ContributionModuleApi extends Module {

    public static final String MODULE_ID = ModuleUtils.getModuleIdByApi(ContributionModuleApi.class);

    /**
     * Creates contribution definition.
     *
     * @return contribution definition
     */
    ContributionDefinition createContributionDefinition();

    /**
     * Creates contribution manager.
     *
     * @return contribution manager
     */
    ContributionManagement createContributionManager();

    /**
     * Creates contribution builder.
     *
     * @return contribution builder
     */
    ContributionSequenceBuilder createContributionSequenceBuilder();

    /**
     * Creates tree contribution manager.
     *
     * @return tree contribution manager
     */
    TreeContributionManagement createTreeContributionManager();

    /**
     * Creates tree contribution builder.
     *
     * @return tree contribution builder
     */
    TreeContributionSequenceBuilder createTreeContributionSequenceBuilder();
}
