package com.micromap.core.map.overlay;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.micromap.BuildingDetailActivity;
import com.micromap.PathFindingActivity;
import com.micromap.R;
import com.micromap.core.map.model.ItemMark;

public class ItemPopupWindow {
    private OverlayItem item;
    private int height;
    private int width;
    private String text;
    private Context context;
    public ItemPopupWindow(Context context, OverlayItem item, String text){
        this.context = context;
        this.item = item;
        this.text = text;
        Paint paint = new Paint();
        paint.setTextSize(14);
        this.height = 30;
        this.width = 20 + (int)paint.measureText(text) + 20;
    }
    
    /**
     * 绘制popup层，位置参照与父View的左上角
     *
     * @param parent    popup对应的父View
     * @param x         popup相对于父View的X轴坐标
     * @param y         popup相对于父View的Y轴坐标
     */
    public  void showItemPopupWindow(View parent, int x, int y){
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE );
        View view = inflater.inflate(R.layout.pop_building_item, null);
        
        PopupWindow popupWindow = new PopupWindow(view, 
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        
        ImageView imageView1 = (ImageView) view.findViewById(R.id.building_pop_mark);
        imageView1.setOnClickListener(listener);
        TextView textView = (TextView)view.findViewById(R.id.building_pop_text);
        textView.setText(text);
        textView.setOnClickListener(listener);
        ImageView imageView2 = (ImageView)view.findViewById(R.id.building_pop_path);
        imageView2.setOnClickListener(listener);
        
        //必须设置背景
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //设置焦点
        popupWindow.setFocusable(true);
        //设置点击其他地方 就消失
        popupWindow.setOutsideTouchable(true);
        x = x - width / 2 - 7;
        y = y - 15;
        popupWindow.showAtLocation(parent, Gravity.LEFT|Gravity.TOP, x, y);
    }
    
    /**
     * 点击道路上的点显示的提示
     *
     * @param parent
     * @param x
     * @param y
     */    
    public void showNavPopupWindow(View parent, int x, int y){
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE );
        View view = inflater.inflate(R.layout.pop_nav_item, null);
        PopupWindow popupWindow = new PopupWindow(view, 
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView textView = (TextView)view.findViewById(R.id.nav_pop_item_text);
        textView.setText(text);
        
        //必须设置背景
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //设置焦点
        popupWindow.setFocusable(true);
        //设置点击其他地方 就消失
        popupWindow.setOutsideTouchable(true);
        x = x - width / 2 - 7;
        y = y - 15;
        popupWindow.showAtLocation(parent, Gravity.LEFT|Gravity.TOP, x, y);
    }
    
    private OnClickListener listener = new OnClickListener() {
        private Intent intent;
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            if(v.getId() == R.id.building_pop_path){
                intent = new Intent(context, PathFindingActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if(item != null){
                    Bundle bundle = new Bundle();
                    bundle.putString("", item.getTitle());
                    //bundle.putSerializable("itemMark", item);
                    intent.putExtras(bundle);
                }
                context.startActivity(intent);
                
            }else{
                intent = new Intent(context,BuildingDetailActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if(item != null){
                    Bundle bundle = new Bundle();
                    intent.putExtra("beginPosition", item.getTitle());
                    //bundle.putSerializable("itemMark", item);
                    intent.putExtras(bundle);
                }
                context.startActivity(intent);
    
            }
        }
    };
}
