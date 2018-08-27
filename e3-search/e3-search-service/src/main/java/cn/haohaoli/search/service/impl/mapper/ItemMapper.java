package cn.haohaoli.search.mapper;

import cn.haohaoli.common.pojo.SearchItem;

import java.util.List;

/**
 * @author Liwenhao
 * @date 2018/8/13 14:56
 */
public interface ItemMapper {
    List<SearchItem> selectList();
}
