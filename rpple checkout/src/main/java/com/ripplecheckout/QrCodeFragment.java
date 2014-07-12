package com.ripplecheckout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class QrCodeFragment extends Fragment {
    public QrCodeFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_example, container, false);
        wireUpButton(rootView);

        AndroidBarcodeView barcodeView = new AndroidBarcodeView(getActivity().getApplicationContext());

        return barcodeView;
        //return rootView;
    }

    void back() {
        getFragmentManager().popBackStack();
    }

    void wireUpButton(View rootView) {
        final Button button = (Button)rootView.findViewById(R.id.back_fragment_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                back();
            }
        });
    }

}
