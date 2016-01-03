package com.bluemobi.ybb.network.model;

import java.util.List;

/**
 * Created by gaoxy on 2015/8/11.
 */
public class FoodModel {//热门推荐中 餐品
    private List<FoodEntry> entry;

    public List<FoodEntry> getEntry() {
        return entry;
    }

    public void setEntry(List<FoodEntry> entry) {
        this.entry = entry;
    }
}
