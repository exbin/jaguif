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
package org.exbin.jaguif.addon.manager.filter.gui;

import java.awt.Component;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import org.exbin.jaguif.addon.manager.gui.CategoryRecord;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * Addons target state including queued changes.
 */
@NullMarked
public class CategoryListCellRenderer extends JCheckBox implements ListCellRenderer<CategoryRecord> {

    private static final Border DEFAULT_NO_FOCUS_BORDER = new EmptyBorder(1, 1, 1, 1);

    @Override
    public Component getListCellRendererComponent(JList<? extends CategoryRecord> list, @Nullable CategoryRecord value, int index, boolean isSelected, boolean cellHasFocus) {
        setEnabled(list.isEnabled());
        setComponentOrientation(list.getComponentOrientation());
        setFont(list.getFont());
        setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
        setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());
        setFocusPainted(cellHasFocus);
        setBorderPainted(true);
        Border border;
        if (cellHasFocus) {
            border = UIManager.getBorder(isSelected ? "List.focusSelectedCellHighlightBorder" : "List.focusCellHighlightBorder");
        } else {
            border = UIManager.getBorder("List.cellNoFocusBorder");
        }
        if (border == null) {
            border = DEFAULT_NO_FOCUS_BORDER;
        }
        setBorder(border);
        setText(value == null ? "" : value.getName());
        setSelected(value != null && value.isSelected());

        return this;
    }
}
