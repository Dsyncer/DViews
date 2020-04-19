# DViews
This repository contains custom views for android and theirs examples. You are free to use any of these views in your projects and 
you can even send them to me and I'll publish them here

Currently it contains next views:
* TopMenuView

## TopMenuView

This is an intuitive and easy-to-use element for navigating the application. It can contain an unlimited number of items and can be customized for your purposes.

###### How to use in your project

Go to packages and download latest .aar file and import it as a library in android studio. Or install it with maven by adding
next lines into your pom.xml
```xml
<dependency>
  <groupId>com.dst.dtools.views</groupId>
  <artifactId>dviews</artifactId>
  <version>1.02</version>
</dependency>
```
And then run via command line
`$ mvn install`

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

## Feedback
If you find any bug please create an issue or contact me via email den4ikindota2@gmail.com. If you have any suggestions also feel free to contact me. If you found that project usefull please give it a star. If you used it in your app please send me link to your app and I'll post it here. 
