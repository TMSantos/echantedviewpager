# echantedviewpager
EchantedViewPager is a custom ViewPager that will you give some custom functionalities, such as, scalling scroll, alpha scroll and swipe to dismiss scroll.

***
<a href="http://www.youtube.com/watch?feature=player_embedded&v=Ca7G2DqXSsc
" target="_blank"><img src="http://img.youtube.com/vi/Ca7G2DqXSsc/0.jpg" 
alt="IMAGE ALT TEXT HERE" width="240" height="180" border="10" /></a>

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