package com.example.arpit.productiontracker;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SetTargetActivity extends AppCompatActivity {
    JSONParser jsonParser = new JSONParser();
    private static String update_url = "https://ps1project.herokuapp.com/set_target.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_NAME = "itemName";
    private static final String TAG_TARGET = "target";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_target);
        Bundle bundle = getIntent().getExtras();
        String itemName = "Part Name";
        if (bundle!= null) {
            itemName = bundle.getString("itemName");
        }
        final TextView partName = (TextView) findViewById(R.id.set_target_part);
        partName.setText(itemName);
        final TextView partValue = (TextView) findViewById(R.id.set_target_text);
        Button button = (Button) findViewById(R.id.edit_target_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new UpdateTarget(partName.getText().toString() ,Integer.parseInt(partValue.getText().toString()), SetTargetActivity.this).execute();
            }
        });
    }

    class UpdateTarget extends AsyncTask<String, String, String> {
        private String partName;
        private int target;
        private ProgressDialog progressDialog;
        private int success;
        public UpdateTarget(String partName, int target, Context context) {
            this.partName = partName;
            this.target = target;
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
            params.add(new BasicNameValuePair(TAG_NAME, partName));
            params.add(new BasicNameValuePair(TAG_TARGET, ""+target+""));
            JSONObject jsonObject = jsonParser.makeHttpRequest(update_url, "POST", params);
//            Log.d("All Products: ", jsonObject.toString());
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
                        Toast.makeText(SetTargetActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SetTargetActivity.this, "Couldn't update", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
