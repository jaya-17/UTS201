package com.example.tugas201;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;

public class TabListener implements ActionBar.TabListener{
	
	Fragment fragment;
	
	public TabListener(Fragment fragment){
		this.fragment = fragment;
	}
	
	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabSelected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		arg1.replace(R.id.fragment_container, fragment);
	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		arg1.remove(fragment);
	}
}
