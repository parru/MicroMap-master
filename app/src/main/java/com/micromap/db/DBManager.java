package com.micromap.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import com.micromap.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;


public class DBManager {
    private static DBManager instance;
    private SQLiteDatabase database;

    private static final int INIT_DB_STATE = 0;
    private static final int INSTALLED_DB_STATE = 1;
    private static final int UNINSTALL_DB_STATE = 2;

    private int currentState;

    private DBManager(){
    }
    
    /**
	 * 如果数据库存在，打开数据库，否则从raw中读取数据库
     *
     * @param context
     * @param db_name
	 */
    private void installLocalDatabase(Context context, String db_name){
    	/*
    	 * 在测试阶段，每次都重新更新数据库
    	 */
    	try {
            currentState = INIT_DB_STATE;
			if(!(new File(db_name).exists())){
				if(! new File(DBConfig.DB_FOLDER_PATH).exists()){
					new File(DBConfig.DB_FOLDER_PATH).mkdir();
				}
				InputStream is = context.getResources().openRawResource(R.raw.micromap);
				FileOutputStream out = new FileOutputStream(db_name);
				byte buffer[] = new byte[4000];
				int length = 0;
				while((length = is.read(buffer)) > 0 ){
					out.write(buffer,0,length);
				}
				out.close();
				is.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
            currentState = UNINSTALL_DB_STATE;
		}
		database = SQLiteDatabase.openOrCreateDatabase(db_name, null);
        currentState = INSTALLED_DB_STATE;
    }
    
    public static synchronized DBManager getInstance(){
        if(instance == null){
            instance = new DBManager();
        }
    	return instance;
    }

    /**
     * 安装本地数据库
     */
    public void installLocalDatabase(Context context){
        installLocalDatabase(context, DBConfig.DB_FILE_PATH);
    }

    /**
     * 得到本地数据库
     *
     * @return
     */
	public SQLiteDatabase getDatabase() {
        if(currentState != INSTALLED_DB_STATE){
            if(!(new File(DBConfig.DB_FILE_PATH).exists())){

            }
        }
		return database;
	}
}

