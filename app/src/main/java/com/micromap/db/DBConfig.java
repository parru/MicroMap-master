package com.micromap.db;

import android.os.Environment;

import java.io.File;

/**
 * Created by pingfu on 14/12/4.
 */
public class DBConfig {
    /** 默认的包名 */
    public static final String PACKAGE_NAME = "com.micromap";

    /** 数据库的名称 */
    public static final String DB_NAME = "micromap.db";

    /** 数据库文件放到系统文件夹所在的路径 */
    public static String DB_FOLDER_PATH = "/data";

    /** 数据库文件放到系统文件中的路径 */
    public static String DB_FILE_PATH = "";

    /**
     * 得到数据库的路径
     *
     * @return 数据库在手机存储的路径
     */
    static {
        DB_FOLDER_PATH += Environment.getDataDirectory().getAbsolutePath();
        DB_FOLDER_PATH += File.separator + PACKAGE_NAME + "/databases";
        DB_FILE_PATH += DB_FOLDER_PATH + File.separator + DB_NAME;
    }

}
