package com.bluemobi.ybb.network.model;

import java.util.List;

/**
 * Created by gaoxy on 2015/8/11.
 */
public class HotRecommendModel {//p5-2热门推荐
    private String type;//typeParamDTO
    private String canteen_id;//食堂id
    private FoodModel combo_category;//餐品
    private List<FoodProductModel> recommend_food;
    private List<String> recommend_pic;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCanteen_id() {
        return canteen_id;
    }

    public void setCanteen_id(String canteen_id) {
        this.canteen_id = canteen_id;
    }

    public FoodModel getCombo_category() {
        return combo_category;
    }

    public void setCombo_category(FoodModel combo_category) {
        this.combo_category = combo_category;
    }

    public List<FoodProductModel> getRecommend_food() {
        return recommend_food;
    }

    public void setRecommend_food(List<FoodProductModel> recommend_food) {
        this.recommend_food = recommend_food;
    }

    public List<String> getRecommend_pic() {
        return recommend_pic;
    }

    public void setRecommend_pic(List<String> recommend_pic) {
        this.recommend_pic = recommend_pic;
    }
}
