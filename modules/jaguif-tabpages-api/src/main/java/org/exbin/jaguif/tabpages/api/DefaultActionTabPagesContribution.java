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
package org.exbin.jaguif.tabpages.api;

import org.jspecify.annotations.NullMarked;
import javax.swing.Action;
import java.util.function.Supplier;

/**
 * Default action tab pages item contribution.
 */
@NullMarked
public class DefaultActionTabPagesContribution implements ActionTabPagesContribution {

    protected final String contributionId;
    protected final Supplier<Action> actionSupplier;

    public DefaultActionTabPagesContribution(String contributionId, Supplier<Action> actionSupplier) {
        this.contributionId = contributionId;
        this.actionSupplier = actionSupplier;
    }

    @Override
    public Action createAction() {
        return actionSupplier.get();
    }

    @Override
    public String getContributionId() {
        return contributionId;
    }
}
