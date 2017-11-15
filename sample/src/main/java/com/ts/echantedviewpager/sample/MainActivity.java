package com.ts.echantedviewpager.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tiagosantos.enchantedviewpager.EnchantedViewPager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    LinearLayout mAlphaWrapper;
    LinearLayout mScaleWrapper;
    LinearLayout mSwipeWrapper;
    LinearLayout mCarrouselWrapper;

    SwitchCompat mUseAlpha;
    SwitchCompat mUseScale;
    SwitchCompat mUseSwipeToDismiss;
    SwitchCompat mUseCarrousel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createSwitches();

        final EnchantedViewPager mViewPager = (EnchantedViewPager) findViewById(R.id.homepage_card_view_pager);

        final EnchantedPagerAdapter adapter = new EnchantedPagerAdapter(this, createAlbumList());
        mViewPager.setAdapter(adapter);

        mUseAlpha.setChecked(true);
        mUseScale.setChecked(true);

        mUseAlpha.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (checked) {
                    mViewPager.useAlpha();
                    adapter.notifyDataSetChanged();
                } else {
                    mViewPager.removeAlpha();
                    adapter.notifyDataSetChanged();
                }
            }
        });

        mUseScale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (checked) {
                    mViewPager.useScale();
                    adapter.notifyDataSetChanged();

                } else {
                    mViewPager.removeScale();
                    adapter.notifyDataSetChanged();
                }
            }
        });

        mUseSwipeToDismiss.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (checked) {
                    mViewPager.addSwipeToDismiss(new EnchantedViewPager.EnchantedViewPagerSwipeListener() {
                        @Override
                        public void onSwipeFinished(int position) {
                            adapter.removeItem(position);
                        }
                    });
                } else {
                    mViewPager.addSwipeToDismiss(null);
                    mViewPager.removeSwipe();
                }
            }
        });

        mUseCarrousel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if(checked){
                    adapter.enableCarrousel();
                    mViewPager.setCurrentItem(adapter.getMiddlePosition());
                }else{
                    adapter.disableCarrousel();
                }
            }
        });
    }

    private void createSwitches() {
        mScaleWrapper = (LinearLayout) findViewById(R.id.scale_switch_wrapper);
        mAlphaWrapper = (LinearLayout) findViewById(R.id.alpha_switch_wrapper);
        mSwipeWrapper = (LinearLayout) findViewById(R.id.swipe_to_dismiss_switch_wrapper);
        mCarrouselWrapper = (LinearLayout) findViewById(R.id.arroussel_switch_wrapper);

        ((TextView) mScaleWrapper.findViewById(R.id.switch_text)).setText("Use scale");
        ((TextView) mAlphaWrapper.findViewById(R.id.switch_text)).setText("Use alpha");
        ((TextView) mSwipeWrapper.findViewById(R.id.switch_text)).setText("Use swipe");
        ((TextView) mCarrouselWrapper.findViewById(R.id.switch_text)).setText("Use carrousell mode");

        mUseAlpha = (SwitchCompat) mAlphaWrapper.findViewById(R.id.switch_view);
        mUseScale = (SwitchCompat) mScaleWrapper.findViewById(R.id.switch_view);
        mUseSwipeToDismiss = (SwitchCompat) mSwipeWrapper.findViewById(R.id.switch_view);
        mUseCarrousel = (SwitchCompat) mCarrouselWrapper.findViewById(R.id.switch_view);
    }

    private ArrayList<AlbumArt> createAlbumList() {
        ArrayList<AlbumArt> albumList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            albumList.add(new AlbumArt(getAlbumArtReference(i)));
        }
        return albumList;
    }

    private int getAlbumArtReference(int position) {
        if (position == 0) {
            return R.drawable.album_art_1;
        } else if (position == 1) {
            return R.drawable.album_art_2;
        } else if (position == 2) {
            return R.drawable.album_art_3;
        } else if (position == 3) {
            return R.drawable.album_art_4;
        } else if (position == 4) {
            return R.drawable.album_art_5;
        } else if (position == 5) {
            return R.drawable.album_art_6;
        } else if (position == 6) {
            return R.drawable.album_art_7;
        } else if (position == 7) {
            return R.drawable.album_art_8;
        } else if (position == 8) {
            return R.drawable.album_art_9;
        } else if (position == 9) {
            return R.drawable.album_art_10;
        }

        return R.drawable.album_art_1;
    }
}
