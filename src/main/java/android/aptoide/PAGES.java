package android.aptoide;


public enum PAGES {
    HOME("action_home", HomePage.class),
    EDITORIAL("action_curation", EditorialPage.class),
    SEARCH("action_search", SearchPage.class),
    STORES("action_stores", StoresPage.class),
    APPS("action_apps", AppsPage.class);

    public final String elementId;
    public final Class<? extends BasePage> pageClass;

    <T extends BasePage> PAGES(String elementId, Class<T> pageClass) {
        this.elementId = elementId;
        this.pageClass = pageClass;
    }
}
