# DViews
This repository contains custom views for android and theirs examples. You are free to use any of these views in your projects and 
you can even send them to me and I'll publish them here

Currently it contains next views:
* TopMenuView

## TopMenuView

This is an intuitive and easy-to-use element for navigating the application. It can contain an unlimited number of items and can be customized for your purposes.

###### How to use in your project

1.Copy TopMenuView.java file from "DViews/app/src/main/java/com/dst/dtools/views/tmv/"

2.Copy view_top_menu.xml from "DViews/app/src/main/res/layout/"

3.Copy attrs.xml from "DViews/app/src/main/res/values/"

###### Example of usage
```java
TopMenuView topMenuView = findViewById(R.id.myTopMenu);
topMenuView.setMenuItems(new String[]{"Home", "Toast Data", "GitHub", "Settings"});
topMenuView.setOnTopMenuItemClickListener(new TopMenuView.OnTopMenuItemClickListener() {
    @Override
    public void topMenuItemClick(final int pos, TextView textView) {
       //Your code here
    }
});
```
###### View screenshot

![TopMenuView Screenshot](/TopMenuViewScreenShot.jpg)
