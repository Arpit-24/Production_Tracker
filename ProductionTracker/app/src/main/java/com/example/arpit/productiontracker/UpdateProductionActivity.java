package com.example.arpit.productiontracker;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UpdateProductionActivity extends AppCompatActivity {

    JSONParser jsonParser = new JSONParser();
    private static String update_url = "https://ps1project.herokuapp.com/update_db.php";
    private static final String TAG_SUCCESS = "success";
    private int success;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_production);
        new UpdateProduction(UpdateProductionActivity.this).execute();
    }
    class UpdateProduction extends AsyncTask<String, String, String> {
        private ProgressDialog progressDialog;
        public UpdateProduction(Context context) {
            this.progressDialog = new ProgressDialog(context);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.progressDialog.setMessage("Updating...");
            this.progressDialog.setIndeterminate(false);
            this.progressDialog.setCancelable(false);
            this.progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            JSONObject jsonObject = jsonParser.makeHttpRequest(update_url, "GET", params);
            Log.d("All Products: ", jsonObject.toString());
            try {
                success = jsonObject.getInt(TAG_SUCCESS);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (success == 1) {
                        Toast.makeText(UpdateProductionActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(UpdateProductionActivity.this, "Couldn't Update", Toast.LENGTH_SHORT).show();
                    }
                    Intent intent = new Intent(UpdateProductionActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}
