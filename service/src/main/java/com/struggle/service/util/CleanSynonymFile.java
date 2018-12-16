package com.struggle.service.util;

import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.HashSet;
import java.util.Set;


/*
* 从同义词词典中删除 停用词
* */
public class CleanSynonymFile {

    public static void main(String[] args) throws IOException {
        try {

            /*
             * 读取完整文件, 替换每一行中的空格为逗号
             */
            Reader stopReader = new FileReader(ResourceUtils.getURL("classpath:").getPath()+"mystop.dic");
            BufferedReader bufferedReader = new BufferedReader(stopReader);
            String stringStop=null;
            Set<Integer> lineIndex = new HashSet<Integer>();

            while ((stringStop = bufferedReader.readLine()) != null){
                Reader synonReader = new FileReader(ResourceUtils.getURL("classpath:").getPath()+"synonym.txt");
                BufferedReader bufSynonReader = null;
                bufSynonReader = new BufferedReader(synonReader);
                String stringSynon = null;

                int pageNum = 0;

                while ((stringSynon = bufSynonReader.readLine()) != null) {
                     String[] str = stringSynon.split(",");
                     pageNum++;
                     if (str != null && str.length >=2 && (str[0].equals(stringStop.trim()) || str[1].equals(stringStop.trim()))){
                         lineIndex.add(pageNum);
                         System.out.println(stringSynon);
                     }
                }
                // 注意这两个关闭的顺序
                bufSynonReader.close();
                synonReader.close();
            }


            /*写文件*/
            bufferedReader.close();
            stopReader.close();

            FileFormat.formatFile("synonym.txt","synonym_clean.txt",lineIndex);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}