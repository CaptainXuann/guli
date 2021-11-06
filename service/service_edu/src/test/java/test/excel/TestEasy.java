package test.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author
 */
public class TestEasy {
    public static void main(String[] args) {
//        //实现excel写操作
//        //1、设置写入文件夹地址和excel文件名称
        String filename="F:\\01.xlsx";
//
//        //调用easyExcel里面的方法实现写操作
//        //参数1：文件名称
//        //参数2：对应实体类
//        EasyExcel
//                .write(filename,DemoData.class)
//                    .sheet("学生列表")
//                .doWrite(getLists());

        //读操作
        EasyExcel.read(filename, DemoData.class, new ExcelListener()).sheet().doRead();

    }

    //创建方法返回List集合
    private static List<DemoData> getLists(){
        ArrayList<DemoData> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            DemoData demoData = new DemoData();
            demoData.setSno(i);
            demoData.setSname("achang ："+ i);
            list.add(demoData);
        }
        return list;
    }

}
