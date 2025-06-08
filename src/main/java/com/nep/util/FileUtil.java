package com.nep.util;

import java.io.*;

public class FileUtil {

    public static Object readObject(String filepath){
        File file = new File(filepath);
        InputStream is = null;
        ObjectInputStream ois = null;
        Object object = null;
        try {
            is = new FileInputStream(file);
            ois = new ObjectInputStream(is);
            object = ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(ois != null){
                    ois.close();
                }
                if(is != null){
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return object;
    }
    public static void writeObject(String filepath,Object obj){
        File file = new File(filepath);
        OutputStream os = null;
        ObjectOutputStream oos = null;
        try {
            os = new FileOutputStream(file);
            oos = new ObjectOutputStream(os);
            oos.writeObject(obj);
            oos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(oos != null){
                    oos.close();
                }
                if(os != null){
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
