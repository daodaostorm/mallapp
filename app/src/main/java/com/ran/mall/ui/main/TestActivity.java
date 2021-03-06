package com.ran.mall.ui.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ran.mall.R;
import com.ran.mall.widget.BannerView;

import java.util.ArrayList;
import java.util.List;


 
public class TestActivity extends AppCompatActivity implements View.OnClickListener  {

    public static String[] titles = new String[]{
            "每周7件Tee不重样",
            "俏皮又知性 适合上班族的漂亮衬衫",
            "名侦探柯南",
            "境界之轮回",
            "我的英雄学院",
            "全职猎人",
            };
    public static String[] urls = new String[]{//750x500
           "https://s2.mogucdn.com/mlcdn/c45406/170422_678did070ec6le09de3g15c1l7l36_750x500.jpg",
           "https://s2.mogucdn.com/mlcdn/c45406/170420_1hcbb7h5b58ihilkdec43bd6c2ll6_750x500.jpg",
           "http://s18.mogucdn.com/p2/170122/upload_66g1g3h491bj9kfb6ggd3i1j4c7be_750x500.jpg",
           "http://s18.mogucdn.com/p2/170204/upload_657jk682b5071bi611d9ka6c3j232_750x500.jpg",
           "http://s16.mogucdn.com/p2/170204/upload_56631h6616g4e2e45hc6hf6b7g08f_750x500.jpg",
           "http://s16.mogucdn.com/p2/170206/upload_1759d25k9a3djeb125a5bcg0c43eg_750x500.jpg"
                                               };

    public static class BannerItem {
        public String image;
        public String title;

        @Override
        public String toString() {
            return title;
        }
    }

    public static class BannerViewFactory implements BannerView.ViewFactory<BannerItem> {
        @Override
        public View create(BannerItem item, int position, ViewGroup container) {
            ImageView iv = new ImageView(container.getContext());
            //RequestOptions options = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.DATA);
            Glide.with(container.getContext().getApplicationContext()).load(item.image).into(iv);
            return iv;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        List<BannerItem> list = new ArrayList<>();
        for (int i = 0; i < urls.length; i++) {
            BannerItem item = new BannerItem();
            item.image = urls[i];
            item.title = titles[i];

            list.add(item);
        }

        final BannerView banner1 = (BannerView) findViewById(R.id.banner1);
        banner1.setViewFactory(new BannerViewFactory());
        banner1.setDataList(list);
        banner1.start();


        final BannerView banner2 = (BannerView) findViewById(R.id.banner2);
        banner2.setViewFactory(new BannerViewFactory());
        banner2.setDataList(list);
        banner2.start();

     }


    @Override
    public void onClick(View v) {

    }
}
