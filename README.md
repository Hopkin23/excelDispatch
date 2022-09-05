# excelDispatch

数据库人员数据批量填充至模板excel中



`目的：朋友在基层工作，需要让村里的人员填写纸质的excel表格，其中表格的一些诸如名字、身份证号之类的信息是不同的，有一个信息总表包含了各个人员的名字和身份证号等信息。村里面是通过将1000多个人分组后让4~5个人手动做这件事。`



## 使用方式

1. 首先将人员信息总表导入数据库（建立）

   ```sql
   -- 参考我这边建库建表的sql例子
   SET NAMES utf8mb4;
   SET FOREIGN_KEY_CHECKS = 0;
   
   -- ----------------------------
   -- Table structure for fill_data
   -- ----------------------------
   DROP TABLE IF EXISTS `fill_data`;
   CREATE TABLE `fill_data`  (
     `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
     `id_card` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '公民身份证号',
     `name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '姓名',
     `group` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '住址门（楼牌号）',
     `house_number` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '住址门（楼）详址',
     `phone` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '电话号码',
     `is_allowance` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '是否享受过财政补助',
     PRIMARY KEY (`id`) USING BTREE
   ) ENGINE = InnoDB AUTO_INCREMENT = 1551 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;
   
   SET FOREIGN_KEY_CHECKS = 1;
   ```

   

2. 运行前文件修改 

   - 数据库连接修改`application.yml`

     ```yml
     spring:
       datasource:
         url: jdbc:mysql://rm-uf6319cr4o94hrs9r2o.mysql.rds.aliyuncs.com:3306/people_info?useSSL=false&useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8
         username: root
         password: 131415liangyueLY
         driver-class-name: com.mysql.cj.jdbc.Driver
     ```

     > people_info 改为自己的数据库
     >
     > username、password 改为自己数据库账号密码

   - pom修改`pom.xml`

     ```xml
     <!-- 集成MySQL连接-->
     <dependency>
     	<groupId>mysql</groupId>
     	<artifactId>mysql-connector-java</artifactId>
     	<version>8.0.28</version>
     </dependency>
     ```

     > 由于涉及的数据库版本不同，对应的connector版本可能要修改，我这边用的mysql是8

3. 编写模板

   > 参照 excel/demo.xlse
   >
   > 该模板就是需要人员手填的表，使用阿里的easyExcel来进行填入，如{group}这种地方就是到时候要填充字符串的位置，group对应的是实体类的一个属性。
   >
   > 主要的逻辑就是从数据库读取信息列表，循环列表填入模板中

4. 运行

   通过postman进行get请求`1127.0.0.1:8001/list`运行程序



## 注意

该程序会将填充完的字段进行分组保存， 注意数据库中存的group字段，该字段用于分组和文件夹的命名。譬如我当时是group值是1~11，所以共11个组，运行完的文件也是分别保存在11个文件夹中。

```java
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
```

