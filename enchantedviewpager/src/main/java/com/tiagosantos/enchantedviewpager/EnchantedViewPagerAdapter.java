package com.tiagosantos.enchantedviewpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tiago on 11/15/17.
 */

public class EnchantedViewPagerAdapter extends PagerAdapter {

    private List<?> list;
    private boolean enableCarrouselMode;

    public EnchantedViewPagerAdapter(List<?> list) {
        this.list = list;
        enableCarrouselMode = false;
    }

    @Override
    public int getCount() {
        if(enableCarrouselMode) {
            return list.size() * 500;
        }else{
            return list.size();
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }

    public void enableCarrousel(){
        enableCarrouselMode = true;
        notifyDataSetChanged();
    }

    public void disableCarrousel(){
        enableCarrouselMode = false;
        notifyDataSetChanged();
    }

    public int getMiddlePosition(){
        if (list.size() == 0) {
            return 0;
        } else {
            return (list.size() * 500) / 2;
        }
    }

    public void removeItem(int position) {
        int itemPosition;

        if(enableCarrouselMode) {
            itemPosition = position % list.size();
        }else{
            itemPosition = position;
        }

        list.remove(list.get(itemPosition));
        notifyDataSetChanged();
    }
}
