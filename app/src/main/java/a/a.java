package a;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextClock;
import android.widget.TextView;
import java.util.List;
import android.graphics.drawable.GradientDrawable;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Handler;

public class a extends Activity {
	ListView apps;
	TextClock time, date;
	TextView myName;
	SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTheme(android.R.style.Theme_DeviceDefault_Wallpaper_NoTitleBar);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		LinearLayout base = new LinearLayout(this);
		RelativeLayout main = new RelativeLayout(this);
		LinearLayout widgets = new LinearLayout(this);
		Intent intent = new Intent(Intent.ACTION_MAIN, (Uri) null);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		final List<ResolveInfo> list = getPackageManager().queryIntentActivities(intent, 0);
		apps = new ListView(this);
		final ArrayAdapter<String> str = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
		
		LinearLayout td = new LinearLayout(this);
		time = new TextClock(this);
		date = new TextClock(this);
		myName = new TextView(this);
		
		GradientDrawable draw = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]{Color.parseColor("#10333333"), Color.parseColor("#ff333333")});
		sp = PreferenceManager.getDefaultSharedPreferences(this);
		
		
		td.setOrientation(LinearLayout.VERTICAL);
		td.setGravity(Gravity.CENTER);
		td.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		
		time.setFormat12Hour("hh:mm:ss");
		time.setFormat24Hour("kk:mm:ss");
		time.setTextSize(30);
		time.setGravity(Gravity.CENTER);
		
		date.setFormat12Hour("EEE dd, MMM, yyyy");
		date.setFormat24Hour("EEE dd, MMM, yyyy");
		date.setTextSize(15);
		date.setGravity(Gravity.CENTER);
		
		myName.setText(sp.getString("name", "RyannKim327"));
		myName.setTextSize(10);
		myName.setGravity(Gravity.CENTER);
		
		//base.setBackground(getWallpaper());
		base.setOrientation(LinearLayout.VERTICAL);
		base.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		
		widgets.setOrientation(LinearLayout.VERTICAL);
		widgets.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, Math.round(getWindowManager().getDefaultDisplay().getHeight() * 0.7f)));
		widgets.setGravity(Gravity.CENTER);
		
		main.setGravity(Gravity.BOTTOM | Gravity.CENTER);
		main.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		
		final int[] app = new int[list.size()];
		String[] app_ = new String[list.size()];
		for(int i = 0; i < list.size(); i++){
			String name = list.get(i).activityInfo.loadLabel(getPackageManager()).toString();
			app_[i] = name;
			app[i] = i;
		}
		
		for(int i = 0; i < app_.length; i++){
			for(int j = 0; j < i; j++){
				char a = app_[i].toLowerCase().charAt(0);
				char b = app_[j].toLowerCase().charAt(0);
				if(a < b){
					String c = app_[i];
					int d = app[i];
					app_[i] = app_[j];
					app[i] = app[j];
					app_[j] = c;
					app[j] = d;
				}
			}
		}
		
		for(int i = 0; i < app_.length; i++){
			str.add(app_[i].toString());
		}
		
		apps.setBackgroundDrawable(draw);
		apps.setAdapter(str);
		apps.setVisibility(View.GONE);
		apps.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		apps.setOnItemClickListener(new OnItemClickListener(){
				@Override
				public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4) {
					int x = app[p3];
					Intent i = getPackageManager().getLaunchIntentForPackage(list.get(x).activityInfo.packageName);
					startActivity(i);
					TranslateAnimation anim = new TranslateAnimation(0, 0, 0, getWindowManager().getDefaultDisplay().getHeight());
					anim.setDuration(750);
					apps.setVisibility(View.GONE);
					apps.setAnimation(anim);
				}
			});
		
		td.addView(time);
		td.addView(date);
		td.addView(myName);
		widgets.addView(td);
		main.addView(apps);
		base.addView(widgets);
		base.addView(main);
		setContentView(base);
		load();
	}
	void load(){
		int color = Color.BLACK;
		try{
			color = Color.parseColor(sp.getString("color", "#ff000000"));
		}catch(Exception e){
			color = Color.DKGRAY;
		}
		time.setTextColor(color);
		date.setTextColor(color);
		myName.setTextColor(color);
		myName.setText(sp.getString("name", "RyannKim327"));
		new Handler().postDelayed(new Runnable(){
				@Override
				public void run() {
					load();
				}
			}, 500);
	}
	@Override
	public void onBackPressed() {
		if(apps.getVisibility() == View.GONE){
			TranslateAnimation anim = new TranslateAnimation(0, 0, getWindowManager().getDefaultDisplay().getHeight(), 0);
			anim.setDuration(750);
			apps.setVisibility(View.VISIBLE);
			apps.setAnimation(anim);
		}else{
			TranslateAnimation anim = new TranslateAnimation(0, 0, 0, getWindowManager().getDefaultDisplay().getHeight());
			anim.setDuration(750);
			apps.setVisibility(View.GONE);
			apps.setAnimation(anim);
		}
	}
}
