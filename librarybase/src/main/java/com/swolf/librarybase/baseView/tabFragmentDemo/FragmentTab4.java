package com.swolf.librarybase.baseView.tabFragmentDemo;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 
 * @author LiuYi-15973602714
 * @version V1.0
 */
@SuppressLint("NewApi")
public class FragmentTab4 extends Fragment {
	TabFragmentActivity a;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

//		View view = inflater.inflate(R.layout.fragment_tab04, null);

		TextView tv = new TextView(a);
		int MATCH_PARENT = LinearLayout.LayoutParams.MATCH_PARENT;
		int WRAP_CONTENT = LinearLayout.LayoutParams.WRAP_CONTENT;
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(MATCH_PARENT,WRAP_CONTENT);
		tv.setText("FragmentTab3");
		tv.setLayoutParams(lp);
		
		return tv;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public void onAttach(Activity activity) {
		super.onAttach(activity);
		a = (TabFragmentActivity) activity;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onDetach() {
		super.onDetach();
	}
}
