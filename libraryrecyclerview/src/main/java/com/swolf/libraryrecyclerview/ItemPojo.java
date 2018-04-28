package com.swolf.libraryrecyclerview;

/**
 * Created by ly on 2017/10/16.
 */

public class ItemPojo {
    public int id;
    public String txt;
    public int size;

    public ItemPojo() {
    }

    public ItemPojo(int id, String txt, int size) {
        this.id = id;
        this.txt = txt;
        this.size = size;
    }

    @Override
    public String toString() {
        return "ItemPojo{" +
                "id=" + id +
                ", txt='" + txt + '\'' +
                ", size=" + size +
                '}';
    }
}
