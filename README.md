# DViews
This repository contains custom views for android and theirs examples. You are free to use any of these views in your projects and 
you can even send them to me and I'll publish them here

Currently it contains next views:
* TopMenuView

## TopMenuView

This is an intuitive and easy-to-use element for navigating the application. It can contain an unlimited number of items and can be customized for your purposes.

###### How to use in your project

Add next lines into your build.gradle (:app):
```gradle
//It is necessary to add that maven repository
repositories {
    maven {
        name = "GitHubPackages"
        url = uri("https://maven.pkg.github.com/dsyncer/dviews")
        credentials {
            username = "Dsyncer"
            password = "06fb0b533f2786935f4a6df3f118cefac783d9af"
        }
    }
}

dependencies {
     implementation 'com.dst.dtools.views:dviews:1.0'
     //other dependencies
}
```

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
