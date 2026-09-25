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
package org.exbin.jaguif.addon.manager.page;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import org.jspecify.annotations.NullMarked;
import javax.swing.JComponent;
import org.exbin.jaguif.App;
import org.exbin.jaguif.addon.manager.api.operation.AddonOperation;
import org.exbin.jaguif.addon.manager.api.AddonManagerModuleApi;
import org.exbin.jaguif.addon.manager.api.ItemRecord;
import org.exbin.jaguif.addon.manager.api.AddonManagerPage;
import org.exbin.jaguif.addon.manager.api.AddonPageRefreshFilter;
import org.exbin.jaguif.addon.manager.api.operation.AddonOperationVariant;
import org.exbin.jaguif.addon.manager.api.AddonsListComponent;
import org.exbin.jaguif.addon.manager.api.AddonsListComponentController;
import org.exbin.jaguif.tabpages.api.AbstractTabPagesComponent;
import org.exbin.jaguif.tabpages.api.ComponentTabPagesContribution;
import org.exbin.jaguif.tabpages.api.TabPagesComponent;
import org.exbin.jaguif.addon.manager.api.AddonsManagementContext;
import org.exbin.jaguif.addon.manager.api.AddonsManagementCartController;
import org.exbin.jaguif.addon.manager.api.AddonsManagementLocalState;
import org.exbin.jaguif.addon.manager.api.UpdateAvailabilityContext;
import org.exbin.jaguif.context.api.ContextChange;
import org.exbin.jaguif.context.api.ContextChangeRegistration;
import org.exbin.jaguif.language.api.LanguageModuleApi;
import org.jspecify.annotations.Nullable;
import org.exbin.jaguif.addon.manager.api.UpdateAvailabilityManagement;

/**
 * Installed addons manager page.
 */
@NullMarked
public class InstalledAddonsPage extends AbstractTabPagesComponent implements AddonManagerPage {

    public static final String PAGE_ID = "installedAddons";
    protected final ResourceBundle resourceBundle = App.getModule(LanguageModuleApi.class).getBundle(InstalledAddonsPage.class);
    protected @Nullable AddonsListComponent listComponent;
    protected final List<ItemChangedListener> itemChangedListeners = new ArrayList<>();

    protected AddonPageRefreshFilter filter = new AddonPageRefreshFilter();
    protected @Nullable AddonsManagementContext managementContext;
    protected @Nullable List<ItemRecord> addonItems = null;

    public InstalledAddonsPage() {
        init();
    }

    private void init() {
        AddonManagerModuleApi addonManagerModule = App.getModule(AddonManagerModuleApi.class);
        listComponent = addonManagerModule.createAddonsListComponent();
        listComponent.setController(new AddonsListComponentController() {

            @Override
            public int getItemsCount() {
                return InstalledAddonsPage.this.getItemsCount();
            }

            @Override
            public ItemRecord getItem(int index) {
                return InstalledAddonsPage.this.getItem(index);
            }

            @Override
            public void addToCart(ItemRecord itemRecord, AddonOperationVariant variant) {
                ((AddonsManagementCartController) managementContext).addCartOperation(new AddonOperation(variant, itemRecord));
            }

            @Override
            public boolean isInCart(String moduleId, AddonOperationVariant variant) {
                return ((AddonsManagementCartController) managementContext).isInCart(moduleId, variant);
            }

            @Override
            public void requestModuleDetail(ItemRecord itemRecord) {
                // TODO addonManager.requestModuleDetail(itemRecord, addonsPanel);
            }
        });
        itemChangedListeners.add(listComponent::notifyItemChanged);
        putValue(KEY_NAME, resourceBundle.getString("page.name"));
        putValue(KEY_CONTEXT_CHANGE, new ContextChange() {
            @Override
            public void register(ContextChangeRegistration registrar) {
                registrar.registerChangeListener(AddonsManagementContext.class, (instance) -> {
                    setContext(instance);
                });
                registrar.registerChangeListener(UpdateAvailabilityContext.class, (instance) -> {
                    setAvailableModuleUpdates((UpdateAvailabilityManagement) instance);
                });
            }
        });
    }

    private int getItemsCount() {
        if (managementContext == null || addonItems == null) {
            return 0;
        }

        return addonItems.size();
    }

    private ItemRecord getItem(int index) {
        return addonItems.get(index);
    }

    public void setAvailableModuleUpdates(UpdateAvailabilityManagement availableModuleUpdates) {
        int itemsCount = getItemsCount();
        for (int i = 0; i < itemsCount; i++) {
            availableModuleUpdates.applyTo(getItem(i));
        }
        notifyItemsChanged();
    }

    @Override
    public JComponent getComponent() {
        return listComponent.getComponent();
    }

    @Override
    public void setContext(AddonsManagementContext context) {
        this.managementContext = context;
        listComponent.setContext(context);
    }

    @Override
    public AddonPageRefreshFilter getFilter() {
        return filter;
    }

    @Override
    public void setFilter(AddonPageRefreshFilter filter) {
        this.filter = filter;
    }

    @Override
    public void refreshContent() {
        if (managementContext == null) {
            return;
        }

        managementContext.runOperation(() -> {
            // TODO Implement as background thread
            List<ItemRecord> installedAddons = ((AddonsManagementLocalState) managementContext).getInstalledAddons();
            String searchCondition = filter.getSearchCondition().trim().toLowerCase();
            if (searchCondition.isEmpty()) {
                addonItems = installedAddons;
                notifyItemsChanged();
                return;
            }

            List<ItemRecord> items = new ArrayList<>();
            for (int i = 0; i < installedAddons.size(); i++) {
                ItemRecord record = installedAddons.get(i);
                if (record.getName().toLowerCase().contains(searchCondition)) {
                    items.add(record);
                }
            }
            addonItems = items;
            notifyItemsChanged();
        });
    }

    private void notifyItemsChanged() {
        for (ItemChangedListener itemChangedListener : itemChangedListeners) {
            itemChangedListener.itemChanged();
        }
        listComponent.notifyItemsChanged();
    }

    public interface ItemChangedListener {

        void itemChanged();
    }

    public static class Contribution implements ComponentTabPagesContribution {

        @Override
        public TabPagesComponent createComponent() {
            return new InstalledAddonsPage();
        }

        @Override
        public String getContributionId() {
            return PAGE_ID;
        }
    };
}
