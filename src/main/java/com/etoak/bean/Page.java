package com.etoak.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Page<T> {
<<<<<<< HEAD

    private int pageNum;
    private int pageSize;
    private List<T> rows;
    private long total;
    private int pageCount;

=======
    // 页码，当前页
    private int pageNum;
    // 每页记录数
    private int pageSize;
    // 数据
    private List<T> rows;
    // 总记录数
    private long total;
    // 总页数
    private int pageCount;
>>>>>>> dev
}
