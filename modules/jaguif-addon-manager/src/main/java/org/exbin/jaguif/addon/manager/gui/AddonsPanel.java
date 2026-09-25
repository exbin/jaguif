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
package org.exbin.jaguif.addon.manager.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.ResourceBundle;
import org.jspecify.annotations.NullMarked;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComponent;
import javax.swing.JList;
import org.exbin.jaguif.App;
import org.exbin.jaguif.addon.manager.api.operation.AddonOperationVariant;
import org.exbin.jaguif.addon.manager.api.AddonsListComponent;
import org.exbin.jaguif.addon.manager.api.AddonsListComponentController;
import org.exbin.jaguif.addon.manager.api.AddonsManagementContext;
import org.exbin.jaguif.addon.manager.api.AddonRecord;
import org.exbin.jaguif.language.api.LanguageModuleApi;
import org.jspecify.annotations.Nullable;

/**
 * Addons list with details panel.
 */
@NullMarked
public class AddonsPanel extends javax.swing.JPanel implements AddonsListComponent {

    protected final ResourceBundle resourceBundle = App.getModule(LanguageModuleApi.class).getBundle(AddonsPanel.class);
    protected final AddonsListModel addonsListModel = new AddonsListModel();
    protected final AddonDetailsPanel addonDetailsPanel = new AddonDetailsPanel();
    protected @Nullable AddonRecord activeRecord;
    protected @Nullable AddonsListComponentController controller;

    public AddonsPanel() {
        initComponents();
        init();
    }

    private void init() {
        itemsList.setCellRenderer(new DefaultListCellRenderer() {

            private final AddonItemComponent addonItemPanel = new AddonItemComponent();

            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                AddonRecord record = (AddonRecord) value;
                addonItemPanel.setItemRecord(list, record, isSelected, cellHasFocus);
                return addonItemPanel;
            }
        });
        itemsList.addListSelectionListener((event) -> {
            notifyItemSelected();
        });
    }

    @Override
    public JComponent getComponent() {
        return this;
    }

    @Override
    public void setController(AddonsListComponentController controller) {
        this.controller = controller;
        addonsListModel.setProvider(new AddonsListModel.RecordsProvider() {
            @Override
            public int getItemsCount() {
                return AddonsPanel.this.controller.getItemsCount();
            }

            @Override
            public AddonRecord getItem(int index) {
                return AddonsPanel.this.controller.getItem(index);
            }
        });
        addonsListModel.notifyItemsChanged();
        itemsList.setModel(addonsListModel);
        addonDetailsPanel.setController(new AddonDetailsPanel.Controller() {

            @Override
            public void addToCart(AddonOperationVariant variant) {
                AddonRecord record = activeRecord;
                controller.addToCart(record, variant);
                addonDetailsPanel.updateRecordControlState(record);
            }

            @Override
            public boolean isInCart(String addonId, AddonOperationVariant variant) {
                return controller.isInCart(addonId, variant);
            }

            @Override
            public void requestModuleDetail(AddonRecord itemRecord) {
                controller.requestModuleDetail(itemRecord);
            }
        });
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    @Override
    public void setContext(AddonsManagementContext context) {
        addonDetailsPanel.setContext(context);
    }

    private void notifyItemSelected() {
        int index = itemsList.getSelectedIndex();
        AddonRecord itemRecord = index >= 0 ? itemsList.getModel().getElementAt(index) : null;

        if (activeRecord != itemRecord) {
            if (activeRecord == null) {
                infoPanel.remove(noItemSelectedLabel);
                addonDetailsPanel.setRecord(itemRecord);
                infoPanel.add(addonDetailsPanel, BorderLayout.CENTER);
                infoPanel.revalidate();
                infoPanel.repaint();
            } else if (itemRecord == null) {
                infoPanel.remove(addonDetailsPanel);
                infoPanel.add(noItemSelectedLabel, BorderLayout.CENTER);
                infoPanel.revalidate();
                infoPanel.repaint();
            } else {
                addonDetailsPanel.setRecord(itemRecord);
            }
            activeRecord = itemRecord;
        }
    }

    @Override
    public void notifyItemChanged() {
        if (activeRecord != null) {
            addonDetailsPanel.setRecord(activeRecord);
        }
    }

    @Override
    public void notifyItemsChanged() {
        addonsListModel.notifyItemsChanged();
    }

    public void setModuleDetail(AddonRecord itemRecord, String details) {
        addonDetailsPanel.setModuleDetail(itemRecord, details);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        splitPane = new javax.swing.JSplitPane();
        infoPanel = new javax.swing.JPanel();
        noItemSelectedLabel = new javax.swing.JLabel();
        itemsListScrollPane = new javax.swing.JScrollPane();
        itemsList = new javax.swing.JList<>();

        setLayout(new java.awt.BorderLayout());

        splitPane.setDividerLocation(250);

        infoPanel.setLayout(new java.awt.BorderLayout());

        noItemSelectedLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        noItemSelectedLabel.setText(resourceBundle.getString("noItemSelectedLabel.text")); // NOI18N
        noItemSelectedLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        infoPanel.add(noItemSelectedLabel, java.awt.BorderLayout.CENTER);

        splitPane.setRightComponent(infoPanel);

        itemsList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        itemsListScrollPane.setViewportView(itemsList);

        splitPane.setLeftComponent(itemsListScrollPane);

        add(splitPane, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel infoPanel;
    private javax.swing.JList<org.exbin.jaguif.addon.manager.api.AddonRecord> itemsList;
    private javax.swing.JScrollPane itemsListScrollPane;
    private javax.swing.JLabel noItemSelectedLabel;
    private javax.swing.JSplitPane splitPane;
    // End of variables declaration//GEN-END:variables
}
