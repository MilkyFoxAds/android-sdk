/*
 * **********************
 * Copyright (c) 2017.
 * MilkyFox
 * http://milkyfox.com
 * **********************
 */

package com.milkyfox.app.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.milkyfox.app.R;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuElementViewHolder> {
    Context mContext;
    List<MenuElement> mMenuItemList;

    public MenuAdapter(Context context, List<MenuElement> menuItemList) {
        mContext = context;
        mMenuItemList = menuItemList;
    }

    @Override
    public MenuElementViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.menu_item, parent, false);
        return new MenuElementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MenuElementViewHolder holder, int position) {
        holder.setMenuItem(getItem(position));
    }

    public MenuElement getItem(int position) {
        return mMenuItemList.get(position);
    }

    @Override
    public int getItemCount() {
        return mMenuItemList.size();
    }
}
