package com.qin.MySurvey;

class LanguageSet {

    private static String currentLanguage = "en";
    private LanguageSet() {
    }

    static String getCurrentLanguage() {
        return currentLanguage;
    }

    static void SetCurrentLanguage(String cur){
        currentLanguage=cur;
    }

}
