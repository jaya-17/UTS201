package com.example.tugas201;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ContactAdd extends Activity {

	private String TAG = ContactAdd.class.getSimpleName();
    private ProgressDialog pDialog;
    
    private static String url = "http://apilearning.totopeto.com/contacts";
	
	Button bsimpan;
	EditText ename, eaddress, eemail, ephone, edob;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_add);
		
		bsimpan = (Button) findViewById(R.id.btsimpan);
		ename = (EditText) findViewById(R.id.etname);
		eaddress = (EditText) findViewById(R.id.etaddress);
		eemail = (EditText) findViewById(R.id.etemail);
		ephone = (EditText) findViewById(R.id.etphone);
		edob = (EditText) findViewById(R.id.etdob);
		
		bsimpan.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new AddContact().execute();
				Intent intent = new Intent(ContactAdd.this, MainActivity.class);
				startActivity(intent);
			}
		});
	}
	
	private class AddContact extends AsyncTask<Void, Void, Void> {
		
		@Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(ContactAdd.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }
        
        @Override
        protected Void doInBackground(Void... arg0) {
            String post_params = null;
            JSONObject params = new JSONObject();
 
            try {
            	params.put("name", ename.getText().toString());
            	params.put("address", eaddress.getText().toString());
            	params.put("email", eemail.getText().toString());
            	params.put("phone", ephone.getText().toString());
            	params.put("dob", edob.getText().toString());
            	post_params = params.toString();
            	
            } catch (JSONException e) {
            	e.printStackTrace();
            }
            
            HttpHandler data = new HttpHandler();
            String jsonStr = data.makePostRequest(url, post_params);
            Log.e(TAG, "Response from url: " + jsonStr);
            
            return null;
        }
 
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
        }
	}
}
