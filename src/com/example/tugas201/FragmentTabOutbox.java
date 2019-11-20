package com.example.tugas201;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentTabOutbox extends Fragment {
	
	private String TAG = ContactMessages.class.getSimpleName();
    private ProgressDialog pDialog;
	
	private Button bsend;
	private TextView toutbox;
	private ListView lv;
	
	private static String url = "http://apilearning.totopeto.com/messages/outbox?id=";
	
	private ArrayList<HashMap<String, String>> messageList;
	
	private int outboxLength;
	private String contact_id, contact_name;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab_outbox, container, false);
        
        lv = (ListView) rootView.findViewById(R.id.messagelist);
        bsend = (Button) rootView.findViewById(R.id.btsend);
        toutbox = (TextView) rootView.findViewById(R.id.tvoutbox);
        
        bsend.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), MessageSend.class);
				intent.putExtra("contact_id", contact_id);
				intent.putExtra("contact_name", contact_name);
				startActivity(intent);
			}
		});
        
        return rootView;
    }
	
	private class GetMessages extends AsyncTask<Void, Void, Void> {
	   	 
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }
 
        @Override
        protected Void doInBackground(Void... arg0) {
        	contact_id = getArguments().getString("id");
        	contact_name = getArguments().getString("name");
        	
            HttpHandler sh = new HttpHandler();
 
            String jsonStr = sh.makeServiceCall(url + contact_id);
 
            Log.e(TAG, "Response from url: " + jsonStr);
 
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray data = jsonObj.getJSONArray("data");
                    outboxLength = data.length();
                    
                    for (int i = 0; i < outboxLength; i++) {
                        JSONObject c = data.getJSONObject(i);
                        
                        String id = c.getString("id");
                        String content = c.getString("content");
                        String created_at = c.getString("created_at");
                        String to = c.getString("to");
                        
                        HashMap<String, String> message = new HashMap<String, String>();
 
                        message.put("id", id);
                        message.put("to", to);
                        message.put("content", content);
                        message.put("created_at", created_at);
                        
                        messageList.add(message);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }
 
            return null;
        }
 
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            
            if (pDialog.isShowing())
                pDialog.dismiss();
            
            toutbox.setText(String.valueOf(outboxLength));
            
            ListAdapter adapter = new SimpleAdapter(
                    getActivity(), messageList,
                    R.layout.list_messages, new String[]{"to", "content",
                    "created_at"}, new int[]{R.id.recipient,
                    R.id.content, R.id.created_at});
 
            lv.setAdapter(adapter);
        }
    }
	
	@Override
    public void onResume() {
    	super.onResume();
    	messageList = new ArrayList<HashMap<String, String>>();
    	new GetMessages().execute();
    }
}
