/*
 * **********************
 * Copyright (c) 2017.
 * MilkyFox
 * http://milkyfox.com
 * **********************
 */

package com.milkyfox.app.recycler;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.milkyfox.app.R;

public class MenuElementViewHolder extends RecyclerView.ViewHolder {
    TextView mTitle;

    public MenuElementViewHolder(View view) {
        super(view);
        mTitle = (TextView) view.findViewById(R.id.title);

    }

    public void setMenuItem(final MenuElement menuElement) {
        mTitle.setText(menuElement.mTitle);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(itemView.getContext(), menuElement.mActivityClass);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                itemView.getContext().startActivity(intent);
            }
        });
    }

}
