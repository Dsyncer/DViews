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
import android.view.ViewGroup;
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

/**
 * Intuitive and easy-to-use element for navigating the application
 */
public class TopMenuView extends LinearLayout {

    private String[] menuItems = {"Item1", "Item2", "Item3"};
    private TextView[] menuItemsTVs;
    private OnTopMenuItemClickListener topMenuItemClickListener;

    private static int curSelectedItem;
    private LinearLayout selectedItem;

    private int selectedItemColor, backgroundColor, itemColor, underlineColor;
    private int itemPadding, layoutPadding, underlineHeight;
    private boolean centerOnSelectedItem, underline;

    private LinearLayout mainLinearLayout;

    /**
     * Interface for setting callback that will be called every time
     * one of the items were pressed (if interface has been set)
     */
    public interface OnTopMenuItemClickListener {
        /**
         * function that will be called every time one of
         * the items were pressed (if interface has been set)
         * @param pos position of the item (counts from 0) and corresponds to its position in variable menuItems
         * @param textView TextView which was pressed
         */
        void topMenuItemClick(int pos, TextView textView);
    }

    public void setOnTopMenuItemClickListener(OnTopMenuItemClickListener topMenuItemClickListener) {
        this.topMenuItemClickListener = topMenuItemClickListener;
    }

    /**
     * Function for setting item for TopMenuView
     * It will redraw the view and reset curSelectedItem if it is more
     * them menuItems.length
     * @param menuItems String array of items that needs to be set
     */
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
            underlineColor = a.getInteger(R.styleable.TopMenuView_underlineColor, selectedItemColor);
            underline = a.getBoolean(R.styleable.TopMenuView_underline, true);
            underlineHeight = a.getDimensionPixelSize(R.styleable.TopMenuView_underlineHeight, 8);
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

        mainLinearLayout = topMenuLayout.findViewById(R.id.v_tm_layout);
        mainLinearLayout.setWeightSum(menuItems.length);
        mainLinearLayout.setBackgroundColor(backgroundColor);
        mainLinearLayout.setPadding(layoutPadding, 0, layoutPadding, 0);
        final HorizontalScrollView horizontalScrollView = topMenuLayout.findViewById(R.id.v_tm_scroll);
        menuItemsTVs = new TextView[menuItems.length];
        for(int i = 0; i < menuItems.length; i++){
            final LinearLayout linearLayout = new LinearLayout(getContext());
            LayoutParams layoutParamsLinear = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1);
            linearLayout.setLayoutParams(layoutParamsLinear);
            linearLayout.setOrientation(VERTICAL);
            linearLayout.setPadding(0, layoutPadding, 0, layoutPadding);

            final TextView textView = createTextView(menuItems[i]);
            linearLayout.addView(textView);

            if(i == curSelectedItem) {
                textView.setTextColor(selectedItemColor);
                selectedItem = linearLayout;

                if (underline) {
                    linearLayout.addView(createUnderlineView());
                    linearLayout.setPadding(0, layoutPadding, 0, 0);
                }
            }
            final int finalI = i;
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (topMenuItemClickListener != null) {
                        topMenuItemClickListener.topMenuItemClick(finalI, textView);
                        if (centerOnSelectedItem) {
                            selectedItem = linearLayout;
                            horizontalScrollView.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (selectedItem != null)
                                        horizontalScrollView.smoothScrollTo((int) (selectedItem.getX() + selectedItem.getWidth() / 2 - horizontalScrollView.getWidth() / 2), 0);
                                }
                            });
                        }
                    }
                }
            });
            menuItemsTVs[i] = textView;
            mainLinearLayout.addView(linearLayout);
        }
        addView(topMenuLayout);

        if (centerOnSelectedItem) {
            horizontalScrollView.post(new Runnable() {
                @Override
                public void run() {
                    if (selectedItem != null)
                        horizontalScrollView.smoothScrollTo((int) (selectedItem.getX() + selectedItem.getWidth() / 2 - horizontalScrollView.getWidth() / 2), 0);
                }
            });
        }
    }

    private View createUnderlineView(){
        View view = new View(getContext());
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, underlineHeight);
        layoutParams.setMargins(0, layoutPadding - underlineHeight, 0, 0);
        layoutParams.gravity = Gravity.CENTER;
        view.setLayoutParams(layoutParams);
        view.setBackgroundColor(underlineColor);

        return view;
    }

    public LinearLayout getSelectedTextView(){
        return selectedItem;
    }

    public int getCurSelectedItem(){
        return curSelectedItem;
    }

    /**
     * function to set static variable curSelectedItem and highlight TextView that
     * located on selectedItem position. It will automatically remove highlight from previously
     * selected TextView
     * @param selectedItem position of selected item that corresponds to its position inside variable menuItems
     */
    public void setCurSelectedItem(int selectedItem) {
        menuItemsTVs[curSelectedItem].setTextColor(itemColor);
        if (underline) {
            ((LinearLayout) mainLinearLayout.getChildAt(curSelectedItem)).removeViewAt(1);
            mainLinearLayout.getChildAt(curSelectedItem).setPadding(0, layoutPadding, 0, layoutPadding);
        }

        curSelectedItem = selectedItem;

        menuItemsTVs[curSelectedItem].setTextColor(selectedItemColor);
        if (underline) {
            ((LinearLayout) mainLinearLayout.getChildAt(curSelectedItem)).addView(createUnderlineView());
            mainLinearLayout.getChildAt(curSelectedItem).setPadding(0, layoutPadding, 0, 0);
        }
    }

    private TextView createTextView(String text){
        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1);
        layoutParams.setMargins(itemPadding, 0, itemPadding, 0);
        layoutParams.gravity = Gravity.CENTER;
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
