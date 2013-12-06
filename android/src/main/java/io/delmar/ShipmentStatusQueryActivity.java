package io.delmar;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;

/**
 * Created by jinw on 04/10/13.
 */
public class ShipmentStatusQueryActivity extends BaseActivity {

    public static final String DELMAR_API_URL = "http://www.delmarcargo.com/api";
    public static final String PARS_QUERY_URL = DELMAR_API_URL + "/shipment/status.json?";

    public static final String TAG_PARS_FILENUMBER = "fileNumber";
    public static final String TAG_PARS_CARRIERCODE = "carrierCode";
    public static final String TAG_PARS_CARGOCONTROLNUMBER = "cargoControlNumber";
    public static final String TAG_PARS_SHIPPERREFERENCE = "shipperReference";
    public static final String TAG_PARS_PORTNUMBER = "portNumber";
    public static final String TAG_PARS_PORTNAME = "portName";
    public static final String TAG_PARS_CUSTOMSACCEPTEDDATE = "customsAcceptedDate";
    public static final String TAG_PARS_CUSTOMSRELEASEDDATAE = "customsReleasedDate";
    public static final String TAG_PARS_QUANTITY = "quantity";
    public static final String TAG_PARS_UOM = "uom";

    public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pars);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Button searchButton = (Button) findViewById(R.id.parsSearchButton);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // clear previous result;
                TextView resultView = (TextView) findViewById(R.id.parsResultView);
                resultView.setText("");

                String carrier = ((EditText) findViewById(R.id.editText1)).getText().toString();
                String ccn = ((EditText) findViewById(R.id.editText2)).getText().toString();

                String queryStr = "carrier=" + carrier + "&ccn=" + ccn;
                String stringUrl = PARS_QUERY_URL + queryStr;
                ConnectivityManager connMgr = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    new ParseJsonTask().execute(stringUrl);
                } else {
                    toast(R.string.no_network_connection);
                }
            }
        });
    }

    private class ParseJsonTask extends AsyncTask<String, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(String... urls) {

            return getJSONFromUrl(urls[0]);

        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(JSONObject result) {
            try {
                TextView tv = (TextView) findViewById(R.id.parsResultView);
                if (result != null) {
                    String shipperRef = result.getString(TAG_PARS_SHIPPERREFERENCE);
                    String fileNumber = result.getString(TAG_PARS_FILENUMBER);
                    String customsAccepted = result.getString(TAG_PARS_CUSTOMSACCEPTEDDATE);
                    String customsReleased = result.getString(TAG_PARS_CUSTOMSRELEASEDDATAE);
                    String quantity = result.getString(TAG_PARS_QUANTITY);
                    String uom = result.getString(TAG_PARS_UOM);
                    String portName = result.getString(TAG_PARS_PORTNAME);
                    String portNumber = result.getString(TAG_PARS_PORTNUMBER);
                    StringBuilder sb = new StringBuilder();
                    sb.append("Shipper Reference: ").append(shipperRef).append("\n");
                    sb.append("FileNumber: ").append(fileNumber).append("\n");
                    sb.append("Customs Accepted Date: ").append(customsAccepted != null ? customsAccepted.substring(0, customsAccepted.indexOf("T") + 6) : "").append("\n");
                    sb.append("Customs Released Date: ").append(customsReleased != null ? customsReleased.substring(0, customsAccepted.indexOf("T") + 6) : "").append("\n");
                    sb.append("Quantity: ").append(quantity).append("\n");
                    sb.append("UOM: ").append(uom).append("\n");
                    sb.append("Port Name: ").append(portName).append("\n");
                    sb.append("Port Number: ").append(portNumber).append("\n");
                    // tv.setText(result.toString(4));
                    tv.setText(sb.toString());
                } else {
                    tv.setText(R.string.pars_not_found);
                }
            } catch (JSONException e) {
                toast(R.string.json_error);
            }
        }

    }

    public JSONObject getJSONFromUrl(String url) {
        InputStream is = null;
        JSONObject jObj = null;
        String json = "";
        // Making HTTP request
        try {
            // defaultHttpClient
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpPost = new HttpGet(url);

            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        // try parse the string to a JSON object
        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        // return JSON String
        return jObj;

    }

    private void toast(int id) {
        String text = getResources().getString(id);
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
    }

}