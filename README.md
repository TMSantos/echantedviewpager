# echantedviewpager
EchantedViewPager is a custom ViewPager that will you give some custom functionalities, such as, scalling scroll, alpha scroll and swipe to dismiss scroll.
[![IMAGE ALT TEXT](http://img.youtube.com/vi/Ca7G2DqXSsc/0.jpg)](http://www.youtube.com/watch?v=Ca7G2DqXSsc "Echanted view pager")

#Scale scroll
```
 final EnchantedViewPager mViewPager = (EnchantedViewPager) findViewById(R.id.viewpager);
 mViewPager.useScale();
```
#Alpha scroll
```
 final EnchantedViewPager mViewPager = (EnchantedViewPager) findViewById(R.id.viewpager);
 mViewPager.useAlpha();
```
#Swipe to dismiss
```
 final EnchantedViewPager mViewPager = (EnchantedViewPager) findViewById(R.id.viewpager);
 mViewPager.addSwipeToDismiss(new EnchantedViewPager.EnchantedViewPagerSwipeListener() {
                        @Override
                        public void onSwipeFinished(int position) {
                            //execute code to remove the swiped item
                        }
                    });
```