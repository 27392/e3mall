package cn.haohaoli.search.service;

import cn.haohaoli.common.pojo.SearchResult;


/**
 * @author Liwenhao
 * @date 2018/8/13 17:17
 */
public interface SearchService {

    SearchResult search(String keyword, int page, int rows) throws Exception;
}
