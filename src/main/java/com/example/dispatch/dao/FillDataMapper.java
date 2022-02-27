package com.example.dispatch.dao;

import com.example.dispatch.entity.FillData;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * @author liuhp
 * @Description dao层数据处理操作
 * @date 2022/2/17 21:42
 */
@Mapper
@Repository
@Component
public interface FillDataMapper {
    /**
     * 查询
     */
    List<FillData> list();
}
