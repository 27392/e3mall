package cn.haohaoli.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 分页对象
 * @author Liwenhao
 * @date 2018/8/5 20:41
 */
public class GridResult<T> implements Serializable {

    private long total;
    private List<T> rows;

    public GridResult(long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
