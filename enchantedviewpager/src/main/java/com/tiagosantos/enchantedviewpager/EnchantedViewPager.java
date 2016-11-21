package com.tiagosantos.enchantedviewpager;

import android.animation.Animator;
import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by tiago on 8/15/16.
 */
public class EnchantedViewPager extends ViewPager {

    public static final String ENCHANTED_VIEWPAGER_POSITION = "ENCHANTED_VIEWPAGER_POSITION";

    //Echanted viewpager configurations values
    public static final int CONFIG_SCALE_SCROLL = 0;
    public static final int CONFIG_ALPHA_SCROLL = 1;

    private float mSwipeThreshold; //to avoid single touchs

    private ArrayList<Integer> mConfigurationList;

    private boolean mUseAlpha;
    private boolean mUseScale;
    private boolean mUseSwipe;

    //variables to help swipe movements interpretation
    private float lastYactionDown = 0;

    private EnchantedViewPagerSwipeListener swipeListener;

    public EnchantedViewPager(Context context) {
        super(context);

        mConfigurationList = new ArrayList<>();
    }

    public EnchantedViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);

        mConfigurationList = new ArrayList<>();
    }

    public void init() {
        for (Integer configuration : mConfigurationList) {
            switch (configuration) {
                case CONFIG_SCALE_SCROLL:
                    initScaleScrollConfig();
                    break;
                case CONFIG_ALPHA_SCROLL:
                    initAlphaConfig();
                    break;
                default:
                    break;
            }
        }

        addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (positionOffset >= 0f && positionOffset <= 1f) {
                    View curPage = findViewWithTag(ENCHANTED_VIEWPAGER_POSITION + position);

                    if (curPage != null) {
                        mSwipeThreshold = (curPage.getHeight() / 2.5f);
                        if (mUseScale) {
                            curPage.setScaleY(EnchantedPagerConstants.BIG_SCALE - (EnchantedPagerConstants.DIFF_SCALE * positionOffset));
                            curPage.setScaleX(EnchantedPagerConstants.BIG_SCALE - (EnchantedPagerConstants.DIFF_SCALE * positionOffset));
                        }

                        if (mUseAlpha) {
                            curPage.setAlpha(1.0f - (0.5f * positionOffset));
                        }

                    }

                    View nextPage = findViewWithTag(ENCHANTED_VIEWPAGER_POSITION + (position + 1));
                    if (nextPage != null) {
                        if (mUseScale) {
                            nextPage.setScaleY(EnchantedPagerConstants.SMALL_SCALE + (EnchantedPagerConstants.DIFF_SCALE * positionOffset));
                            nextPage.setScaleX(EnchantedPagerConstants.SMALL_SCALE + (EnchantedPagerConstants.DIFF_SCALE * positionOffset));
                        }

                        if (mUseAlpha) {
                            nextPage.setAlpha(0.5f + (0.5f * positionOffset));
                        }
                    }

                    View previousPage = findViewWithTag(ENCHANTED_VIEWPAGER_POSITION + (position - 1));
                    if (previousPage != null) {
                        if (mUseScale) {
                            previousPage.setScaleY(EnchantedPagerConstants.SMALL_SCALE + (EnchantedPagerConstants.DIFF_SCALE * positionOffset));
                            previousPage.setScaleX(EnchantedPagerConstants.SMALL_SCALE + (EnchantedPagerConstants.DIFF_SCALE * positionOffset));
                        }

                        if (mUseAlpha) {
                            previousPage.setAlpha(0.5f + (0.5f * positionOffset));
                        }
                    }

                    View nextAfterPage = findViewWithTag(ENCHANTED_VIEWPAGER_POSITION + (position + 2));
                    if (nextAfterPage != null) {
                        if (mUseScale) {
                            nextAfterPage.setScaleX(EnchantedPagerConstants.SMALL_SCALE);
                            nextAfterPage.setScaleY(EnchantedPagerConstants.SMALL_SCALE);
                        }

                        if (mUseAlpha) {
                            nextAfterPage.setAlpha(0.5f);
                        }
                    }
                }
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    //TouchEvent used for swipe actions
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!mUseSwipe) return super.onTouchEvent(event);

        int action = MotionEventCompat.getActionMasked(event);

        switch (action) {
            case (MotionEvent.ACTION_DOWN):
                lastYactionDown = event.getY();
                return true;
            case (MotionEvent.ACTION_UP):
                if (lastYactionDown < event.getY()) { //swipe down
                    //check if the user swiped long enough
                    if ((event.getY() - lastYactionDown) > mSwipeThreshold) {
                        onSwipe(SWIPE_DIRECTION.SWIPE_DOWN);
                    }

                } else { // swipe up
                    //check if the user swiped long enough
                    if ((lastYactionDown - event.getY()) > mSwipeThreshold) {
                        onSwipe(SWIPE_DIRECTION.SWIPE_UP);
                    }

                }
                return super.onTouchEvent(event);
            default:
                return super.onTouchEvent(event);
        }
    }

    private void onSwipe(SWIPE_DIRECTION direction) {
        final int position = getCurrentItem();
        final View currPage = findViewWithTag(ENCHANTED_VIEWPAGER_POSITION + position);
        float translationValue = 0;

        switch (direction) {
            case SWIPE_UP:
                translationValue = -currPage.getHeight();
                break;
            case SWIPE_DOWN:
                translationValue = currPage.getHeight();
                break;
        }

        currPage.animate()
                .translationY(translationValue)
                .alpha(0.0f).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                swipeListener.onSwipeFinished(position);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    public void addConfiguration(int configuration) {
        mConfigurationList.add(configuration);
    }

    public void addSwipeToDismiss(EnchantedViewPagerSwipeListener listener) {
        this.swipeListener = listener;
        mUseSwipe = true;
    }

    private void initScaleScrollConfig() {
        int padding_in_px = getResources().getDimensionPixelSize(R.dimen.enchanted_view_pager_margin);
        setPadding(padding_in_px, 0, padding_in_px, 0);
        setClipToPadding(false);
        mUseScale = true;

    }

    private void initAlphaConfig() {
        mUseAlpha = true;
    }

    public void removeSwipe() {
        mUseSwipe = false;
    }

    public void removeAlpha() {
        mUseAlpha = false;
    }

    public void removeScale() {
        mUseScale = false;
    }

    public interface EnchantedViewPagerSwipeListener {
        void onSwipeFinished(int position);
    }

    private enum SWIPE_DIRECTION {
        SWIPE_UP, SWIPE_DOWN
    }
}
