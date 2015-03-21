package com.micromap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.micromap.core.map.model.FacilityMark;
import com.micromap.core.map.model.ItemMark;
import com.micromap.core.map.model.dao.FacilityMarkDao;
import com.micromap.core.map.model.dao.ItemMarkDao;
import com.micromap.core.map.overlay.OverlayItem;
import com.micromap.db.DBManager;
import com.micromap.view.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuildingSearchActivity extends BaseActivity {
    private int screenWidth;

    private GridView gridView = null;
    private ArrayList<HashMap<String, Object>> categoryList = null;

    private HashMap<String, Integer> itemImages;
    private Map<String, Integer> category_type;
    private EditText searchEdit;
    private ImageButton imageButton;
    private TextView text;

    private MicroMapApplication application;
    private Context mContext;

    private String TAG = "SearchBuilding";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.building_search_activity);

        application = (MicroMapApplication) getApplication();
        mContext = getApplicationContext();

        /** 初始化分类的数据 */
        itemImages = new HashMap<String, Integer>();

        itemImages.put("教室", R.drawable.classroom);
        itemImages.put("学院", R.drawable.college);
        itemImages.put("医院", R.drawable.hospital);
        itemImages.put("宿舍", R.drawable.dorm);
        itemImages.put("餐厅", R.drawable.dining);
        itemImages.put("学校", R.drawable.school);
        itemImages.put("咖啡厅", R.drawable.coffee);
        itemImages.put("图书馆", R.drawable.library);
        itemImages.put("厕所", R.drawable.wc);
        itemImages.put("ATM机", R.drawable.atm);
        itemImages.put("书店", R.drawable.bookshop);
        itemImages.put("超市", R.drawable.market);

        category_type = new HashMap<String, Integer>();

        category_type.put("教室", 1);
        category_type.put("学院", 2);
        category_type.put("医院", 3);
        category_type.put("宿舍", 4);
        category_type.put("餐厅", 5);
        category_type.put("学校", 6);
        category_type.put("咖啡厅", 7);
        category_type.put("图书馆", 8);
        category_type.put("厕所", 9);
        category_type.put("ATM机", 10);
        category_type.put("书店", 11);
        category_type.put("超市", 12);
        /*
		 * 获得XML中的控件
		 */
        getAllWidgets();

        categoryList = new ArrayList<HashMap<String, Object>>();
        for (Map.Entry<String, Integer> entry : itemImages.entrySet()) {
            String mark = entry.getKey();
            int id = entry.getValue();
            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put("Image", id);
            hashMap.put("Text", mark);
            categoryList.add(hashMap);
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, categoryList,
                R.layout.building_search_category_item,
                new String[]{"Image", "Text"},
                new int[]{R.id.item_image, R.id.item_text});

        gridView.setAdapter(simpleAdapter);
        //ItemAdapter adapter = new ItemAdapter(titles, images, this);
        //gridView.setAdapter(simpleAdapter);

        gridView.setOnItemClickListener(new ItemClickListener());
        imageButton.setOnClickListener(new MyOnClickListener());
    }

    protected void getAllWidgets() {
		/*
		 * 获得XML中的控件
		 */
        screenWidth = getWindowManager().getDefaultDisplay().getWidth();
        searchEdit = (EditText) findViewById(R.id.search_content_text);
        imageButton = (ImageButton) findViewById(R.id.do_search_button);
        text = (TextView) findViewById(R.id.result_content);
        gridView = (GridView) findViewById(R.id.gridview);


        int columnWidth = screenWidth / 5;
        gridView.setColumnWidth(columnWidth);
    }

    private class ItemClickListener implements OnItemClickListener {
        /**
         *
         */
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            // arg0就是那个DateList
            HashMap<String, Object> item = categoryList.get(arg2);
            // 显示应用标题的那个TextView
            String type = item.get("Text").toString();
            SQLiteDatabase database = DBManager.getInstance().getDatabase();
            int i = category_type.get(type);
            FacilityMarkDao facilityMarkDao = new FacilityMarkDao(database);
            List<FacilityMark> facilityMarks = facilityMarkDao.getFacilityMarkByType(i);
            List<ItemMark> itemMarks = ItemMarkDao.getMarksByFacilityMarks(facilityMarks);

            //存储查询结果
            application.itemMarks = itemMarks;
            Log.e(TAG, "itemMarks" + itemMarks.size());

            goToMapActivity();
        }
    }

    private class MyOnClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            // 得到查询信息
            String search_content = searchEdit.getText().toString();
            Intent intent = new Intent(BuildingSearchActivity.this, BuildingSearchResultActivity.class);

            Bundle bundle = new Bundle();
            bundle.putString("type", "");
            bundle.putString("name", search_content);

            if (search_content == null || search_content.length() == 0) {
                Toast toast = Toast.makeText(mContext, "请输入查询名称", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            } else {
                intent.putExtras(bundle);
                startActivityForResult(intent, 0);
            }
            text.setText(search_content);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //返回MapActivity界面
        goToMapActivity();

    }

    /**
     * 返回MapActivity界面
     */
    private void goToMapActivity() {
        setResult(MapActivity.ACTIVITY_RESULT_CODE);
        finish();
    }
}
