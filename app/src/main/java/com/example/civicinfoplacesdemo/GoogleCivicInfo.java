package com.example.civicinfoplacesdemo;

import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.gson.GsonFactory;rvices.civicinfo.model.*;

import java.io.IOException;



abstract public class GoogleCivicInfo {
    private static final String TAG = GoogleCivicInfo.class.getName();

    private static final HttpTransport HTTP_TRANSPORT = AndroidHttp.newCompatibleTransport();
    private static final GsonFactory
import com.google.api.services.civicinfo.CivicInfo;
import com.google.api.services.civicinfo.CivicInfoRequestInitializer;
import com.google.api.seJSON_FACTORY = new GsonFactory();
    private static final HttpRequestInitializer httpRequestInitializer = new HttpRequestInitializer() {
        @Override
        public void initialize(HttpRequest request) throws IOException {
        }
    };

    public void getInfo(final String address) {

        new AsyncTask<Void, Void, RepresentativeInfoResponse>() {
            @Override
            protected RepresentativeInfoResponse doInBackground(Void... params) {
                GoogleClientRequestInitializer KEY_INITIALIZER =
                        new CivicInfoRequestInitializer(GooglePlacesKey.API_KEY);

                CivicInfo civicInfo =
                        new CivicInfo.Builder(HTTP_TRANSPORT, JSON_FACTORY, httpRequestInitializer)
                                .setApplicationName("Places")
                                .setGoogleClientRequestInitializer(KEY_INITIALIZER)
                                .build();

                RepresentativeInfoResponse representativeInfoResponse = null;
                try {
                    representativeInfoResponse = civicInfo.representatives()
                            .representativeInfoByAddress()
                            .setAddress(address)
                            .execute();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
                return representativeInfoResponse;
            }

            @Override
            protected void onPostExecute(RepresentativeInfoResponse representativeInfoResponse) {
                super.onPostExecute(representativeInfoResponse);

                if (representativeInfoResponse != null)
                    onSuccess(representativeInfoResponse);
                else
                    onFailure();
            }
        }.execute();
    }

    abstract public void onSuccess(RepresentativeInfoResponse response);
    abstract public void onFailure();
}
