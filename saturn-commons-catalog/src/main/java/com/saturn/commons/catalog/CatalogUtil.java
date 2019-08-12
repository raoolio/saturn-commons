package com.saturn.commons.catalog;

/**
 * CatalogProvider wrapper
 * @author raoolio
 */
public class CatalogUtil implements CatalogProvider {

    /** CatalogProvider Provider instance */
    private CatalogProvider provider;


    /**
     * Set CatalogProvider instance
     * @param provider
     */
    public void setProvider(CatalogProvider provider) {
        this.provider = provider;
    }


    @Override
    public Integer getId(String value) {
        return provider.getId(value);
    }


    @Override
    public long getSize() {
        return provider.getSize();
    }


    @Override
    public void release() {
        provider.release();
    }


    @Override
    public void addItem(Integer id, String value) {
        provider.addItem(id, value);
    }


}
