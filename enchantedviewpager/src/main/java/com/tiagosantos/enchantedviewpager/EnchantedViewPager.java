package com.tiagosantos.enchantedviewpager;

import android.animation.Animator;
import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by tiago on 8/15/16.
 */
public class EnchantedViewPager extends ViewPager {

    public static final String ENCHANTED_VIEWPAGER_POSITION = "ENCHANTED_VIEWPAGER_POSITION";

    private float mSwipeThreshold; //to avoid single touchs

    private boolean mUseAlpha;
    private boolean mUseScale;
    private boolean mUseSwipe;

    //variables to help swipe movements interpretation
    private float lastYactionDown = 0;

    private float originalDragXposition;
    private float originalDragYposition;

    private boolean dragStarted;

    private EnchantedViewPagerSwipeListener swipeListener;

    public EnchantedViewPager(Context context) {
        super(context);

        init();
    }

    public EnchantedViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    private void init() {
        useAlpha();
        useScale();

        addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (positionOffset >= 0f && positionOffset <= 1f) {
                    View curPage = findViewWithTag(ENCHANTED_VIEWPAGER_POSITION + position);

                    if (curPage != null) {
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

        final int position = getCurrentItem();
        final View currPage = findViewWithTag(ENCHANTED_VIEWPAGER_POSITION + position);
        if (currPage == null) {
            return super.onTouchEvent(event);
        }

        mSwipeThreshold = (currPage.getHeight() / 4);

        switch (action) {
            case (MotionEvent.ACTION_DOWN):
                originalDragXposition = currPage.getX();
                originalDragYposition = currPage.getY();
                lastYactionDown = event.getY();
                return super.onTouchEvent(event);
            case (MotionEvent.ACTION_MOVE):
                if (!dragStarted && checkSwipe(event.getY())) {
                    dragStarted = true;
                }

                if (dragStarted) {
                    onDrag(event.getY(), currPage);
                    return true;
                } else {
                    return super.onTouchEvent(event);
                }
            case (MotionEvent.ACTION_UP):
                dragStarted = false;
                boolean dismissed = checkDismiss(event.getY(), currPage);

                if (!dismissed) {
                    currPage.setX(originalDragXposition);
                    currPage.setY(originalDragYposition);
                }
                return super.onTouchEvent(event);
            default:
                return super.onTouchEvent(event);
        }
    }

    private boolean checkDismiss(float y, View view) {
        float viewDismissThreshold = view.getHeight() / 2;
        if (originalDragYposition < y) {
            if ((y - lastYactionDown) > viewDismissThreshold) {
                onSwipe(SWIPE_DIRECTION.SWIPE_DOWN, view);
                return true;
            }
        } else {
            if ((lastYactionDown - y) > viewDismissThreshold) {
                onSwipe(SWIPE_DIRECTION.SWIPE_UP, view);
                return true;
            }
        }


        return false;
    }

    private boolean checkSwipe(float eventY) {
        if (lastYactionDown < eventY) { //swipe down
            //check if the user swiped long enough
            if ((eventY - lastYactionDown) > mSwipeThreshold) {
                return true;
            }

        } else { // swipe up
            //check if the user swiped long enough
            if ((lastYactionDown - eventY) > mSwipeThreshold) {
                return true;
            }
        }

        return false;
    }

    private void onDrag(float y, View view) {
        view.setX(originalDragXposition);
        view.setY(y - (view.getHeight() / 2));
    }

    private void onSwipe(SWIPE_DIRECTION direction, View view) {
        float translationValue = 0;

        switch (direction) {
            case SWIPE_UP:
                translationValue = -view.getHeight();
                break;
            case SWIPE_DOWN:
                translationValue = view.getHeight();
                break;
        }

        view.animate()
                .translationY(translationValue)
                .alpha(0.0f).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                swipeListener.onSwipeFinished(getCurrentItem());
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    public void addSwipeToDismiss(EnchantedViewPagerSwipeListener listener) {
        this.swipeListener = listener;
        mUseSwipe = true;
    }

    public void useScale() {
        int padding_in_px = getResources().getDimensionPixelSize(R.dimen.enchanted_view_pager_margin);
        setPadding(padding_in_px, 0, padding_in_px, 0);
        setClipToPadding(false);
        mUseScale = true;

    }

    public void useAlpha() {
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
        setPadding(0, 0, 0, 0);
    }

    public interface EnchantedViewPagerSwipeListener {
        void onSwipeFinished(int position);
    }

    private enum SWIPE_DIRECTION {
        SWIPE_UP, SWIPE_DOWN
    }
}
