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

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import org.jspecify.annotations.NullMarked;
import javax.swing.JToggleButton;
import org.jspecify.annotations.Nullable;

/**
 * Addons manager cart button.
 */
@NullMarked
public class CartButton extends JToggleButton {

    protected int changesCount = 0;
    protected @Nullable ImageIcon leftIcon = null;
    protected @Nullable String centerText = null;
    protected final Color changesCountFg = new Color(255, 255, 201);
    protected final Color noChangesFg = Color.BLACK;
    protected final Color changesCountBg = new Color(16, 163, 16);
    protected final Color noChangesBg = Color.LIGHT_GRAY;

    public CartButton() {
        super();
        updateWrapper();
    }

    public void setLeftIcon(@Nullable ImageIcon leftIcon) {
        this.leftIcon = leftIcon;
        updateWrapper();
    }

    public void setCenterText(@Nullable String centerText) {
        this.centerText = centerText;
        updateWrapper();
    }

    @Override
    public void setIconTextGap(int iconTextGap) {
        super.setIconTextGap(iconTextGap);
        updateWrapper();
    }
    
    
    private void updateWrapper() {
        setIcon(new WrapperIcon());
    }

    public void setChangesCount(int changesCount) {
        this.changesCount = changesCount;
        repaint();
    }

    private class WrapperIcon implements Icon {
        int width;
        int height;
        
        private WrapperIcon() {
            width = 25;
            if (leftIcon != null) {
                width += leftIcon.getIconWidth() + getIconTextGap();
            }
            if (centerText != null) {
                char[] changesCharArray = centerText.toCharArray();
                FontMetrics fontMetrics = getFontMetrics(getFont());
                int textWidth = fontMetrics.charsWidth(changesCharArray, 0, changesCharArray.length);
                width += textWidth + getIconTextGap();
            }

            height = 20;
            if (leftIcon != null && leftIcon.getIconHeight() > height) {
                height = leftIcon.getIconHeight();
            }
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            int textX = x;
            if (leftIcon != null) {
                g.drawImage(leftIcon.getImage(), x, y, null);
                textX += leftIcon.getIconWidth() + getIconTextGap();
            }
            if (centerText != null) {
                Font font = getFont();
                int fontSize = font.getSize();
                int textY = y + (height / 2) + (fontSize / 2);
                g.setFont(font);
                g.setColor(getForeground());
                g.drawString(centerText, textX, textY);
            }

            int counterX = x + width - 25;
            int counterY = y + height / 2 - 10;
            g.setColor(changesCount == 0 ? noChangesBg : changesCountBg);
            g.fillOval(counterX, counterY, 25, 20);
            g.setColor(changesCount == 0 ? noChangesFg : changesCountFg);
            Font font = getFont().deriveFont(Font.BOLD);
            g.setFont(font);
            FontMetrics fontMetrics = g.getFontMetrics(font);
            String text = changesCount > 99 ? "+" : String.valueOf(changesCount);
            char[] changesCharArray = text.toCharArray();
            int textWidth = fontMetrics.charsWidth(changesCharArray, 0, changesCharArray.length);
            g.drawString(text, counterX + 13 - textWidth / 2, counterY + 15);
        }

        @Override
        public int getIconWidth() {
            return width;
        }

        @Override
        public int getIconHeight() {
            return height;
        }
    }
}
