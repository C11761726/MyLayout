package com.example.mylayout;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class LSLX_fragment extends Fragment {
    private View view;
    private TextView tv_date_pic;
    private Context context;
    private GridLayout gl_lslx_card;
    private List<RelativeLayout> cards;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.lslx_fragment, container, false);
        context = view.getContext();
        initViews();
        return view;
    }

    private void initViews() {
        cards = new ArrayList<>();
        gl_lslx_card = view.findViewById(R.id.gl_lslx_card);
        tv_date_pic = view.findViewById(R.id.tv_date_pic);
        Calendar currentDate = Calendar.getInstance();
        tv_date_pic.setText(currentDate.get(Calendar.YEAR) + "-" + currentDate.get(Calendar.MONTH) + "-" + currentDate.get(Calendar.DATE));
        tv_date_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickDialog();
            }
        });

        //===========
        createCards();
        addCards();
    }

    long day = 24 * 60 * 60 * 1000;

    private void showDatePickDialog() {
        CustomDatePickerDialogFragment fragment = new CustomDatePickerDialogFragment();
        fragment.setOnSelectedDateListener(new CustomDatePickerDialogFragment.OnSelectedDateListener() {
            @Override
            public void onSelectedDate(int year, int monthOfYear, int dayOfMonth) {
                tv_date_pic.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
            }
        });
        Bundle bundle = new Bundle();
        Calendar currentDate = Calendar.getInstance();
        currentDate.setTimeInMillis(System.currentTimeMillis());
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        currentDate.set(Calendar.MILLISECOND, 0);
        bundle.putSerializable(CustomDatePickerDialogFragment.CURRENT_DATE, currentDate);


        long start = currentDate.getTimeInMillis() - day * 2;
        long end = currentDate.getTimeInMillis() - day;
        Calendar startDate = Calendar.getInstance();
        startDate.setTimeInMillis(start);
        Calendar endDate = Calendar.getInstance();
        endDate.setTimeInMillis(end);
        bundle.putSerializable(CustomDatePickerDialogFragment.START_DATE, startDate);
        bundle.putSerializable(CustomDatePickerDialogFragment.END_DATE, currentDate);

        fragment.setArguments(bundle);
        fragment.show(getChildFragmentManager(), CustomDatePickerDialogFragment.class.getSimpleName());
    }

    /**
     * @param date 日期
     * @param time 时间
     */
    private void createCard(final String date, final String time) {
        RelativeLayout.LayoutParams rlp;

        RelativeLayout relativeLayout = new RelativeLayout(view.getContext());
        relativeLayout.setBackground(getResources().getDrawable(R.drawable.lslx_card_shape, null));
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //每一个块的点击事件
                resetViewsSelectedState();
                ImageView iv = v.findViewWithTag("iv_select");
                iv.setVisibility(View.VISIBLE);
            }
        });

        /// img edit
        ImageView imageView = new ImageView(view.getContext());
        imageView.setId(View.generateViewId());
        imageView.setImageResource(R.drawable.icon_video);

        rlp = new RelativeLayout.LayoutParams(80, 80);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        rlp.setMargins(10, 10, 10, 10);
        relativeLayout.addView(imageView, rlp);

        TextView tv_date = new TextView(view.getContext());
        tv_date.setText(date);
        tv_date.setId(View.generateViewId());
        tv_date.setTextSize(30);
        tv_date.setTag("tv_date");
        tv_date.setTextColor(getResources().getColor(R.color.black, null));

        rlp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        rlp.setMargins(0, 15, 15, 0);
        relativeLayout.addView(tv_date, rlp);

        ////////===============================================
        TextView tv_time = new TextView(view.getContext());
        tv_time.setText(time);
        tv_time.setId(View.generateViewId());
        tv_time.setTextSize(30);
        tv_time.setTag("tv_time");
        tv_time.setTextColor(getResources().getColor(R.color.black, null));

        rlp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        rlp.addRule(RelativeLayout.BELOW, tv_date.getId());
        rlp.addRule(RelativeLayout.ALIGN_LEFT, tv_date.getId());
        rlp.setMargins(0, 10, 0, 0);
        relativeLayout.addView(tv_time, rlp);

        //////===============================================

        ImageView iv_select = new ImageView(view.getContext());
        iv_select.setId(View.generateViewId());
        iv_select.setTag("iv_select");
        iv_select.setImageResource(R.drawable.selected2);
        iv_select.setVisibility(View.GONE);

        rlp = new RelativeLayout.LayoutParams(50, 50);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        rlp.setMargins(0, 0, 0, -5);
        relativeLayout.addView(iv_select, rlp);

        cards.add(relativeLayout);
    }

    private void resetViewsSelectedState() {
        for (RelativeLayout relativeLayout : cards) {
            ImageView iv = relativeLayout.findViewWithTag("iv_select");
            iv.setVisibility(View.GONE);
        }
    }

    private void createCards() {
        for (int i = 0; i < 24; ++i) {
            createCard("2019-11-05", "11:12:15");
        }
    }

    private void addCards() {
        for (int i = 0; i < cards.size(); ++i) {
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            //params.columnSpec = GridLayout.spec(i % 2, 1.0f);
            params.width = 220;
            params.height = 100;
            params.setMargins(20, 0, 0, 20);
            gl_lslx_card.addView(cards.get(i), params);
        }
    }

}
