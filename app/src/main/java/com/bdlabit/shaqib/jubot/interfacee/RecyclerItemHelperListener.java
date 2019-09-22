package com.bdlabit.shaqib.jubot.interfacee;

import androidx.recyclerview.widget.RecyclerView;

public interface RecyclerItemHelperListener {
    void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position);
}
