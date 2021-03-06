package com.micromap.model.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.util.Xml;

import com.micromap.core.FileManager;
import com.micromap.core.map.model.BuildingMark;
import com.micromap.model.Facility;
import com.micromap.model.TBManager;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class FacilityDao extends TBManager {
    public static final String TABLE_NAME = "facility";
    public static final String FILE_NAME = "Facility.xml";
    public static final String ID = "facility_id";
    public static final String BUILDING_ID = "building_id";
    public static final String NAME = "facility_name";
    public static final String TYPE = "facility_type";
    public static final String DESCRIPTION = "facility_description";

    private SQLiteDatabase database;

    public FacilityDao(SQLiteDatabase database) {
        super(database);
        this.database = database;
    }

    @Override
    public void createTable() {
        // TODO Auto-generated method stub
        String sql = "create table " + TABLE_NAME + "(";
        sql += ID + " INTEGER primary key,";
        sql += BUILDING_ID + " INTEGER,";
        sql += NAME + " VARCHAR(50),";
        sql += TYPE + " INTEGER";
        sql += ");";
        database.execSQL(sql);
    }

    public void insertElem(Facility facility) {
        if (facility == null) {
            return;
        }

        ContentValues content = new ContentValues();
        content.put(ID, facility.getId());
        content.put(NAME, facility.getName());
        content.put(TYPE, facility.getType());
        database.insert(TABLE_NAME, null, content);
    }

    @Override
    public void dropTable() {
        // TODO Auto-generated method stub
        if (tableIsExist(TABLE_NAME)) {
            String sql = "drop table " + TABLE_NAME;
            Log.i("drop", sql);
            database.execSQL(sql);
        }
    }

    @Override
    public void updateTable() {
        // TODO Auto-generated method stub
        FileManager fileManager = new FileManager();
        InputStream input = fileManager.readFileFromRAM(fileManager.XML_DIR, FILE_NAME);
        if (input != null) {
            dropTable();
            createTable();
            parserXml(input);
        } else {
            Log.i(FILE_NAME, "file is null");
        }
    }

    /*
     * 根据Facility的编号查找
     * @param id Facility的编号
     * @return 一个Facility对象
     */
    public Facility getFacilityById(int id) {
        Facility facility = null;
        Cursor cursor = database.query("facility", null, "facility_id = ?",
                new String[]{"" + id}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                int f_id = cursor.getInt(cursor.getColumnIndex(ID));
                int b_id = cursor.getInt(cursor.getColumnIndex(BUILDING_ID));
                String f_name = cursor.getString(cursor.getColumnIndex(NAME));
                int f_type = cursor.getInt(cursor.getColumnIndex(TYPE));
                String f_desc = cursor.getString(cursor.getColumnIndex(DESCRIPTION));
                if (f_id == id) {
                    facility = new Facility(f_id, b_id, f_name, f_type);
                    facility.setDescription(f_desc);
                    break;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return facility;
    }

    /*
     * 根据Facility的名称查找
     * @param name 要查找的Facility名称
     * @return 一组Facility对象
     */
    public List<Facility> getFacilityByName(String name) {
        List<Facility> facilities = new ArrayList<Facility>();
        Cursor cursor = database.query(TABLE_NAME, null, "facility_name like ?", new String[]{"%" + name + "%"},
                null, null, null);
        if (cursor.moveToFirst()) {
            do {
                int f_id = cursor.getInt(cursor.getColumnIndex(ID));
                int b_id = cursor.getInt(cursor.getColumnIndex(BUILDING_ID));
                String f_name = cursor.getString(cursor.getColumnIndex(NAME));
                int f_type = cursor.getInt(cursor.getColumnIndex(TYPE));
                String f_desc = cursor.getString(cursor.getColumnIndex(DESCRIPTION));
                // 判断要查的name和数据库中的name的相似度
                // double d = LD.sim(f_name, name);
                // if(d > LD.SIMILARITY){
                Facility facility = new Facility(f_id, b_id, f_name, f_type, f_desc);
                facilities.add(facility);
                // }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return facilities;
    }

    /**
     * 根据Facility的类型查找
     *
     * @param type 要查找的Facility的类型
     * @return 一组Facility对象
     */
    public List<Facility> getFacilityByType(int type) {
        List<Facility> facilities = new ArrayList<Facility>();
        Cursor cursor = database.query("facility", null, "facility_type = ?", new String[]{"" + type}, null, null,
                null);
        if (cursor.moveToFirst()) {
            do {
                int f_id = cursor.getInt(cursor.getColumnIndex(ID));
                int b_id = cursor.getInt(cursor.getColumnIndex(BUILDING_ID));
                String f_name = cursor.getString(cursor.getColumnIndex(NAME));
                int f_type = cursor.getInt(cursor.getColumnIndex(TYPE));
                String f_desc = cursor.getString(cursor.getColumnIndex(DESCRIPTION));

                // 判断要查的Facility是否属于特定的type类型
                if (f_type == type) {
                    Facility facility = new Facility(f_id, b_id, f_name, f_type, f_desc);
                    facilities.add(facility);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return facilities;
    }

    /**
     * 根据XML数据更新表
     *
     * @param in XML文件输入流
     */
    public void parserXml(InputStream in) {
        try {
            // 得到文件流，并设置编码方式
            XmlPullParser xmlParser = Xml.newPullParser();
            xmlParser.setInput(in, "UTF-8");

            // 这里有开始文档，结束文档，开始标签，结束标签，文本等等事件。
            int evtType = xmlParser.getEventType();
            // 一直循环，直到文档结束
            Facility facility = null;
            while (evtType != XmlPullParser.END_DOCUMENT) {
                switch (evtType) {

                    case XmlPullParser.START_TAG: // 标签开始

                        String tag = xmlParser.getName();
                        if (tag.equals(TABLE_NAME)) {
                            // 如果是user标签开始，则说明需要实例化对象了
                            facility = new Facility();
                        }
                        if (tag.equalsIgnoreCase(ID)) {
                            // 取出User标签中的一些属性值
                            String id = xmlParser.nextText();
                            facility.setId(Integer.parseInt(id));
                        }
                        if (tag.equalsIgnoreCase(BUILDING_ID)) {
                            // 取出User标签中的一些属性值
                            String b_id = xmlParser.nextText();
                            facility.setBuildingId(Integer.parseInt(b_id));
                        }
                        if (tag.equalsIgnoreCase(NAME)) {
                            String name = xmlParser.nextText();
                            facility.setName(name);
                        }
                        if (tag.equalsIgnoreCase(TYPE)) {
                            String type = xmlParser.nextText();
                            facility.setType(Integer.parseInt(type));
                        }
                        if (tag.equalsIgnoreCase(DESCRIPTION)) {
                            // 取出User标签中的一些属性值
                            String desc = xmlParser.nextText();
                            facility.setDescription(desc);
                        }
                        break;

                    case XmlPullParser.END_TAG:
                        // 如果遇到river标签结束，则把river对象添加进集合中
                        if (xmlParser.getName().equals(TABLE_NAME)) {
                            if (facility != null) {
                                insertElem(facility);
                            }
                        }
                        break;
                    default:
                        break;
                }
                evtType = xmlParser.next();
            }
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }
}
