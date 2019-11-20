package com.example.tugas201;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

public class ContactMessages extends Activity {

	ActionBar.Tab TabInbox, TabOutbox;
	Fragment fragmentTabInbox = new FragmentTabInbox();
	Fragment fragmentTabOutbox = new FragmentTabOutbox();
	
	private String contact_id, contact_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_messages);
        
        Intent intent = getIntent();
        contact_id = intent.getStringExtra("id");
        contact_name = intent.getStringExtra("name");
        Bundle bundle = new Bundle();
        bundle.putString("id", contact_id);
        bundle.putString("name", contact_name);
        fragmentTabInbox.setArguments(bundle);
        fragmentTabOutbox.setArguments(bundle);
        
        setTitle(contact_name);
        
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        
        TabInbox = actionBar.newTab().setText("Inbox");
        TabOutbox = actionBar.newTab().setText("Outbox");
        
        TabInbox.setTabListener(new TabListener(fragmentTabInbox));
        TabOutbox.setTabListener(new TabListener(fragmentTabOutbox));
        
		actionBar.addTab(TabInbox);
		actionBar.addTab(TabOutbox);
    }
}
