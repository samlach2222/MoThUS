/**
 * @file UserLanguage.java
 * @brief Enum of supported languages
 * @date 2023-11-22
 * @version 1.0
 */

package com.siesth.mothus.model;

/**
 * Enum of supported languages, using the same format as HTML but with an underscore _ instead of a hyphen -
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
     * Returns the UserLanguage from a locale string
     * It will automatically replace hyphens - with underscores _
     * @param language the locale string (en, fr-FR, ...)
     * @return the UserLanguage corresponding to the locale string
     * @throws IllegalArgumentException if the locale string is not supported
     */
    public static UserLanguage fromLocaleString(String language) {
        return UserLanguage.valueOf(language.replace('-', '_'));
    }

    /**
     * Returns the UserLanguage from a locale string, or en (english) if the locale string is not supported
     * It will automatically replace hyphens - with underscores _
     * @param language the locale string (en, fr-FR, ...)
     * @return the UserLanguage corresponding to the locale string, or en (english) if not supported
     */
    public static UserLanguage fromLocaleStringOrEn(String language) {
        try {
            return fromLocaleString(language);
        } catch (IllegalArgumentException e) {
            return UserLanguage.en;
        }
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
