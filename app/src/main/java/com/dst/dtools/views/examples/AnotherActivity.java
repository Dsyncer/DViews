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


package com.dst.dtools.views.examples;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.dst.dtools.views.tmv.TopMenuView;

public class AnotherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another);

        //We declare our TopMenuView and set items for it
        final TopMenuView topMenuView = findViewById(R.id.myTopMenu);
        //Because variable curSelectedItem, which we set earlier, is static "Settings" will be highlighted
        topMenuView.setMenuItems(new String[]{"Home", "Toast Data", "GitHub", "Settings"});
        //We set an action which will happen when one of the items have been pressed
        topMenuView.setOnTopMenuItemClickListener(new TopMenuView.OnTopMenuItemClickListener() {
            @Override
            public void topMenuItemClick(int pos, TextView textView) {
                switch (pos){
                    case 0:
                        //Set this item as current selected item for TopMenuView to highlight it
                        topMenuView.setCurSelectedItem(pos);
                        startActivity(new Intent(AnotherActivity.this, MainActivity.class));
                        break;
                    case 1:
                        //Set this item as current selected item for TopMenuView to highlight it
                        topMenuView.setCurSelectedItem(pos);
                        Toast.makeText(AnotherActivity.this, "Toast With Some Data", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        //Set this item as current selected item for TopMenuView to highlight it
                        topMenuView.setCurSelectedItem(pos);
                        AlertDialog.Builder builder = new AlertDialog.Builder(AnotherActivity.this);
                        builder.setTitle("You can get this project on github")
                                .setMessage("If you put a star on github, I will be very grateful to you")
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                })
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Dsyncer")));
                                    }
                                }).show();
                        break;
                    case 3:
                        //Set this item as current selected item for TopMenuView to highlight it
                        topMenuView.setCurSelectedItem(pos);
                        startActivity(new Intent(AnotherActivity.this, AnotherActivity.class));
                        break;
                }
            }
        });
    }
}
