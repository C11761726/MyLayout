package com.example.mylayout;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * 终端通话的界面
 */
public class ZDTH_fragment extends Fragment {
    private GridLayout gl_oil_gun_card;
    private View view;
    private List<RelativeLayout> cards;
    private RelativeLayout m_rl = null;

    private void initViews(View view) {
        gl_oil_gun_card = view.findViewById(R.id.gl_oil_gun_card);

        cards = new ArrayList<>();

        createCards();

        addCards();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.zdth_fragment, container, false);
        initViews(view);
        return view;
    }

    private void addCards() {
        for (int i = 0; i < cards.size(); ++i) {
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.columnSpec = GridLayout.spec(i % 5, 1.0f);
            params.setMargins(20, 20, 0, 20);
            gl_oil_gun_card.addView(cards.get(i), params);
        }
    }

    private void createCard(CARD_DATA data) {
        RelativeLayout.LayoutParams rlp;

        RelativeLayout relativeLayout = new RelativeLayout(view.getContext());
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_rl = (RelativeLayout) v;
                setSelectBackground();
            }
        });
        relativeLayout.setBackground(getResources().getDrawable(R.drawable.device_bg, null));
        relativeLayout.setGravity(Gravity.CENTER);

        ImageView imageView = new ImageView(view.getContext());
        imageView.setId(View.generateViewId());
        imageView.setImageResource(R.drawable.icon_device);
        relativeLayout.addView(imageView);

        TextView tv_state = new TextView(view.getContext());
        tv_state.setText(data.getState());
        tv_state.setId(View.generateViewId());
        tv_state.setTextSize(20);
        tv_state.setGravity(Gravity.CENTER);
        if (data.getState().equals("在线")) {
            tv_state.setTextColor(getResources().getColor(R.color.online, null));
        } else if (data.getState().equals("通话中")) {
            tv_state.setTextColor(getResources().getColor(R.color.in_call, null));
        } else if (data.getState().equals("离线")) {
            tv_state.setTextColor(getResources().getColor(R.color.offline, null));
        }
        rlp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rlp.addRule(RelativeLayout.BELOW, imageView.getId());
        rlp.addRule(RelativeLayout.ALIGN_LEFT, imageView.getId());
        rlp.addRule(RelativeLayout.ALIGN_RIGHT, imageView.getId());
        rlp.setMargins(0, 10, 5, 0);
        relativeLayout.addView(tv_state, rlp);

        TextView tv_name = new TextView(view.getContext());
        tv_name.setText(data.getName());
        tv_name.setTextSize(30);
        tv_name.setId(View.generateViewId());
        tv_name.setTypeface(Typeface.DEFAULT_BOLD);
        rlp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rlp.addRule(RelativeLayout.RIGHT_OF, imageView.getId());
        rlp.addRule(RelativeLayout.ALIGN_TOP, imageView.getId());
        rlp.setMargins(30, 0, 0, 0);
        relativeLayout.addView(tv_name, rlp);

        TextView tv_ip_address = new TextView(view.getContext());
        tv_ip_address.setText(data.getIp());
        tv_ip_address.setTextSize(25);
        tv_ip_address.setId(View.generateViewId());
        rlp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rlp.addRule(RelativeLayout.BELOW, tv_name.getId());
        rlp.addRule(RelativeLayout.RIGHT_OF, imageView.getId());
        rlp.setMargins(30, 5, 0, 0);
        relativeLayout.addView(tv_ip_address, rlp);

        TextView tv_area = new TextView(view.getContext());
        tv_area.setText(data.getArea());
        tv_area.setTextSize(25);
        tv_area.setId(View.generateViewId());
        rlp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rlp.addRule(RelativeLayout.RIGHT_OF, imageView.getId());
        rlp.addRule(RelativeLayout.ALIGN_BOTTOM, tv_state.getId());
        rlp.setMargins(30, 0, 0, 0);
        relativeLayout.addView(tv_area, rlp);

        cards.add(relativeLayout);
    }

    /**
     * 将点击的card设置为选中的背景图片
     */
    private void setSelectBackground() {
        resetCardsBackground();
        m_rl.setBackground(getResources().getDrawable(R.drawable.device_sel_bg, null));
    }

    /**
     * 将所有card设置为默认背景图片
     */
    private void resetCardsBackground() {
        for (RelativeLayout relativeLayout : cards) {
            relativeLayout.setBackground(getResources().getDrawable(R.drawable.device_bg, null));
        }
    }

    private void createCards() {
//        for (int i = 0; i < 2; ++i) {
//            CARD_DATA data = new CARD_DATA();
//            data.setState("在线");
//            data.setName("一号油机位2枪");
//            data.setIp("192.168.1.125");
//            data.setArea("监控A区");
//            createCard(data);
//        }

        for (int i = 0; i < 11; ++i) {
            CARD_DATA data = new CARD_DATA();
            data.setState("在线");
            data.setName("一号油机位2枪");
            data.setIp("192.168.1.125");
            data.setArea("监控A区");
            createCard(data);
        }

        for (int i = 0; i < 1; ++i) {
            CARD_DATA data = new CARD_DATA();
            data.setState("通话中");
            data.setName("一号油机位2枪");
            data.setIp("192.168.1.125");
            data.setArea("监控A区");
            createCard(data);
        }

        for (int i = 0; i < 5; ++i) {
            CARD_DATA data = new CARD_DATA();
            data.setState("在线");
            data.setName("一号油机位2枪");
            data.setIp("192.168.1.125");
            data.setArea("监控A区");
            createCard(data);
        }

        for (int i = 0; i < 2; ++i) {
            CARD_DATA data = new CARD_DATA();
            data.setState("离线");
            data.setName("一号油机位2枪");
            data.setIp("192.168.1.125");
            data.setArea("监控A区");
            createCard(data);
        }
    }
}
