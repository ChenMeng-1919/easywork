package com.cm.easywork.datalistener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.cm.easywork.entity.LanGanInputEntity;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/*
 * @author: cm
 * @date: Created in 2021/10/20 15:14
 * @description:
 */
// 有个很重要的点 BaiYeInputEntityListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
@Data
@Slf4j
public class LanGanInputEntityListener extends AnalysisEventListener<LanGanInputEntity> {
    /**
     * 缓存的数据
     */
    private List<LanGanInputEntity> list = new ArrayList<>();

    public LanGanInputEntityListener() {

    }
    /**
     * 这个每一条数据解析都会来调用
     *
     * @param data    one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context
     */
    @Override
    public void invoke(LanGanInputEntity data, AnalysisContext context) {
        list.add(data);
    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("所有数据解析完成！");
    }


}