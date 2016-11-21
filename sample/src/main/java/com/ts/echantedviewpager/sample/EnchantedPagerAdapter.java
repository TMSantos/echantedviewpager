package com.ts.echantedviewpager.sample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.tiagosantos.enchantedviewpager.EnchantedViewPager;

import java.util.ArrayList;

/**
 * Created by tiago on 8/13/16.
 */
public class EnchantedPagerAdapter extends PagerAdapter{

    Context mContext;

    LayoutInflater inflater;

    final ArrayList<AlbumArt> mAlbumlist;

    public EnchantedPagerAdapter(Context context,ArrayList<AlbumArt> albumList) {
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        mAlbumlist = albumList;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView mCurrentView = (ImageView) inflater.inflate(R.layout.item_view, container, false);

        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inScaled = false;

        AlbumArt album = this.mAlbumlist.get(position);
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), album.getImageResource(), opts);

        mCurrentView.setImageBitmap(bitmap);
        mCurrentView.setTag(EnchantedViewPager.ENCHANTED_VIEWPAGER_POSITION + position);
        container.addView(mCurrentView);

        return mCurrentView;
    }

    @Override
    public int getCount() {
        return mAlbumlist.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public void removePosition(int position) {
        mAlbumlist.remove(mAlbumlist.get(position));
        notifyDataSetChanged();
    }
}
