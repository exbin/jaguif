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
package org.exbin.jaguif.ui.settings;

import java.util.Locale;
import org.jspecify.annotations.NullMarked;
import org.exbin.jaguif.options.api.OptionsStorage;
import org.exbin.jaguif.options.settings.api.SettingsOptions;

/**
 * UI language options.
 */
@NullMarked
public class LanguageOptions implements SettingsOptions {

    public static final String KEY_LOCALE_LANGUAGE = "locale.language";
    public static final String KEY_LOCALE_COUNTRY = "locale.country";
    public static final String KEY_LOCALE_VARIANT = "locale.variant";
    public static final String KEY_LOCALE_TAG = "locale.tag";

    protected final OptionsStorage storage;

    public LanguageOptions(OptionsStorage storage) {
        this.storage = storage;
    }

    public String getLocaleLanguage() {
        return storage.get(KEY_LOCALE_LANGUAGE, "");
    }

    public String getLocaleCountry() {
        return storage.get(KEY_LOCALE_COUNTRY, "");
    }

    public String getLocaleVariant() {
        return storage.get(KEY_LOCALE_VARIANT, "");
    }

    public String getLocaleTag() {
        return storage.get(KEY_LOCALE_TAG, "");
    }

    public Locale getLocale() {
        String localeTag = getLocaleTag();
        if (!localeTag.trim().isEmpty()) {
            try {
                return Locale.forLanguageTag(localeTag);
            } catch (SecurityException ex) {
                // Ignore it in java WebStart
            }
        }

        String localeLanguage = getLocaleLanguage();
        String localeCountry = getLocaleCountry();
        String localeVariant = getLocaleVariant();
        try {
            return new Locale(localeLanguage, localeCountry, localeVariant);
        } catch (SecurityException ex) {
            // Ignore it in java WebStart
        }

        return Locale.ROOT;
    }

    public void setLocaleLanguage(String language) {
        storage.put(KEY_LOCALE_LANGUAGE, language);
    }

    public void setLocaleCountry(String country) {
        storage.put(KEY_LOCALE_COUNTRY, country);
    }

    public void setLocaleVariant(String variant) {
        storage.put(KEY_LOCALE_VARIANT, variant);
    }

    public void setLocaleTag(String variant) {
        storage.put(KEY_LOCALE_TAG, variant);
    }

    public void setLocale(Locale locale) {
        setLocaleTag(locale.toLanguageTag());
        setLocaleLanguage(locale.getLanguage());
        setLocaleCountry(locale.getCountry());
        setLocaleVariant(locale.getVariant());
    }

    @Override
    public void copyTo(SettingsOptions options) {
        LanguageOptions with = (LanguageOptions) options;
        with.setLocaleCountry(getLocaleCountry());
        with.setLocaleLanguage(getLocaleLanguage());
        with.setLocaleTag(getLocaleTag());
        with.setLocaleVariant(getLocaleVariant());
    }
}
