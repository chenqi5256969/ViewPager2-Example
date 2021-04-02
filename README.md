# ViewPager2-Example 记录一些关于ViewPager2的基础和扩展使用

### 对Viewpager2中offscreenPageLimit和Fragment销毁回收的分析

#### 先看源码：

```java
 public void setOffscreenPageLimit(@OffscreenPageLimit int limit) {
        if (limit < 1 && limit != OFFSCREEN_PAGE_LIMIT_DEFAULT) {
            throw new IllegalArgumentException(
                    "Offscreen page limit must be OFFSCREEN_PAGE_LIMIT_DEFAULT or a number > 0");
        }
        mOffscreenPageLimit = limit;
        // Trigger layout so prefetch happens through getExtraLayoutSize()
        mRecyclerView.requestLayout();
    }
```

我们发现在Viewpager2中，limit最开始的值默认为-1.也就是说如果我们不设置setOffscreenPageLimit的值，vp2是默认不进行预加载的。

如果我们需要进行预加载，vp2是怎么实现这种机制的尼？

我们发现vp2中维系了一个RecyclerView，有RecyclerView就必然会有用来计算布局空间的LayoutManager，查找LayoutManager，果然在vp2中找打了LinearLayoutManagerImpl，那么就可以去看LayoutManager计算布局空间的方法了。

```java
Viewpager2.LinearLayoutManagerImpl    
				@Override
        protected void calculateExtraLayoutSpace(@NonNull RecyclerView.State state,
                @NonNull int[] extraLayoutSpace) {
            int pageLimit = getOffscreenPageLimit();
            if (pageLimit == OFFSCREEN_PAGE_LIMIT_DEFAULT) {
                // Only do custom prefetching of offscreen pages if requested
                super.calculateExtraLayoutSpace(state, extraLayoutSpace);
                return;
            }
            final int offscreenSpace = getPageSize() * pageLimit;
            extraLayoutSpace[0] = offscreenSpace;
            extraLayoutSpace[1] = offscreenSpace;
        }
```

如果我们设置了setOffscreenPageLimit，那么就会预先给出getPageSize() * pageLimit的空间，也就是预加载。比如pageLimit=1，

也就是会预先加载左右两边的Fragment。



#### Fragment销毁回收

既然vp2是与Recyclerview有关系，那么Fragment的绑定与回收肯定就是与adapter有关系。

具体的回收与缓存操作还是在Recyclerview中实现，有机会会写一篇关于Recyclerview的缓存与预存的源码阅读

我们去看看FragmentStateAdapter的源码：

```java
public abstract class FragmentStateAdapter extends
        RecyclerView.Adapter<FragmentViewHolder> implements StatefulAdapter
```

FragmentViewHolder很简单，就是创建了一个FrameLayout，然后会将我们创建的Fragment添加进去。

继续看onBindViewHolder的方法，在这个方法中，我们可以看到很关键的一个方法叫做gcFragments。

```java
  void gcFragments() {
        if (!mHasStaleFragments || shouldDelayFragmentTransactions()) {
            return;
        }
        // Remove Fragments for items that are no longer part of the data-set
        Set<Long> toRemove = new ArraySet<>();
        for (int ix = 0; ix < mFragments.size(); ix++) {
            long itemId = mFragments.keyAt(ix);
            if (!containsItem(itemId)) {
                toRemove.add(itemId);
                mItemIdToViewHolder.remove(itemId); // in case they're still bound
            }
        }
        // Remove Fragments that are not bound anywhere -- pending a grace period
        if (!mIsInGracePeriod) {
            mHasStaleFragments = false; // we've executed all GC checks

            for (int ix = 0; ix < mFragments.size(); ix++) {
                long itemId = mFragments.keyAt(ix);
                if (!isFragmentViewBound(itemId)) {
                    toRemove.add(itemId);
                }
            }
        }
        for (Long itemId : toRemove) {
            removeFragment(itemId);
        }
    }
```

在Recyclerview进行缓存与回收的时候，在FragmentStateAdapter中，同时也用了两个LongSparseArray进行缓存与回收。

如果我们要设置缓存的Fragment的个数，需要调用setItemViewCacheSize方法。也可以调用vp2的offscreenPageLimit。

