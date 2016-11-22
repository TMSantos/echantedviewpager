# echantedviewpager
EchantedViewPager is a custom ViewPager that provides some custom and usefull functionalities, such as, scalling scroll, alpha scroll and swipe to dismiss option.

#Video
<a href="http://www.youtube.com/watch?feature=player_embedded&v=Ca7G2DqXSsc
" target="_blank"><img src="http://img.youtube.com/vi/Ca7G2DqXSsc/0.jpg" 
alt="IMAGE ALT TEXT HERE" width="240" height="180" border="10" /></a>

#Usage
```Inside your pager adapter you must do this whenever you instantiate a view:
@Override
    public Object instantiateItem(ViewGroup container, int position) {
        (...)
        mCurrentView.setTag(EnchantedViewPager.ENCHANTED_VIEWPAGER_POSITION + position);
        container.addView(mCurrentView);

        return mCurrentView;
    }
```
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
#License
```
Copyright 2016 Tiago Santos

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
``