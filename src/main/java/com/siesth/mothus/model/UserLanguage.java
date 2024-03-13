/**
 * @file UserLanguage.java
 * @brief Enum of supported languages
 * @date 2023-11-22
 * @version 1.0
 */

package com.siesth.mothus.model;

import java.util.Locale;

/**
 * Enum of supported languages
 * Follows IETF BCP 47 language and region codes but with underscores _ instead of hyphens -
 * See <a href="https://www.iana.org/assignments/language-subtag-registry/language-subtag-registry">possible values</a>
 * Note: regions are necessary to differentiate between "same" languages (e.g. Canadian French, Scottish English...)
 */
public enum UserLanguage {
    /**
     * English
     */
    en,
    /**
     * Français
     */
    fr;

    /**
     * Returns the UserLanguage from a locale string, or en (english) if the locale string is not supported
     * It will automatically replace hyphens - with underscores _
     * @param language the locale string (en, fr-FR, ...)
     * @return the UserLanguage corresponding to the locale string, or en (english) if not supported
     */
    public static UserLanguage fromLocaleStringOrEn(String language) {
        try {
            return UserLanguage.valueOf(language.replace('-', '_'));
        } catch (IllegalArgumentException e) {
            return UserLanguage.en;
        }
    }

    /**
     * Returns the UserLanguage from a locale, or en (english) if the locale is not supported
     * @param language the locale. Only language and region are used, the rest is ignored
     * @return the UserLanguage corresponding to the locale, or en (english) if not supported
     */
    public static UserLanguage fromLocaleOrEn(Locale language) {
        return fromLocaleStringOrEn(language.getLanguage() + "-" + language.getCountry());
    }

    /**
     * Returns a locale corresponding to the UserLanguage
     * @return the locale corresponding to the UserLanguage, with its language and optionally region
     */
    public Locale toLocale() {
        return Locale.forLanguageTag(this.name().replace('_', '-'));
    }

    /**
     * Returns the locale string from the UserLanguage
     * It will automatically replace underscores _ with hyphens -
     * @return the locale string (en, fr-FR, ...) corresponding to the UserLanguage
     */
    public String toLocaleString() {
        return this.name().replace('_', '-');
    }
}
