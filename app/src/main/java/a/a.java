package a;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
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
import android.view.View.OnTouchListener;
import android.view.MotionEvent;
import android.widget.Toast;
import android.widget.ScrollView;
import android.content.DialogInterface;
import android.icu.util.GregorianCalendar;
import java.util.Date;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.content.Context;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.graphics.Typeface;
import java.io.File;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Adapter;
import android.provider.Settings;

public class a extends Activity {
	ArrayAdapter<String>  str;
	List<ResolveInfo> list;
	ListView apps;
	TextClock time, date;
	TextView myName, q, au;
	SharedPreferences sp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTheme(android.R.style.Theme_DeviceDefault_Wallpaper_NoTitleBar);
		
		Thread.setDefaultUncaughtExceptionHandler(new err(this));
		
		LinearLayout base = new LinearLayout(this);
		RelativeLayout main = new RelativeLayout(this);
		LinearLayout widgets = new LinearLayout(this);
		Intent intent = new Intent(Intent.ACTION_MAIN, (Uri) null);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		list = getPackageManager().queryIntentActivities(intent, 0);
		apps = new ListView(this);
		str = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
		
		LinearLayout td = new LinearLayout(this);
		time = new TextClock(this);
		date = new TextClock(this);
		myName = new TextView(this);
		q = new TextView(this);
		au = new TextView(this);
		
