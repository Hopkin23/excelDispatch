package com.example.dispatch.serivice;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.example.dispatch.dao.FillDataMapper;
import com.example.dispatch.entity.FillData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


/**
 * @author liuhp
 * @Description service主要业务操作
 * @date 2022/2/17 21:47
 */
@Service
public class DispatchService {

    /**
     * 文件生成的主文件夹（需要提前新建好）
     */
    @Value("${path.dirStr}")
    private String dirStr;

    /**
     * excel模板文件路径及其名称，提前放入静态资源的excel文件夹下
     */
    @Value("${path.excelDemoName}")
    private String excelDemo;

    @Resource
    private FillDataMapper fillDataMapper;

    /**
     * 读取数据库中的信息
     * @return
     */
    public List<FillData> read() throws IOException {
        List<FillData> list = fillDataMapper.list();
        group(list);
        return list;
    }

    /**
     * 按照组别创建不同文件夹
     * 在文件夹中批量填充excel到demo.xlsx模板中
     * @param list
     * @throws IOException
     */
    public void group(List<FillData> list) throws IOException {
        // 转换成map
        for (FillData fillData : list) {
            String group = fillData.getGroup();

            // 创建文件夹 以group命名 当文件夹不存在时创建文件夹
            Path path = Paths.get(dirStr + group);
            boolean pathExists = Files.exists(path, LinkOption.NOFOLLOW_LINKS);
            if(!pathExists) {
                Files.createDirectory(path);
            }

            // easyExcel写入
            writeExcel(path.toString(), fillData);

        }
    }

    /**
     * 使用模板单组数据填充
     *
     * @param pathSuffix excel文件保存路径前缀
     * @param fillData 对应一份文件填充的对象信息
     */
    public void writeExcel(String pathSuffix, FillData fillData) {
        // 生成的excel文件路径及名称
        String fileName = pathSuffix + File.separator + fillData.getHouseNumber() + "_" + fillData.getName() + ".xlsx";

        // 读取填充模板
        InputStream templatePathName = this.getClass().getClassLoader().getResourceAsStream("excel"+ File.separator + excelDemo);

        // easyExcel
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ExcelWriter workBook = EasyExcel.write(fileName).file(out).withTemplate(templatePathName).excelType(ExcelTypeEnum.XLSX).build();
        WriteSheet sheet = EasyExcel.writerSheet().build();

        // 数据填充
        workBook.fill(fillData, sheet);

        workBook.finish();

    }

}
