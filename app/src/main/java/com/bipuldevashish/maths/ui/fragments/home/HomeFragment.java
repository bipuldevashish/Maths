package com.bipuldevashish.maths.ui.fragments.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bipuldevashish.maths.R;
import com.bipuldevashish.maths.models.SliderItem;
import com.bipuldevashish.maths.ui.AllCourse;
import com.bipuldevashish.maths.ui.DoubtActivity;
import com.bipuldevashish.maths.ui.FreeVideosActivity;
import com.bipuldevashish.maths.ui.LiveClass;
import com.bipuldevashish.maths.ui.Mycourse;
import com.bipuldevashish.maths.ui.PdfActivity;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        List<Integer> sliderItems;
        sliderItems = new ArrayList<>();
        sliderItems.add( R.drawable.poster1);
        sliderItems.add(R.drawable.poster2);
        SliderView sliderView = root.findViewById(R.id.imageSlider);
        sliderView.setSliderAdapter(new SliderAdapterExample(requireContext(),
                sliderItems));

        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.startAutoCycle();
        sliderView.setScrollTimeInMillis(5000);

        LinearLayout button_liveClass = root.findViewById(R.id.btn_live_class);
        button_liveClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LiveClass.class);
                startActivity(intent);
            }
        });

        LinearLayout button_allCourses  = root.findViewById(R.id.btn_all_courses);
        button_allCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AllCourse.class));

            }
        });

        LinearLayout my_courses = root.findViewById(R.id.btn_my_courses);
        my_courses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent( getActivity(), Mycourse.class));
            }
        });

        LinearLayout free_pdf = root.findViewById(R.id.btn_free_pdf);
        free_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent( getActivity(), PdfActivity.class));
            }
        });

        LinearLayout testSeries = root.findViewById(R.id.btn_doubts);
        testSeries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent( getActivity(), DoubtActivity.class));
            }
        });

        LinearLayout freeVideos = root.findViewById(R.id.btn_free_videos);
        freeVideos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), FreeVideosActivity.class));
            }
        });

        return root;
    }

}