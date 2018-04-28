package com.swolf.librarybase.baseView.tabFragmentDemo;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.swolf.librarybase.R;
import com.swolf.librarybase.baseView.activity.NYBaseFragmentActivity;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
@SuppressLint("NewApi")
public class TabFragmentActivity extends NYBaseFragmentActivity {
	FragmentManager fm;
	FragmentTransaction ft;
	int currentSelectIndex = -1;

	TextView textView0;
	TextView textView1;
	TextView textView2;
	TextView textView3;

	
	FragmentTab0 ft0 = new FragmentTab0();
	FragmentTab1 ft1 = new FragmentTab1();
	FragmentTab2 ft2 = new FragmentTab2();
	FragmentTab3 ft3 = new FragmentTab3();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.ny_demo_activity_tab_fragment);

		fm = this.getSupportFragmentManager();

		textView0 = (TextView)findViewById(R.id.textView0);
		textView1 = (TextView)findViewById(R.id.textView1);
		textView2 = (TextView)findViewById(R.id.textView2);
		textView3 = (TextView)findViewById(R.id.textView3);

		textView0.setOnClickListener(onclick);
		textView1.setOnClickListener(onclick);
		textView2.setOnClickListener(onclick);
		textView3.setOnClickListener(onclick);

		setTabItem(0);
	}

	View.OnClickListener onclick = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			if(v == textView0){
				setTabItem(0);
			}else if(v == textView1){
				setTabItem(1);
			}else if(v == textView2){
				setTabItem(2);
			}else if(v == textView3){
				setTabItem(3);
			}
		}
	};

	
	private void setTabItem(int tabSelectIndex) {
		if (currentSelectIndex != tabSelectIndex) {
			textView0.setTextColor(getResources().getColor(R.color.black));
			textView1.setTextColor(getResources().getColor(R.color.black));
			textView2.setTextColor(getResources().getColor(R.color.black));
			textView3.setTextColor(getResources().getColor(R.color.black));

			ft = fm.beginTransaction();
			switch (tabSelectIndex) {
			case 0:
				ft.replace(R.id.linearLayoutFragment, ft0,"ft0");
				textView0.setTextColor(getResources().getColor(R.color.red));
				break;
			case 1:
				ft.replace(R.id.linearLayoutFragment, ft1,"ft1");
				textView1.setTextColor(getResources().getColor(R.color.red));
				break;
			case 2:
				ft.replace(R.id.linearLayoutFragment, ft2,"ft2");
				textView2.setTextColor(getResources().getColor(R.color.red));
				break;
			case 3:
				ft.replace(R.id.linearLayoutFragment, ft3,"ft3");
				textView3.setTextColor(getResources().getColor(R.color.red));
				break;
			default:
				break;
			}
			ft.commit();
		}
	}

}
