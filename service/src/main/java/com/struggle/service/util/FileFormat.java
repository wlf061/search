package com.struggle.service.util;

import org.springframework.util.ResourceUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

/****
 *
 * */
public class FileFormat {

    public static void main(String[] args) throws IOException {
        try {
            StringBuffer sb = new StringBuffer("");

            /*
             * 读取完整文件, 替换每一行中的空格为逗号
             */
            System.out.println(ResourceUtils.getURL("classpath:").getPath());
            Reader reader = new FileReader(ResourceUtils.getURL("classpath:").getPath()+"origin_syno.txt");
            BufferedReader bufferedReader = new BufferedReader(reader);
            String string = null;

            //写文件
            Writer writer = new FileWriter(ResourceUtils.getURL("classpath:").getPath()+"synonym.txt");
            BufferedWriter bw = new BufferedWriter(writer);

            while ((string = bufferedReader.readLine()) != null) {
                bw.write(string.replace(" ",","));
                bw.newLine();
            }
            bw.close();
            writer.close();
            // 注意这两个关闭的顺序
            bufferedReader.close();
            reader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}