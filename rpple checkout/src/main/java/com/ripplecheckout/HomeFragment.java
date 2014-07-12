package com.ripplecheckout;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;
import com.loopj.android.http.*;
import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A placeholder fragment containing a simple view.
 */
public class HomeFragment extends Fragment {

    TextView textView;
    EditText inputNameEditText;

    public HomeFragment() {
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        textView = (TextView)rootView.findViewById(R.id.textView);
        textView.setTextIsSelectable (true);
        final Button button = (Button)rootView.findViewById(R.id.button);

        inputNameEditText = (EditText)rootView.findViewById(R.id.editText);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                final String name = inputNameEditText.getText().toString();
                getRippleNameAuthInfo(name);
            }
        });

        final Button launchQrButton = (Button)rootView.findViewById(R.id
                .launchQrButton);
        launchQrButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                launchQrCodeFragment();
            }
        });

        return rootView;
    }

    void getRippleNameAuthInfo(final String name) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://id.ripple.com/v1/authinfo?username=" + name, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                textView.setText("Looking up the account for "+name);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                String address;
                try {
                    address = response.getString("address");
                } catch(JSONException exception) {
                    address = "No Address Found";
                }
                textView.setText(address);
            }

            @Override
            public void onFailure(int statusCode, org.apache.http.Header[] headers, java.lang.Throwable throwable, org.json.JSONObject errorResponse) {
                textView.setText("Lookup Request Failed");
            }

            @Override
            public void onRetry(int retryNo) {
                textView.setText("onRetry");
            }
        });

    }

    void launchQrCodeFragment() {
        QrCodeFragment exampleFragment = new QrCodeFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, exampleFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}