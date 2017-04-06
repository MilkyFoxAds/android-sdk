/*
 * **********************
 * Copyright (c) 2017.
 * MilkyFox
 * http://milkyfox.com
 * **********************
 */

package com.milkyfox.app.recycler;

public class MenuElement {
    public String mTitle;
    public Class mActivityClass;

    public MenuElement(String title, Class activityClass) {
        this.mTitle = title;
        this.mActivityClass = activityClass;
    }
}
