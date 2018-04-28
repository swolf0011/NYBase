package com.swolf.libraryxrefreshview;

/**
 * Created by ly on 2017/10/18.
 */

public class NYPage {

    public int pageSize = 10;
    public int pageNumber = 1;
    public boolean havNext = true;

    public NYPage(int pageSize) {
        this.pageSize = pageSize;
    }

    public NYPage() {
    }
}
