package com.imooc.utils.ReadAndWriteFile;

import java.io.*;

public class ReadAndWriteFile{


    /**
     * 读取文件
     * @param filePath
     * @return
     * @throws Exception
     */
    public static String readFile(String filePath){
        String str = "";

        BufferedInputStream in = null;
        try {

            in = new BufferedInputStream(new FileInputStream(filePath));

            int available = in.available();

            if(available!=0){
                byte[] bytes = new byte[in.available()];

                int len=-1;
                while((len=in.read(bytes,0, bytes.length))!=-1){
                    str=new String(bytes,0,len,"UTF-8");
                }

            }else
            {
                str=" ";
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("str:"+str);

       return str;
    }

    /**
     * 写文件
     * @param str
     * @return
     */
    public static int writeFile(String url,String str) {
        //创建文件
        File file = new File(url);

        BufferedOutputStream out = null;
        try {
            out = new BufferedOutputStream(new FileOutputStream(file));
            byte[] bytes = str.getBytes("UTF-8");

            out.write(bytes);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return 1;
    }
}
