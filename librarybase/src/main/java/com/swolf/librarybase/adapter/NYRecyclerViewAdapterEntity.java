package com.swolf.librarybase.adapter;

import java.io.Serializable;

public class NYRecyclerViewAdapterEntity implements Serializable {

    public int typeIndex;//从0开始

    public int getTypeIndex() {
        return typeIndex;
    }

    public void setTypeIndex(int typeIndex) {
        this.typeIndex = typeIndex;
    }
}
