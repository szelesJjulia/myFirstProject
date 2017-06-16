package com.ebookfrenzy.szelesjulide.practiceprojectgooglemaplist.data;

/**
 * Created by szeles.julide on 2017/06/13.
 */

public class ItemTop {

    private String itemName;
    private boolean isChecked;

    public ItemTop(String name){
        this.setItemName(name);
        setChecked(false);
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
