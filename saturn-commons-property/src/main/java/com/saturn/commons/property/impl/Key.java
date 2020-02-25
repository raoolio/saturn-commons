package com.saturn.commons.property.impl;

import java.util.Objects;


/**
 * PropertyKey
 */
public class Key {

    /** Property Path */
    private String path;

    /** Property ID */
    private String id;

    /** Hashcode */
    private int hash;



    /**
     * Constructor
     * @param path Property path
     * @param id Property ID
     */
    public Key(String path, String id) {
        this.path = path;
        this.id = id;
    }



    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Override
    public int hashCode() {
        if (hash==0) {
            hash= path.hashCode();
            hash= hash + 31*id.hashCode();
        }
        return hash;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Key other = (Key) obj;
        if (!Objects.equals(this.path, other.path)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }


    @Override
    public String toString() {
        return "Key: PATH[" + path + "] ID[" + id + ']';
    }

}
