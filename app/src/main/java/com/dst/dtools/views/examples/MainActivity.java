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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.dst.dtools.views.R;
import com.dst.dtools.views.tmv.TopMenuView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    /**
        This is a simple example of TopMenuView usage
        @author Denis Yatsenko
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView myTextView = findViewById(R.id.textView);
        Button button = findViewById(R.id.button);

        //We declare our TopMenuView and set items for it
        final TopMenuView topMenuView = findViewById(R.id.myTopMenu);
        topMenuView.setMenuItems(new String[]{"Home", "Toast Data", "GitHub", "Settings"});
        //We set an action which will happen when one of the items have been pressed
        topMenuView.setOnTopMenuItemClickListener(new TopMenuView.OnTopMenuItemClickListener() {
            @Override
            public void topMenuItemClick(final int pos, TextView textView) {
                switch (pos){
                    //If item position is 0 (Home) display a PopupMenu with anchor on textView
                    case 0:
                        PopupMenu popupMenu = new PopupMenu(MainActivity.this, textView);
                        popupMenu.getMenu().add(R.string.myProfie);
                        popupMenu.getMenu().add(R.string.myFeed);
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem menuItem) {
                                if (menuItem.getTitle().toString().equals(getString(R.string.myProfie))){
                                    //Set this item as current selected item for TopMenuView to highlight it
                                    topMenuView.setCurSelectedItem(pos);
                                    myTextView.setText(R.string.myProfie);
                                    return true;
                                }
                                if (menuItem.getTitle().toString().equals(getString(R.string.myFeed))){
                                    //Set this item as current selected item for TopMenuView to highlight it
                                    topMenuView.setCurSelectedItem(pos);
                                    myTextView.setText(R.string.myFeed);
                                    return true;
                                }
                                return false;
                            }
                        });
                        popupMenu.show();
                        break;
                    //If item position is 1 (Toast Data) make a toast with data
                    case 1:
                        //Set this item as current selected item for TopMenuView to highlight it
                        topMenuView.setCurSelectedItem(pos);
                        Toast.makeText(MainActivity.this, "Toast With Some Data", Toast.LENGTH_SHORT).show();
                        break;
                    //If item position is 2 (GitHub) show dialog
                    case 2:
                        //Set this item as current selected item for TopMenuView to highlight it
                        topMenuView.setCurSelectedItem(pos);
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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
                    //If item position is 3 (Settings) go to SettingsActivity
                    case 3:
                        //Set this item as current selected item for TopMenuView to highlight it
                        topMenuView.setCurSelectedItem(pos);
                        startActivity(new Intent(MainActivity.this, AnotherActivity.class));
                        break;
                }
            }
        });

        //Populate TopMenuView with random amounts of items when button was clicked
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random random = new Random();
                int randNumber = random.nextInt(10) + 1;
                String[] newMenuItems = new String[randNumber];
                for (int i = 0; i < randNumber; i++){
                    newMenuItems[i] = "Item " + i;
                }

                //We can change TopMenuView items at runtime
                topMenuView.setMenuItems(newMenuItems);
                topMenuView.setOnTopMenuItemClickListener(new TopMenuView.OnTopMenuItemClickListener() {
                    @Override
                    public void topMenuItemClick(int pos, TextView textView) {
                        //Set this item as current selected item for TopMenuView to highlight it
                        topMenuView.setCurSelectedItem(pos);
                        Toast.makeText(MainActivity.this, "Item " + pos + " was pressed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}
