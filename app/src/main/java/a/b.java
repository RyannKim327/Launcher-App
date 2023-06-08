package a;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.widget.Toast;
import mpop.revii.launcher.R;

public class b extends PreferenceActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTheme(android.R.style.Theme_DeviceDefault_Light);
		Thread.setDefaultUncaughtExceptionHandler(new err(this));
		getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
		addPreferencesFromResource(R.xml.main);
		final Preference pref = findPreference("def");
		
		final Intent intent = new Intent();
		intent.setAction(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		if(!getPackageManager().resolveActivity(intent, 0).activityInfo.packageName.equalsIgnoreCase(getPackageName())){
			pref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener(){
					@Override
					public boolean onPreferenceClick(Preference p1) {
						intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(Intent.createChooser(intent, "Select Application", null));
						return false;
					}
				});
		}else{
			pref.setTitle("Open Main Activity");
			pref.setSummary("Activated already");
			pref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener(){
					@Override
					public boolean onPreferenceClick(Preference p1) {
						startActivity(new Intent(b.this, a.class));
						finish();
						return false;
					}
				});
		}
		
		/*final Intent in = new Intent(Intent.ACTION_MAIN);
		in.setAction(Intent.ACTION_MAIN);
		in.addCategory(Intent.CATEGORY_HOME);
		if(!getPackageManager().resolveActivity(in, 0).activityInfo.packageName.equalsIgnoreCase(getPackageName())){
			pref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener(){
					@Override
					public boolean onPreferenceClick(Preference p1) {
						Intent intent = new Intent();
						intent.setAction(Intent.ACTION_MAIN);
						intent.addCategory(Intent.CATEGORY_HOME);
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(Intent.createChooser(intent, "Choose", null));
						// startActivity(intent);
						return false;
					}
				});
		}else{
			pref.setTitle("Open Main Activity");
			pref.setSummary("Activated already");
			pref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener(){
					@Override
					public boolean onPreferenceClick(Preference p1) {
						startActivity(new Intent(b.this, a.class));
						finish();
						return false;
					}
				});
		}*/
		
		if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
			requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
		}
	}
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		switch(requestCode){
			case 0:
				if(grantResults.length > 0 & grantResults[0] == PackageManager.PERMISSION_GRANTED){
					Toast.makeText(b.this, "Thank you", 1).show();
				}
			break;
		}
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
	}

	@Override
	protected void onPause() {
		finish();
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		finish();
		super.onDestroy();
	}
}
