/*
 * 负责地图切片的获取（从Assets中获取）
 */

package com.micromap.core.map.cache;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.InputStream;

public class MapTile {
    private Context context;
    
    public MapTile(Context context){
    	this.context = context;
    }

    public Bitmap getBitmapById(int zoom_level,int id){
    	String pathString = "zoom_"+Integer.toString(zoom_level) + "/";
    	String idString = null;
    	if(id < 10){
    		idString = "0" + Integer.toString(id);
    	} else {
    		idString = Integer.toString(id);
    	}
    	pathString += "zoom_" + idString + ".png";
        Bitmap bitmap = getImageFromAssets(pathString);
    	return bitmap;
    }

    /**
     * 读取Assert中的文件
     *
     * @param path 文件的路径
     * @return
     */
    private Bitmap getImageFromAssets(String path){
    	Bitmap image = null;
    	try{
    		AssetManager am = context.getAssets();
    		InputStream is = am.open(path);
    		image = BitmapFactory.decodeStream(is);
    		is.close();
    	}catch (Exception e) {
			// TODO: handle exception
            Log.e("MapTile -->", path);
		}
    	return image;
    }
}
