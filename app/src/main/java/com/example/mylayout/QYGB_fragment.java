package com.example.mylayout;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class QYGB_fragment extends Fragment {
    private static final String TAG = "QYGB_fragment";

    private View view;
    private List<RelativeLayout> cards;
    private GridLayout gl_qygb_card;
    private Button btn_add_area;
    private Button btn_default_selected;
    private LinearLayout add_toucherLayout;
    private LinearLayout del_toucherLayout;
    private LinearLayout client_list_toucherLayout;
    private WindowManager windowManager;
    private WindowManager.LayoutParams add_wmParams;
    private WindowManager.LayoutParams del_wmParams;
    private WindowManager.LayoutParams client_list_wmParams;
    private RoundRectLayout m_rv = null;
    private boolean b_rename = false;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.qygb_fragment, container, false);
        initViews(view);
        initDatas();
        return view;
    }

    private void initDatas() {
        btn_add_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d(TAG, "QYGB_fragment onClick");
                b_rename = false;
                if (add_toucherLayout != null) {
                    windowManager.addView(add_toucherLayout, add_wmParams);
                } else {
                    createAddAreaToucher();
                }
            }
        });

        btn_default_selected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (m_rv != null) {
                    removeDefaultSelected();
                    m_rv.findViewWithTag("default_selected").setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void initViews(View view) {
        gl_qygb_card = view.findViewById(R.id.gl_qygb_card);
        btn_add_area = view.findViewById(R.id.btn_add_area);
        btn_default_selected = view.findViewById(R.id.btn_default_selected);

        cards = new ArrayList<>();

        createMainCard("全部终端");
    }

    /**
     * @param title     标题
     * @param bUse      下方的按钮是否可用
     * @param bSelected 是否显示默认选择按钮
     */
    private void createCard(final String title, boolean bUse, boolean bSelected) {
        RelativeLayout.LayoutParams rlp;

        RoundRectLayout relativeLayout = new RoundRectLayout(view.getContext());
        //relativeLayout.setBackgroundColor(getResources().getColor(R.color.colorAccent, null));
        relativeLayout.setBackgroundColor(getResources().getColor(R.color.qygb_btn_bg, null));
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_rv = (RoundRectLayout) v;
                setSelectBackground();
            }
        });

        TextView tv_name = new TextView(view.getContext());
        tv_name.setText(title);
        tv_name.setId(View.generateViewId());
        tv_name.setTextSize(60);
        tv_name.setTag("tv_name");
        tv_name.setTextColor(getResources().getColor(R.color.qygb_btn_text_color, null));

        rlp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rlp.addRule(RelativeLayout.CENTER_HORIZONTAL);
        rlp.setMargins(0, 30, 0, 0);
        relativeLayout.addView(tv_name, rlp);

        /// img edit
        ImageView imageView = new ImageView(view.getContext());
        imageView.setId(View.generateViewId());
        if (bUse) {
            imageView.setImageResource(R.drawable.btn_edit);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "编辑的点击事件");
                    m_rv = (RoundRectLayout) v.getParent();
                    setSelectBackground();
                    b_rename = true;
                    if (add_toucherLayout != null) {
                        windowManager.addView(add_toucherLayout, add_wmParams);
                    } else {
                        createAddAreaToucher();
                    }
                }
            });
        } else {
            imageView.setImageResource(R.drawable.btn_edit_disabled);
        }

        rlp = new RelativeLayout.LayoutParams(50, 50);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        rlp.setMargins(34, 0, 0, 20);
        relativeLayout.addView(imageView, rlp);

        //=============================================
        imageView = new ImageView(view.getContext());
        imageView.setId(View.generateViewId());
        if (bUse) {
            imageView.setImageResource(R.drawable.btn_list);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "终端列表的点击事件");
                    m_rv = (RoundRectLayout) v.getParent();
                    setSelectBackground();
                    if (client_list_toucherLayout != null) {
                        windowManager.addView(client_list_toucherLayout, client_list_wmParams);
                    } else {
                        createClientListToucher(title);
                    }
                }
            });
        } else {
            imageView.setImageResource(R.drawable.btn_list_disabled);
        }

        rlp = new RelativeLayout.LayoutParams(50, 50);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        rlp.addRule(RelativeLayout.CENTER_HORIZONTAL);
        rlp.setMargins(0, 0, 0, 20);
        relativeLayout.addView(imageView, rlp);

        //==============================================
        imageView = new ImageView(view.getContext());
        imageView.setId(View.generateViewId());
        if (bUse) {
            imageView.setImageResource(R.drawable.btn_del);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "删除的点击事件");
                    m_rv = (RoundRectLayout) v.getParent();
                    setSelectBackground();
                    if (del_toucherLayout != null) {
                        TextView textView = del_toucherLayout.findViewById(R.id.tv_del_title);
                        textView.setText("确认删除“" + title + "”么？");
                        windowManager.addView(del_toucherLayout, del_wmParams);
                    } else {
                        createDelAreaToucher(title);
                    }
                }
            });
        } else {
            imageView.setImageResource(R.drawable.btn_del_disabled);
        }

        rlp = new RelativeLayout.LayoutParams(50, 50);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        rlp.setMargins(0, 0, 25, 20);
        relativeLayout.addView(imageView, rlp);

        //==============================================
        imageView = new ImageView(view.getContext());
        imageView.setId(View.generateViewId());
        imageView.setVisibility(View.GONE);
        imageView.setTag("default_selected");
        imageView.setImageResource(R.drawable.default_selected);

        rlp = new RelativeLayout.LayoutParams(50, 50);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        rlp.setMargins(0, 10, 10, 0);
        relativeLayout.addView(imageView, rlp);

        cards.add(relativeLayout);
    }

    /**
     * 将点击的card设置为选中的绿色
     */
    private void setSelectBackground() {
        resetCardsBackground();
        m_rv.setBackgroundColor(getResources().getColor(R.color.qygb_card_bg_green, null));
    }

    /**
     * 将所有的card去除默认选择图标
     */
    private void removeDefaultSelected() {
        for (RelativeLayout relativeLayout : cards) {
            relativeLayout.findViewWithTag("default_selected").setVisibility(View.GONE);
        }
    }

    /**
     * 将所有card设置为默认背景颜色
     */
    private void resetCardsBackground() {
        for (RelativeLayout relativeLayout : cards) {
            relativeLayout.setBackgroundColor(getResources().getColor(R.color.qygb_btn_bg, null));
        }
    }

    /**
     * 创建默认card
     *
     * @param name card显示的名称
     */
    private void createMainCard(String name) {
        createCard(name, false, false);

        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.width = 330;
        params.height = 188;
        params.setMargins(60, 55, 0, 0);
        gl_qygb_card.addView(cards.get(0), params);
    }

    private void reflush() {
        gl_qygb_card.removeAllViews();

        for (RelativeLayout relativeLayout : cards) {
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 330;
            params.height = 188;
            params.setMargins(60, 55, 20, 0);
            gl_qygb_card.addView(relativeLayout, params);
        }
    }

    /**
     * 添加区域窗口的Toucher
     */
    private void createAddAreaToucher() {
        //赋值WindowManager&LayoutParam.
        Context context = view.getContext();
        add_wmParams = new WindowManager.LayoutParams();
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        //设置type.系统提示型窗口，一般都在应用程序窗口之上.
        //params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        if (Build.VERSION.SDK_INT >= 26) {//8.0新特性
            add_wmParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            add_wmParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        }


        //设置效果为背景透明.
        //params.format = PixelFormat.RGBA_8888;
        //设置flags.不可聚焦及不可使用按钮对悬浮窗进行操控.
        //wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

        //设置窗口初始停靠位置.
        add_wmParams.gravity = Gravity.CENTER;
        add_wmParams.x = 0;
        add_wmParams.y = 0;

        //设置悬浮窗口长宽数据.
        //注意，这里的width和height均使用px而非dp.这里我偷了个懒
        //如果你想完全对应布局设置，需要先获取到机器的dpi
        //px与dp的换算为px = dp * (dpi / 160).
        add_wmParams.width = 400;
        add_wmParams.height = 230;

        LayoutInflater inflater = LayoutInflater.from(view.getContext());
        //获取浮动窗口视图所在布局.
        add_toucherLayout = (LinearLayout) inflater.inflate(R.layout.qygb_add_area_layout, null);
        Button btn_confirm = add_toucherLayout.findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "确认");
                EditText et_name = add_toucherLayout.findViewById(R.id.et_set_name);
                if (b_rename) {
                    ((TextView) (m_rv.findViewWithTag("tv_name"))).setText(et_name.getText().toString());
                } else {
                    createCard(et_name.getText().toString(), true, false);
                    reflush();
                }
            }
        });
        Button btn_cancle = add_toucherLayout.findViewById(R.id.btn_cancle);
        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "取消");
                windowManager.removeView(add_toucherLayout);
            }
        });
        //添加toucherlayout
        windowManager.addView(add_toucherLayout, add_wmParams);
    }


    /**
     * 删除区域窗口的Toucher
     */
    private void createDelAreaToucher(String title) {
        //赋值WindowManager&LayoutParam.
        Context context = view.getContext();
        del_wmParams = new WindowManager.LayoutParams();
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        //设置type.系统提示型窗口，一般都在应用程序窗口之上.
        //params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        if (Build.VERSION.SDK_INT >= 26) {//8.0新特性
            del_wmParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            del_wmParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        }


        //设置效果为背景透明.
        //params.format = PixelFormat.RGBA_8888;
        //设置flags.不可聚焦及不可使用按钮对悬浮窗进行操控.
        //wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

        //设置窗口初始停靠位置.
        del_wmParams.gravity = Gravity.CENTER;
        del_wmParams.x = 0;
        del_wmParams.y = 0;

        //设置悬浮窗口长宽数据.
        //注意，这里的width和height均使用px而非dp.这里我偷了个懒
        //如果你想完全对应布局设置，需要先获取到机器的dpi
        //px与dp的换算为px = dp * (dpi / 160).
        del_wmParams.width = 400;
        del_wmParams.height = 150;

        LayoutInflater inflater = LayoutInflater.from(view.getContext());
        //获取浮动窗口视图所在布局.
        del_toucherLayout = (LinearLayout) inflater.inflate(R.layout.qygb_del_area_layout, null);
        TextView textView = del_toucherLayout.findViewById(R.id.tv_del_title);
        textView.setText("确认删除“" + title + "”么？");
        Button btn_confirm = del_toucherLayout.findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "删除确认");
                gl_qygb_card.removeView(m_rv);
                cards.remove(m_rv);
                windowManager.removeView(del_toucherLayout);
            }
        });
        Button btn_cancle = del_toucherLayout.findViewById(R.id.btn_cancle);
        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "取消");
                windowManager.removeView(del_toucherLayout);
            }
        });
        //添加toucherlayout
        windowManager.addView(del_toucherLayout, del_wmParams);
    }

    /**
     * 终端列表的Toucher
     */
    private void createClientListToucher(String title) {
        //赋值WindowManager&LayoutParam.
        client_list_wmParams = new WindowManager.LayoutParams();
        if (Build.VERSION.SDK_INT >= 26) {//8.0新特性
            client_list_wmParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            client_list_wmParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        }
        client_list_wmParams.format = PixelFormat.RGBA_8888;
        client_list_wmParams.gravity = Gravity.TOP | Gravity.RIGHT;
        client_list_wmParams.width = 800;
        client_list_wmParams.height = gl_qygb_card.getHeight();

        LayoutInflater inflater = LayoutInflater.from(view.getContext());
        //获取浮动窗口视图所在布局.
        client_list_toucherLayout = (LinearLayout) inflater.inflate(R.layout.qygb_client_list_layout, null);
        TextView tv_title = client_list_toucherLayout.findViewById(R.id.tv_title);
        tv_title.setText(title + " 终端");
        //=======================添加终端=================================================
        LinearLayout ll_left = client_list_toucherLayout.findViewById(R.id.ll_left_client_list);
        LinearLayout ll_right = client_list_toucherLayout.findViewById(R.id.ll_right_client_list);
        LinearLayout ll_child;
        for (int i = 0; i < 10; ++i) {
            ll_child = (LinearLayout) inflater.inflate(R.layout.client_layout, null);
            if (i != 5) {
                CheckBox cb = ll_child.findViewById(R.id.cb_client_select);
                cb.setChecked(true);
            }
            LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 55);
            llp.setMargins(40, 20, 0, 0);
            ll_left.addView(ll_child, llp);
        }
        for (int i = 0; i < 5; ++i) {
            ll_child = (LinearLayout) inflater.inflate(R.layout.client_layout, null);
            CheckBox cb = ll_child.findViewById(R.id.cb_client_select);
            cb.setChecked(true);
            if (i == 4) {
                TextView tv_client_name = ll_child.findViewById(R.id.tv_client_name);
                tv_client_name.setText("192.168.1.118");
            }
            LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 55);
            llp.setMargins(40, 20, 0, 0);
            ll_right.addView(ll_child, llp);
        }
        //======================= end ==================
        Button btn_confirm = client_list_toucherLayout.findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                windowManager.removeView(client_list_toucherLayout);
            }
        });
        Button btn_cancle = client_list_toucherLayout.findViewById(R.id.btn_cancle);
        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                windowManager.removeView(client_list_toucherLayout);
            }
        });

        //添加toucherlayout
        windowManager.addView(client_list_toucherLayout, client_list_wmParams);
    }
}
