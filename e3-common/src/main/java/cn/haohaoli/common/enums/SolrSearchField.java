package cn.haohaoli.common.enums;

/**
 * @author Liwenhao
 * @date 2018/8/13 16:57
 */
public enum SolrSearchField {

    ID("id"),
    ITEM_TITLE("item_title"),
    ITEM_SELL_POINT("item_sell_point"),
    ITEM_PRICE("item_price"),
    ITEM_IMAGE("item_image"),
    ITEM_CATEGORY_NAME("item_category_name");

    String name;

    SolrSearchField(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
