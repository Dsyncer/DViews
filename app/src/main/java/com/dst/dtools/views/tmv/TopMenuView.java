/*
Copyright 2020 Denis Yatsenko

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */


package com.dst.dtools.views.tmv;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.dst.dtools.views.R;

/**
 * TopMenuView
 * @author Denis Yatsenko
 * @version 1.0
 */

public class TopMenuView extends LinearLayout {

    private String[] menuItems = {"Item1", "Item2", "Item3"};
    private TextView[] menuItemsTVs;
    private OnTopMenuItemClickListener topMenuItemClickListener;

    private static int curSelectedItem;
    private TextView selectedTextView;

    private int selectedItemColor, backgroundColor, itemColor;
    private int itemPadding, layoutPadding;
    private boolean centerOnSelectedItem;

    public interface OnTopMenuItemClickListener {
        /**
         * function that will be called every time one of
         * the items were pressed (if interface has been set)
         * @param pos position of the item (counts from 0) and corresponds to its position in menuItems
         * @param textView TextView which was pressed
         */
        void topMenuItemClick(int pos, TextView textView);
    }

    public void setOnTopMenuItemClickListener(OnTopMenuItemClickListener topMenuItemClickListener) {
        this.topMenuItemClickListener = topMenuItemClickListener;
    }

    public void setMenuItems(String[] menuItems) {
        if (curSelectedItem >= menuItems.length)
            curSelectedItem = 0;
        this.menuItems = menuItems;
        invalidate();
        requestLayout();
        init();
    }

    public TopMenuView(Context context) {
        super(context);
        init();
    }

    public TopMenuView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        getAttributes(context, attrs, 0);
        init();
    }

    public TopMenuView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttributes(context, attrs, defStyleAttr);
        init();
    }

    private void getAttributes(Context context, AttributeSet attributeSet, int defStyleAttr){
        TypedArray a = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.TopMenuView, defStyleAttr, 0);

        try {
            backgroundColor = a.getColor(R.styleable.TopMenuView_backgroundColor, Color.parseColor("#728844"));
            itemColor = a.getColor(R.styleable.TopMenuView_itemColor, Color.parseColor("#ffffff"));
            selectedItemColor = a.getInteger(R.styleable.TopMenuView_selectedItemColor, Color.parseColor("#000000"));
            itemPadding = a.getDimensionPixelSize(R.styleable.TopMenuView_itemPadding, 30);
            layoutPadding = a.getDimensionPixelSize(R.styleable.TopMenuView_layoutPadding, 30);
            centerOnSelectedItem = a.getBoolean(R.styleable.TopMenuView_centerOnSelectedItem, true);
        } finally {
            a.recycle();
        }
    }

    private void init(){
        removeAllViews();
        this.topMenuItemClickListener = null;

        if(isInEditMode())
            menuItems = new String[]{"Home", "Data", "Share"};

        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View topMenuLayout = inflater.inflate(R.layout.view_top_menu, null);
        topMenuLayout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        final LinearLayout mainLinearLayout = topMenuLayout.findViewById(R.id.v_tm_layout);
        mainLinearLayout.setWeightSum(menuItems.length);
        mainLinearLayout.setBackgroundColor(backgroundColor);
        mainLinearLayout.setPadding(layoutPadding, layoutPadding, layoutPadding, layoutPadding);
        final HorizontalScrollView horizontalScrollView = topMenuLayout.findViewById(R.id.v_tm_scroll);
        menuItemsTVs = new TextView[menuItems.length];
        for(int i = 0; i < menuItems.length; i++){
            final TextView textView = createTextView(menuItems[i]);
            if(i == curSelectedItem) {
                textView.setTextColor(selectedItemColor);
                selectedTextView = textView;
            }
            final int finalI = i;
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (topMenuItemClickListener != null) {
                        topMenuItemClickListener.topMenuItemClick(finalI, textView);
                        if (centerOnSelectedItem) {
                            selectedTextView = textView;
                            horizontalScrollView.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (selectedTextView != null)
                                        horizontalScrollView.smoothScrollTo((int) (selectedTextView.getX() + selectedTextView.getWidth() / 2 - horizontalScrollView.getWidth() / 2), 0);
                                }
                            });
                        }
                    }
                }
            });
            menuItemsTVs[i] = textView;
            mainLinearLayout.addView(textView);
        }
        addView(topMenuLayout);

        if (centerOnSelectedItem) {
            horizontalScrollView.post(new Runnable() {
                @Override
                public void run() {
                    if (selectedTextView != null)
                        horizontalScrollView.smoothScrollTo((int) (selectedTextView.getX() + selectedTextView.getWidth() / 2 - horizontalScrollView.getWidth() / 2), 0);
                }
            });
        }
    }

    public TextView getSelectedTextView(){
        return selectedTextView;
    }

    public int getCurSelectedItem(){
        return curSelectedItem;
    }

    public void setCurSelectedItem(int selectedItem) {
        menuItemsTVs[curSelectedItem].setTextColor(itemColor);
        curSelectedItem = selectedItem;
        menuItemsTVs[curSelectedItem].setTextColor(selectedItemColor);
    }

    private TextView createTextView(String text){
        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1);
        layoutParams.setMargins(itemPadding, 0, itemPadding, 0);
        TextView textView = new TextView(getContext());
        textView.setLayoutParams(layoutParams);
        textView.setTextColor(itemColor);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            textView.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        }
        else
            textView.setGravity(Gravity.CENTER);

        textView.setText(text);

        return textView;
    }
}
