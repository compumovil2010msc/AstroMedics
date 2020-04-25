package com.example.astromedics.helpers;

import androidx.annotation.NonNull;

public class GenericListViewItem {
    private String label;
    private GenericOnClickListener onClickListener;

    public GenericListViewItem(String label) {
        this.label = label;
    }

    public void setOnClickListener(GenericOnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void onClickPressed() {
        if (onClickListener != null) {
            onClickListener.onClickPressed();
        }
    }

    @NonNull
    @Override
    public String toString() {
        return label;
    }

    public interface GenericOnClickListener {
        void onClickPressed();
    }
}
