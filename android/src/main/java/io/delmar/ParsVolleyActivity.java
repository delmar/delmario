package io.delmar;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jinw on 04/10/13.
 */
public class ParsVolleyActivity extends BaseActivity {

    public static final String DELMAR_API_URL = "http://www.delmarcargo.com/api";
    public static final String PARS_QUERY_URL = DELMAR_API_URL + "/pars/?";

    private String TAG = this.getClass().getSimpleName();
    private RequestQueue mRequestQueue;

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
                mRequestQueue = Volley.newRequestQueue(view.getContext());

                // clear previous result;
                TextView resultView = (TextView) findViewById(R.id.parsResultView);
                resultView.setText("");

                String carrier = ((EditText) findViewById(R.id.editText1)).getText().toString();
                String ccn = ((EditText) findViewById(R.id.editText2)).getText().toString();

                String queryStr = "carrier=" + carrier + "&ccn=" + ccn;
                JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET, PARS_QUERY_URL + queryStr, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // Log.i(TAG, "size = " + response.length());
                                try {
                                    int code = parseJSON(response);
                                    if (code != 200) {
                                        toast(R.string.server_error);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    toast(R.string.json_error);
                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.i(TAG, error.getMessage());
                            }
                        }
                );

                mRequestQueue.add(jr);
                mRequestQueue.start();
            }
        });
    }

    private int parseJSON(JSONObject root) throws JSONException {
        String fileNumber = root.getString("fileNumber");
        String status = root.getString("status");
        String accountSecurityNumber = root.getString("accountSecurityNumber");
        String checkDigit = root.getString("checkDigit");
        String portNumber = root.getString("portNumber");
        String ediSequence = root.getString("ediSequence");
        String releasedDate = root.getString("releasedDate");

        TextView tv = (TextView) findViewById(R.id.parsResultView);

        StringBuilder sb = new StringBuilder();

        if (fileNumber == null) {
            sb.append(R.string.pars_not_found);
        } else {
            sb.append("FileNumber: ").append(fileNumber).append("\n");
            sb.append("Status: ").append(status).append("\n");
            sb.append("Released Date: ").append(releasedDate).append("\n");
            sb.append("Account Security Number: ").append(accountSecurityNumber).append("\n");
            sb.append("Check Digit: ").append(checkDigit).append("\n");
            sb.append("Port Number: ").append(portNumber).append("\n");
            sb.append("EDI Sequence: ").append(ediSequence).append("\n");
        }
        tv.setText(sb.toString());
        return 200;
    }

    class ParsResult {
        private String fileNumber;
        private String accouontSecurityNumber;
        private String transactionNumber;
        private String checkDigit;
        private String portNumber;
        private String ediSequence;
        private String status;
        private String releasedDate;

        public String getFileNumber() {
            return fileNumber;
        }

        public void setFileNumber(String fileNumber) {
            this.fileNumber = fileNumber;
        }

        public String getAccouontSecurityNumber() {
            return accouontSecurityNumber;
        }

        public void setAccouontSecurityNumber(String accouontSecurityNumber) {
            this.accouontSecurityNumber = accouontSecurityNumber;
        }

        public String getTransactionNumber() {
            return transactionNumber;
        }

        public void setTransactionNumber(String transactionNumber) {
            this.transactionNumber = transactionNumber;
        }

        public String getCheckDigit() {
            return checkDigit;
        }

        public void setCheckDigit(String checkDigit) {
            this.checkDigit = checkDigit;
        }

        public String getPortNumber() {
            return portNumber;
        }

        public void setPortNumber(String portNumber) {
            this.portNumber = portNumber;
        }

        public String getEdiSequence() {
            return ediSequence;
        }

        public void setEdiSequence(String ediSequence) {
            this.ediSequence = ediSequence;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getReleasedDate() {
            return releasedDate;
        }

        public void setReleasedDate(String releasedDate) {
            this.releasedDate = releasedDate;
        }
    }

    private void toast(int id) {
        String text = getResources().getString(id);
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
    }

}