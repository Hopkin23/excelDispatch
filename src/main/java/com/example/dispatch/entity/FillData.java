package com.example.dispatch.entity;

import lombok.Data;
import java.io.Serializable;

/**
 * @author liuhp
 * @Description 用来填充到demo.xlsx中的数据对象
 * @date 2022/2/17 21:41
 */
@Data
public class FillData implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 公民身份证号
     */
    private String idCard;

    /**
     * 姓名
     */
    private String name;

    /**
     * 住址门（楼牌号）
     */
    private String group;

    /**
     * 住址门（楼）详址
     */
    private String houseNumber;

    /**
     * 电话号码
     */
    private String phone;

    /**
     * 电话号码
     */
    private String isAllowance;

}