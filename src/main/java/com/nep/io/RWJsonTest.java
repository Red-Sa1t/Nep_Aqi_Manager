package com.nep.io;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nep.po.Admin;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class RWJsonTest {
    // 获取当前项目类路径（读取json文件的时候会用到，对象格式数据用不到此对象）
    public static ClassLoader classLoader = RWJsonTest.class.getClassLoader();
    // 操作json格式数据是用的工具类对象（操作json文件的时候会用到，对象格式数据用不到此对象）
    public static ObjectMapper objectMapper = new ObjectMapper();


    // 用Jackson工具类读取json格式类型数据时，参照此方法内容
    public static List<Admin> getListByJackson() {

        List<Admin> admin = new ArrayList<>();
        try {
            // 通过类加载器加载json格式文件内容
            InputStream inputStream = classLoader.getResourceAsStream("NepDatas/JSONData/admins.json");
            // 将JSON数组字符串转换为Employee对象列表
            admin = objectMapper.readValue(inputStream, new TypeReference<List<Admin>>() {
            });

            // 打印解析后的对象列表
            for (Admin employee : admin) {
                System.out.println(employee);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return admin;
    }

    // 用Jackson工具类写入json格式类型数据时，参照此方法内容
    public static void writeListByJson(List<Admin> admins) {
        try {
            OutputStream outputStream =
                    new FileOutputStream(RWJsonTest.class.getClassLoader().getResource("NepDatas/JSONData/admins.json").getFile());

            objectMapper.writeValue(outputStream, admins);

            outputStream.close();

            System.out.println("数据写入成功.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // 调用读取json格式数据方法
        List<Admin> admins = getListByJackson();
        admins.add(new Admin("1", "123", "王一"));
        // 调用写入json格式数据方法
        writeListByJson(admins);
    }
}
