package com.bocop.zyt.added;


import android.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by android_sant on 2016/4/29.
 */
public class HomePage_ extends Fragment implements View.OnClickListener {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      final View v  = inflater.inflate(R.layout.activity_list_item, null);
        initViews(v);


        return v;
    }

    private void initViews(View v) {
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case 1:
//               startActivity(new Intent(getActivity(), AssociationSearch.class));
                break;
            default:
                break;
        }
    }



}
