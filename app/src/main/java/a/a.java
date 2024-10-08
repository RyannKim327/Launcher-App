package a;
import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextClock;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.graphics.Paint;
import java.util.Comparator;

public class a extends Activity {
	lists str;
	List<ResolveInfo> list;
	ListView apps;
	TextClock time, date;
	TextView myName, q, au;
	SharedPreferences sp;
	LinearLayout base;
	ArrayList<Object> lists;
    AI ai;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTheme(android.R.style.Theme_DeviceDefault_Wallpaper_NoTitleBar);
		
		Thread.setDefaultUncaughtExceptionHandler(new err(this));
		
		if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
			requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
		}
		
        RelativeLayout _ = new RelativeLayout(this);
		base = new LinearLayout(this);
		RelativeLayout main = new RelativeLayout(this);
		LinearLayout widgets = new LinearLayout(this);
		Intent intent = new Intent(Intent.ACTION_MAIN, (Uri) null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
		list = getPackageManager().queryIntentActivities(intent, 0);
		apps = new ListView(this);
		lists = new ArrayList<Object>();
		str = new lists(this, lists);
        ai = new AI(this);
		
		LinearLayout td = new LinearLayout(this);
        float[] r = {
            10, 10, 10, 10,
            10, 10, 10, 10
        };
        
        ShapeDrawable drawable = new ShapeDrawable(new RoundRectShape(r, null, null));
		time = new TextClock(this);
		date = new TextClock(this);
		myName = new TextView(this);
		q = new TextView(this);
		au = new TextView(this);
		
		sp = PreferenceManager.getDefaultSharedPreferences(this);
		String customQuotes = sp.getString("c_quotes", "");
		
        drawable.getPaint().setColor(Color.argb(0.3f, 0f, 0f, 0f));
        drawable.getPaint().setShadowLayer(3, 0, 0, Color.argb(0.3f, 0f, 0f, 0f));
        
        td.setBackground(drawable);
        td.setOrientation(LinearLayout.VERTICAL);
		td.setGravity(Gravity.CENTER);
        td.setPadding(13, 8, 13, 8);
		td.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		td.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View p1) {
					if(apps.getVisibility() == View.VISIBLE){
						hideMenu();
					}else{
						startActivity(new Intent(a.this, b.class));
					}
				}
			});
        
        _.setBackgroundDrawable(getWallpaper());
		
		time.setFormat12Hour("hh:mm:ss");
		time.setFormat24Hour("kk:mm:ss");
		time.setTextSize(30);
		time.setGravity(Gravity.CENTER);
		time.setTypeface(Typeface.createFromAsset(getAssets(), "digital-7.ttf"), Typeface.BOLD);
		
		date.setFormat12Hour("EEE dd, MMM, yyyy");
		date.setFormat24Hour("EEE dd, MMM, yyyy");
		date.setTextSize(15);
		date.setGravity(Gravity.CENTER);
		date.setTypeface(Typeface.createFromAsset(getAssets(), "digital-7.ttf"), Typeface.BOLD);
		
		myName.setText(greet(sp.getString("name", "RyannKim327")));
		myName.setTextSize(10);
		myName.setGravity(Gravity.CENTER);
        myName.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
		
		String data = sp.getString("QUOTES", "");
		
		if(customQuotes.equalsIgnoreCase("") || customQuotes.split("\n").length <= 0){
			try {
				JSONArray arr = new JSONArray(data);
				JSONObject obj = arr.getJSONObject(0);
				q.setText(obj.getString("q"));
				au.setText("~ " + obj.getString("a"));
			} catch (JSONException e) {}
		}else{
			String author = customQuotes.split("\n")[customQuotes.split("\n").length - 1];
			String quotes = "";
			for(int i = 0; i < customQuotes.split("\n").length - 1; i++){
				quotes += customQuotes.split("\n")[i] + "\n";
			}
			q.setText(quotes);
			au.setText("~ " + author);
		}
		
		q.setTextSize(12);
		q.setGravity(Gravity.FILL);
		q.setPadding(0, 5, 0, 0);
        q.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
		q.setLayoutParams(new LayoutParams(Math.round(getWindowManager().getDefaultDisplay().getWidth() * 0.7f), LayoutParams.WRAP_CONTENT));
		
		au.setTextSize(12);
		au.setGravity(Gravity.RIGHT);
		au.setPadding(0, 5, 0, 0);
        au.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
		au.setLayoutParams(new LayoutParams(Math.round(getWindowManager().getDefaultDisplay().getWidth() * 0.7f), LayoutParams.WRAP_CONTENT));
		
		base.setOrientation(LinearLayout.VERTICAL);
		base.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		
		widgets.setOrientation(LinearLayout.VERTICAL);
		widgets.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, Math.round(getWindowManager().getDefaultDisplay().getHeight() * 0.68f)));
		widgets.setGravity(Gravity.CENTER);
		
		main.setGravity(Gravity.BOTTOM | Gravity.CENTER);
		main.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		
		base.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View p1) {
				hideMenu();
			}
		});
		
		base.setOnTouchListener(new swiper(this){
			@Override
			public void onSwipeBottom() {
				if(apps.getVisibility() == View.VISIBLE){
					hideMenu();
				}
			}
			@Override
			public void onSwipeLeft() {
                showAi();
			}
			@Override
			public void onSwipeRight() {
                showAi();
			}
			@Override
			public void onSwipeTop(){
				showOnMenu();
			}
		});
        
        ai.setVisibility(View.GONE);
        ai.setBackgroundColor(Color.TRANSPARENT);
        ai.setLayoutParams(new RelativeLayout.LayoutParams(0, 0));
		
		td.addView(time);
		td.addView(date);
		td.addView(myName);
		td.addView(q);
		td.addView(au);
		widgets.addView(td);
		main.addView(apps);
        
		base.addView(widgets);
		base.addView(main);
        _.addView(base);
        _.addView(ai);
		setContentView(_);
		load();
		quotes();
		update();
	}
	void load(){
		int color = Color.BLACK;
		int size = 12;
		try{
			color = Color.parseColor(sp.getString("color", "#ff000000"));
		}catch(Exception e){
			color = Color.DKGRAY;
		}
		time.setTextColor(color);
		date.setTextColor(color);
		myName.setTextColor(color);
		q.setTextColor(color);
		au.setTextColor(color);
		myName.setText(greet(sp.getString("name", "RyannKim327")));
		
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
		}else{
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setTheme(android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen);
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
		shades();
	}
	void quotes(){
		http h = new http(this, sp, q, au);
		h.execute();
		new Handler().postDelayed(new Runnable(){
				@Override
				public void run() {
					quotes();
				}
		}, ((1000) * 60) * 60);
	}
	void update(){
		http3 h = new http3(this);
		h.execute(getPackageName());
		new Handler().postDelayed(new Runnable(){
				@Override
				public void run() {
					update();
				}
			}, ((1000 * 60) * 60));
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
			lists.add(list.get(app[i]));
		}
        /*
        for(int i = 0; i < list.size() - 1; i++){
            for(int j = 0; j < list.size() - 1; j++){
                int k = 0;
                String a = lists.get(i).toString();
                String b = lists.get(j).toString();
                while(a.charAt(k) == b.charAt(k)){
                    k++;
                }
                if(a.charAt(k) < b.charAt(k)){
                   Object x = lists.get(i);
                   lists.set(i, lists.get(j));
                   lists.set(j, x);
                }
            }
        }*/
        
		apps.setAdapter(str);
		apps.setOnItemClickListener(new OnItemClickListener(){
				@Override
				public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4) {
					int x = app[p3];
					Intent i = getPackageManager().getLaunchIntentForPackage(list.get(x).activityInfo.packageName);
					startActivity(i);
					hideMenu();
					finishAndRemoveTask();
				}
			});
			

		apps.setOnItemLongClickListener(new OnItemLongClickListener(){
				@Override
				public boolean onItemLongClick(AdapterView<?> p1, View p2, int p3, long p4) {
					final int x = app[p3];
					AlertDialog dialog = new AlertDialog(a.this);
					dialog.setTitle("App Details: ");
					dialog.setMessage("App name: " + list.get(x).activityInfo.loadLabel(getPackageManager()) + "\nPackage name: " + list.get(x).activityInfo.packageName);
					dialog.setIcon(list.get(x).activityInfo.loadIcon(getPackageManager()));
					dialog.setPositiveButton("Uninstall", new DialogInterface.OnClickListener(){
							@Override
							public void onClick(DialogInterface p1, int p2) {
								Intent i = new Intent(Intent.ACTION_DELETE);
								i.setData(Uri.parse("package:" + list.get(x).activityInfo.packageName));
								startActivity(i);
							}
						});
					dialog.setNegativeButton("App Info", new DialogInterface.OnClickListener(){
							@Override
							public void onClick(DialogInterface p1, int p2) {
								Intent i = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
								i.addCategory(Intent.CATEGORY_DEFAULT);
								i.setData(Uri.parse("package:" + list.get(x).activityInfo.packageName));
								startActivity(i);
							}
						});
					dialog.setNeutralButton("Cancel", null);
					dialog.setBackground(Color.parseColor("#ff333333"));
					dialog.display();
					hideMenu();
					return true;
				}
			});
	}
	void hideMenu(){
		TranslateAnimation anim = new TranslateAnimation(0, 0, 0, getWindowManager().getDefaultDisplay().getHeight());
		anim.setDuration(750);
		apps.setVisibility(View.GONE);
        apps.setLayoutParams(new RelativeLayout.LayoutParams(0, 0));
		apps.setAnimation(anim);
		base.setBackgroundColor(Color.TRANSPARENT);
	}
	String greet(String n){
		String str = "";
		Date date = new Date();
		int h = date.getHours();
		String name = "";
		for(int i = 0; i < 20 && i < n.length(); i++){
			name += n.charAt(i);
		}
		
		if(h >= 22 || h <= 4){
			str = "Goodnight and sweet dreams " + name;
		}else if(h > 4 && h < 10){
			str = "Good morning " + name;
		}else if(h >= 10 && h <= 14){
			str = "Let's eat " + name;
		}else if(h > 14 && h <= 16){
			str = "Good afternoon " + name;
		}else if(h > 16 && h <= 22){
			str = "Good evening " + name;
		}
		
		return str;
	}
	
	void shades(){
		int x = 0;
		int y = 0;
		int b = 0;
		int c = 0;
		
		String[] s = sp.getString("shadow", "#ff000000").split(" ");
		
		try{
			if(s.length == 4){
				x = Integer.parseInt(s[0]);
				y = Integer.parseInt(s[1]);
				b = Integer.parseInt(s[2]);
				c = checkColor(s[3]);
			}else if(s.length == 3){
				x = Integer.parseInt(s[0]);
				y = Integer.parseInt(s[0]);
				b = Integer.parseInt(s[1]);
				c = checkColor(s[2]);
			}else if(s.length == 2){
				x = Integer.parseInt(s[0]);
				y = Integer.parseInt(s[0]);
				b = Integer.parseInt(s[0]);
				c = checkColor(s[1]);
			}else{
				x = 0;
				y = 0;
				b = 3;
				c = Color.WHITE;
			}
		}catch(Exception e){
			x = 0;
			y = 0;
			b = 3;
			c = Color.WHITE;
		}
		
		time.setShadowLayer(b, x, y, c);
		date.setShadowLayer(b, x, y, c);
		myName.setShadowLayer(b, x, y, c);
		q.setShadowLayer(b, x, y, c);
		au.setShadowLayer(b, x, y, c);
	}
	int checkColor(String color){
		try{
			return Color.parseColor(color);
		}catch(Exception e){
			return Color.WHITE;
		}
	}
	void showOnMenu(){
		if(apps.getVisibility() == View.GONE){
			apps();
			TranslateAnimation anim = new TranslateAnimation(0, 0, getWindowManager().getDefaultDisplay().getHeight(), 0);
			anim.setDuration(750);
			apps.setVisibility(View.VISIBLE);
            apps.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
			apps.setAnimation(anim);
			base.setBackgroundColor(Color.parseColor("#90000000"));
		}else{
			hideMenu();
		}
	}
    void showAi(){
        if(ai.getVisibility() == View.GONE){
            ai.setVisibility(View.VISIBLE);
            ai.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        }else{
            ai.setVisibility(View.GONE);
            ai.setLayoutParams(new RelativeLayout.LayoutParams(0, 0));
        }
    }
	@Override
	public void onBackPressed() {
		// showOnMenu();
        showAi();
	}
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		switch(requestCode){
			case 0:
				if(grantResults.length > 0 & grantResults[0] == PackageManager.PERMISSION_GRANTED){
					Toast.makeText(this, "Thank you", Toast.LENGTH_LONG, Toast.STYLE_SUCCESS).show();
				}
			break;
		}
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
	}
}
