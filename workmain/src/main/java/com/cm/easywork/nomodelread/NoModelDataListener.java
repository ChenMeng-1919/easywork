package com.cm.easywork.nomodelread;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: cm
 * @since: Created in 2022/11/25 14:40
 * @description:
 */
@Slf4j
public class NoModelDataListener extends AnalysisEventListener<Map<Integer, String>> {

    public List<Map<Integer, String>> cachedDataList = new ArrayList<>();

    @Override
    public void invoke(Map<Integer, String> data, AnalysisContext context) {
        log.info("解析到一条数据:{}", data);
        cachedDataList.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("所有数据解析完成！");
    }

    /**
     * 加上存储数据库
     */

}