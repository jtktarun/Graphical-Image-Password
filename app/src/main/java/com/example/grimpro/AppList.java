package com.example.grimpro;

import android.graphics.drawable.Drawable;

class AppList{
    private String name;
    private Drawable icon;
    private String packageName;
    private boolean isLocked = false;

    String getAppName() {
        return name;
    }

    String getPackageName() { return packageName; }

    Drawable getIcon() {
        return icon;
    }

    void setAppName(String appName) {
        this.name = appName;
    }

    void setIcon(Drawable icon) {
        this.icon = icon;
    }

    void setPackageName(String name){
        this.packageName = name;
    }

    void setLock(){isLocked = !isLocked;}

    boolean getLockStatus(){return isLocked;}
}
