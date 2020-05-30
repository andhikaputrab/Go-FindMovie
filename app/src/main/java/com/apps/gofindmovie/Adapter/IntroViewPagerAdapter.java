package com.apps.gofindmovie.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.apps.gofindmovie.R;
import com.apps.gofindmovie.ScreenItem;

import java.util.List;

public class IntroViewPagerAdapter extends PagerAdapter {

    private Context context;
    private List<ScreenItem> ListScreen;

    public IntroViewPagerAdapter(Context context, List<ScreenItem> listScreen) {
        this.context = context;
        ListScreen = listScreen;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        @SuppressLint("InflateParams") View layoutScreen = inflater.inflate(R.layout.layout_intro_item, null);

        ImageView imgSlide = layoutScreen.findViewById(R.id.intro_img);
        TextView title = layoutScreen.findViewById(R.id.intro_title);
        TextView description = layoutScreen.findViewById(R.id.intro_description);

        title.setText(ListScreen.get(position).getTitle());
        description.setText(ListScreen.get(position).getDescription());
        imgSlide.setImageResource(ListScreen.get(position).getScreenImg());

        container.addView(layoutScreen);

        return layoutScreen;
    }

    @Override
    public int getCount() {
        return ListScreen.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
