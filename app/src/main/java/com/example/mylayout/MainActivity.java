package com.example.mylayout;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";

    //左边的父布局
    private LinearLayout ll_left_root_layout;
    //右边的父布局
    private FrameLayout fl_right_root_layout;
    //保存左侧所有按钮
    private List<Button> btns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.root_layout);

        initViews();
        initDatas();
    }

    private void doNext() {
        //ll_left_root_layout.removeAllViews();
        btns.clear();
        addLeftLayout();

        getWindow().getDecorView().findViewWithTag(Constants.btn_tag_lslx).performClick();
    }

    private void addLeftLayout() {
        addButton("终端通话", Constants.btn_tag_zdth, R.drawable.icon_camera, 20, 20, -10, 0);
        addButton("区域广播", Constants.btn_tag_qygb, R.drawable.icon_area, 20, 12, -10, 0);
        addButton("实时监控", Constants.btn_tag_ssjk, R.drawable.icon_camera2, 20, 12, -10, 0);
        addButton("历史录像", Constants.btn_tag_lslx, R.drawable.icon_record, 20, 12, -10, 0);
        addButton("系统设置", Constants.btn_tag_xtsz, R.drawable.icon_setting, 20, 12, -10, 0);
    }

    private void addButton(String title, String tag, int resID, int left, int top, int right, int bottom) {
        Button btn = new Button(this);
        btn.setText(title);
        btn.setId(View.generateViewId());
        btn.setTag(tag);
        btn.setTextColor(getResources().getColor(R.color.btn_text_color, null));
        btn.setPadding(20, 0, 0, 0);
        Drawable drawable_n = getResources().getDrawable(resID, null);
        drawable_n.setBounds(0, 0, drawable_n.getMinimumWidth() + 10, drawable_n.getMinimumHeight() + 10);
        btn.setCompoundDrawables(drawable_n, null, null, null);
        btn.setTextSize(getResources().getDimensionPixelSize(R.dimen.btn_text_size));
        btn.setHeight((int) getResources().getDimension(R.dimen.left_root_btn_height));
        btn.setBackground(getDrawable(R.drawable.btn_bg));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(left, top, right, bottom);
        ll_left_root_layout.addView(btn, lp);

        btn.setOnClickListener(this);
        btns.add(btn);
    }

    private void initDatas() {
        btns = new ArrayList<>();
    }

    private void initViews() {
        ll_left_root_layout = findViewById(R.id.ll_left_root_layout);
        fl_right_root_layout = findViewById(R.id.fl_right_root_layout);
        replaceFragment(new ZDTH_fragment());
    }

    //在大于23的android版本中,文件读写需要动态申请权限
    private void verifyStoragePermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if ((this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) ||
                    (this.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
                this.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, Constants.PERMISSION_ALLOW);
            } else {
                Log.d(TAG, "已有权限,不需要再申请");
                if (Settings.canDrawOverlays(MainActivity.this)) {
                    doNext();
                } else {
                    //若没有权限，提示获取.
                    Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                    Toast.makeText(MainActivity.this, "需要取得权限以使用悬浮窗", Toast.LENGTH_SHORT).show();
                    //startActivity(intent);
                    startActivityForResult(intent, Constants.PERMISSION_OVERLAY_REQ_CODE);
                }
            }
        } else {
            doNext();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Constants.PERMISSION_ALLOW:  //动态申请的读写权限
            case Constants.PERMISSION_OVERLAY_REQ_CODE:
                if ((!Settings.canDrawOverlays(this)) && ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED))) {
                    // SYSTEM_ALERT_WINDOW permission not granted...
                    Toast.makeText(this, "Permission Denieddd by user.Please Check it in Settings", Toast.LENGTH_SHORT).show();
                } else {
                    // Already hold the SYSTEM_ALERT_WINDOW permission, do addview or something.
                    doNext();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 设置所有按钮为正常颜色
     */
    private void setBtnsTextNormalColor() {
        for (Button btn : btns) {
            btn.setTextColor(getResources().getColor(R.color.btn_text_color, null));
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fl_right_root_layout, fragment);
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        String tag = (String) v.getTag();
        Log.d(TAG, "=onClick tag=>>" + tag);
        //==================设置选中时字体颜色========================>>>>>>>>>>
        if ((tag.equals(Constants.btn_tag_zdth)) ||
                (tag.equals(Constants.btn_tag_qygb)) ||
                (tag.equals(Constants.btn_tag_ssjk)) ||
                (tag.equals(Constants.btn_tag_lslx)) ||
                (tag.equals(Constants.btn_tag_xtsz))) {
            setBtnsTextNormalColor();
            ((Button) v).setTextColor(getResources().getColor(R.color.btn_text_select_color, null));
        }
        //============end=================>>>>>>
        //==     添加点击事件   ==//
        //1、终端通话
        if (tag.equals(Constants.btn_tag_zdth)) {
            replaceFragment(new ZDTH_fragment());
        } else if (tag.equals(Constants.btn_tag_ssjk)) {
            replaceFragment(new SSJK_fragment());
        } else if (tag.equals(Constants.btn_tag_qygb)) {
            replaceFragment(new QYGB_fragment());
        } else if (tag.equals(Constants.btn_tag_lslx)) {
            replaceFragment(new LSLX_fragment());
        } else if (tag.equals(Constants.btn_tag_xtsz)) {
            replaceFragment(new XTSZ_fragment());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //大于23版本的权限申请
        verifyStoragePermissions();
    }
}
