package cn.haohaoli.item.pojo;

import cn.haohaoli.model.TbItem;
import org.springframework.util.StringUtils;

/**
 * @author Liwenhao
 * @date 2018/8/14 21:31
 */
public class Item extends TbItem {

    public Item(TbItem tbItem) {
        this.setId(tbItem.getId());
        this.setTitle(tbItem.getTitle());
        this.setSellPoint(tbItem.getSellPoint());
        this.setPrice(tbItem.getPrice());
        this.setNum(tbItem.getNum());
        this.setBarcode(tbItem.getBarcode());
        this.setImage(tbItem.getImage());
        this.setCid(tbItem.getCid());
        this.setStatus(tbItem.getStatus());
        this.setCreated(tbItem.getCreated());
        this.setUpdated(tbItem.getUpdated());
    }

    public String[] getImages() {
        String image = this.getImage();
        if (!StringUtils.isEmpty(image)) {
            return image.split(",");
        }
        return null;
    }
}
