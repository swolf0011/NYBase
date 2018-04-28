package com.swolf.libraryviewviewpage.viewPager;

import android.content.Context;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.swolf.libraryviewviewpage.R;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * ViewPager
 * Created by LiuYi-15973602714 on 2017-01-01
 */
public class NYViewPager {

    public interface INYViewPagerHander {
        public void onClick(int index);
    }

    public View view;
    private ViewPager viewPager;
    public TextView textView_title;
    public LinearLayout linearLayout_bottom;
    private LinearLayout linearLayout_dot;
    private List<NYBanner> list;
    private INYViewPagerHander viewPagerHandler;

    private int currentItemIndex = 0;
    private int oldItemIndex = 0;
    private Context context;
    private boolean isAutoChange;


    public View getView() {
        return view;
    }


    public NYViewPager(Context contexts, List<NYBanner> lists, boolean isShowTitle, boolean isShowDot, boolean isAutoChanges, INYViewPagerHander viewPagerHandler) {
        this.context = contexts;
        this.list = lists;
        this.isAutoChange = isAutoChanges;
        this.viewPagerHandler = viewPagerHandler;

        LayoutInflater li = LayoutInflater.from(context);
        view = li.inflate(R.layout.ny_view_viewpage, null);

        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        textView_title = (TextView) view.findViewById(R.id.textView_title);
        linearLayout_bottom = (LinearLayout) view.findViewById(R.id.linearLayout_bottom);
        linearLayout_dot = (LinearLayout) view.findViewById(R.id.linearLayout_dot);
        linearLayout_dot.removeAllViews();


        if (isShowTitle) {
            textView_title.setVisibility(View.VISIBLE);
        } else {
            textView_title.setVisibility(View.GONE);
        }
        if (isShowDot) {
            linearLayout_dot.setVisibility(View.VISIBLE);
        } else {
            linearLayout_dot.setVisibility(View.GONE);
        }

        if (isShowDot || isShowTitle) {
            linearLayout_bottom.setVisibility(View.VISIBLE);
        } else {
            linearLayout_bottom.setVisibility(View.GONE);
        }
        for (int i = 0; i < list.size(); i++) {
            View v = new View(context);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(20, 20);
            lp.setMargins(3, 3, 3, 3);
            v.setLayoutParams(lp);
            v.setBackgroundResource(R.drawable.dot_normal);
            linearLayout_dot.addView(v);
        }

        if (list != null && list.size() > 0) {
            linearLayout_dot.getChildAt(0).setBackgroundResource(R.drawable.dot_focused);
            textView_title.setText(list.get(0).title);
        } else {
            textView_title.setText("");
        }
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                NYBanner banner = list.get(position);
                ImageView imageView = new ImageView(context);
                if (banner.type == NYBanner.TYPE_DRAWABLE) {
                    imageView.setImageResource(banner.drawable);
                } else if (banner.type == NYBanner.TYPE_URL) {
//                    NYImageLoader.showLoaderImage(banner.img_url, imageView);
                }
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.setOnClickListener(clickListener);

                container.addView(imageView);
                return imageView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public void restoreState(Parcelable arg0, ClassLoader arg1) {

            }

            @Override
            public Parcelable saveState() {
                return null;
            }

            @Override
            public void startUpdate(ViewGroup container) {

            }

            @Override
            public void finishUpdate(ViewGroup container) {

            }
        });// 设置填充ViewPager页面的适配器
        viewPager.addOnPageChangeListener(pageChangeListener);// 设置一个监听器，当ViewPager中的页面改变时调用

        timerStart();
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (viewPagerHandler != null) {
                timerStop();
                viewPagerHandler.onClick(currentItemIndex);
            }
        }
    };

    ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        public void onPageSelected(final int position) {
            pageChange(position);
        }

        public void onPageScrollStateChanged(int arg0) {
        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }
    };

    private synchronized void pageChange(int position) {
        currentItemIndex = position;
        linearLayout_dot.getChildAt(oldItemIndex).setBackgroundResource(R.drawable.dot_normal);
        linearLayout_dot.getChildAt(currentItemIndex).setBackgroundResource(R.drawable.dot_focused);
        NYBanner banner = list.get(currentItemIndex);
        textView_title.setText(banner.title);
        oldItemIndex = position;
    }

    private Timer timer = null;
    private boolean isTimerStart = true;

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            currentItemIndex = (currentItemIndex + 1) % list.size();
            viewPager.setCurrentItem(currentItemIndex);// 切换当前显示的图片
        }
    };

    public void timerStart() {
        if (isAutoChange && isTimerStart) {
            isTimerStart = false;
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (list.size() > 0) {
                        synchronized (viewPager) {
                            handler.obtainMessage().sendToTarget(); // 通过Handler切换图片
                        }
                    }
                }
            }, 5000, 5000);
        }
    }

    public void timerStop() {
        isTimerStart = true;
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