		GradientDrawable draw = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]{Color.parseColor("#10333333"), Color.parseColor("#ff333333")});
		sp = PreferenceManager.getDefaultSharedPreferences(this);
		
		td.setOrientation(LinearLayout.VERTICAL);
		td.setGravity(Gravity.CENTER);
		td.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		td.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View p1) {
					startActivity(new Intent(a.this, b.class));
				}
			});
		
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
		//myName.setTypeface(Typeface.createFromFile(new File("/storage/emulated/0/a.otf")));
		
		String data = sp.getString("QUOTES", "");
		
		try {
			JSONArray arr = new JSONArray(data);
			JSONObject obj = arr.getJSONObject(0);
			//Toast.makeText(a.this, obj.getString("q"), 1).show();
			q.setText(obj.getString("q"));
			au.setText("~ " + obj.getString("a"));
		} catch (JSONException e) {}
		
		q.setTextSize(12);
		q.setGravity(Gravity.LEFT);
		q.setPadding(0, 5, 0, 0);
		q.setLayoutParams(new LayoutParams(Math.round(getWindowManager().getDefaultDisplay().getWidth() * 0.7f), LayoutParams.WRAP_CONTENT));
		
		au.setTextSize(12);
		au.setGravity(Gravity.RIGHT);
		au.setPadding(0, 5, 0, 0);
		au.setLayoutParams(new LayoutParams(Math.round(getWindowManager().getDefaultDisplay().getWidth() * 0.7f), LayoutParams.WRAP_CONTENT));
		
		//base.setBackground(getWallpaper());
		base.setOrientation(LinearLayout.VERTICAL);
		base.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		
		widgets.setOrientation(LinearLayout.VERTICAL);
		widgets.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, Math.round(getWindowManager().getDefaultDisplay().getHeight() * 0.68f)));
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
				int k = 0;
				a = app_[i].toLowerCase().charAt(k);
				b = app_[j].toLowerCase().charAt(k);
				while(a == b && (k < app_[i].length() - 1 && k < app_[j].length() - 1)){
					k++;
					a = app_[i].toLowerCase().charAt(k);
					b = app_[j].toLowerCase().charAt(k);
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
		}
		
		for(int i = 0; i < app_.length; i++){
			if(i < app_.length - 1 && i > 0){
				if(app_[i].equals(app_[i + 1]) || app_[i - 1].equals(app_[i])){
					String pack = list.get(app[i]).activityInfo.packageName;
					str.add(app_[i].toString() + " (" + pack + ")");
				}else{
					str.add(app_[i].toString());
				}
			}
			if(i == 0){
				if(app_[i].equals(app_[i + 1])){
					String pack = list.get(app[i]).activityInfo.packageName;
					str.add(app_[i].toString() + " (" + pack + ")");
				}else{
					str.add(app_[i].toString());
				}
			}
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
		
		apps.setOnItemLongClickListener(new OnItemLongClickListener(){
				@Override
				public boolean onItemLongClick(AdapterView<?> p1, View p2, int p3, long p4) {
					int x = app[p3];
					Intent i = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
					i.addCategory(Intent.CATEGORY_DEFAULT);
					i.setData(Uri.parse("package:" + list.get(x).activityInfo.packageName));
					startActivity(i);
					return false;
				}
			});
		
		td.addView(time);
		td.addView(date);
		td.addView(myName);
		td.addView(q);
		td.addView(au);
		widgets.addView(td);
		main.addView(apps);
		base.addView(widgets);
		base.addView(main);
		setContentView(base);
		load();
		quotes();
	}
	void load(){
		int color = Color.BLACK;
		int shadow = Color.WHITE;
		int size = 12;
		try{
			color = Color.parseColor(sp.getString("color", "#ff000000"));
		}catch(Exception e){
			color = Color.DKGRAY;
		}
		try{
			shadow = Color.parseColor(sp.getString("shadow", "#ff000000"));
		}catch(Exception e){
			shadow = Color.WHITE;
		}
		time.setTextColor(color);
		time.setShadowLayer(3, 3, 3, shadow);
		date.setTextColor(color);
		date.setShadowLayer(3, 3, 3, shadow);
		myName.setTextColor(color);
		myName.setShadowLayer(3, 3, 3, shadow);
		q.setTextColor(color);
		q.setShadowLayer(3, 3, 3, shadow);
		au.setTextColor(color);
		au.setShadowLayer(3, 3, 3, shadow);
		myName.setText(sp.getString("name", "RyannKim327"));
		
		try{
			size = Integer.parseInt(sp.getString("size", "12"));
			if(size <= 10 || size > 25){
				size = 12;
				sp.edit().putString("size", "12").commit();
			}
		}catch(Exception e){
			size = 12;
		}
		
		if(sp.getBoolean("sb", false)){
			getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
			getWindow().setStatusBarColor(Color.TRANSPARENT);
			//getWindow().setStatusBarContrastEnforced(true);
		}else{
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		}
		
		myName.setTextSize(size);
		date.setTextSize(size * 1.5f);
		time.setTextSize(size * 2);
		q.setTextSize(size);
		au.setTextSize(size);
		
		new Handler().postDelayed(new Runnable(){
				@Override
				public void run() {
					load();
				}
			}, 500);
	}
	void quotes(){
		Date date = new Date();
		int hours = date.getHours();
		int mins = date.getMinutes();
		int sec = date.getSeconds();
		http h = new http(this, sp, q, au);
		h.execute();
		new Handler().postDelayed(new Runnable(){
				@Override
				public void run() {
					quotes();
				}
		}, ((1000) * 60) * 60);
	}
	void apps(){
		Intent intent = new Intent(Intent.ACTION_MAIN, (Uri) null);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		list = getPackageManager().queryIntentActivities(intent, 0);
		
		str.clear();
		str.notifyDataSetChanged();
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
				int k = 0;
				a = app_[i].toLowerCase().charAt(k);
				b = app_[j].toLowerCase().charAt(k);
				while(a == b && (k < app_[i].length() - 1 && k < app_[j].length() - 1)){
					k++;
					a = app_[i].toLowerCase().charAt(k);
					b = app_[j].toLowerCase().charAt(k);
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
		}

		for(int i = 0; i < app_.length; i++){
			if(i < app_.length - 1 && i > 0){
				if(app_[i].equals(app_[i + 1]) || app_[i - 1].equals(app_[i])){
					String pack = list.get(app[i]).activityInfo.packageName;
					str.add(app_[i].toString() + " (" + pack + ")");
				}else{
					str.add(app_[i].toString());
				}
			}
			if(i == 0){
				if(app_[i].equals(app_[i + 1])){
					String pack = list.get(app[i]).activityInfo.packageName;
					str.add(app_[i].toString() + " (" + pack + ")");
				}else{
					str.add(app_[i].toString());
				}
			}
		}
		apps.setAdapter(str);
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

		apps.setOnItemLongClickListener(new OnItemLongClickListener(){
				@Override
				public boolean onItemLongClick(AdapterView<?> p1, View p2, int p3, long p4) {
					int x = app[p3];
					Intent i = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
					i.addCategory(Intent.CATEGORY_DEFAULT);
					i.setData(Uri.parse("package:" + list.get(x).activityInfo.packageName));
					startActivity(i);
					return false;
				}
			});
	}
	@Override
	public void onBackPressed() {
		if(apps.getVisibility() == View.GONE){
			apps();
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
