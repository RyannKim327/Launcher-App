package a;

import android.content.Intent;
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
		
		final Intent i = new Intent(Intent.ACTION_MAIN);
		i.setAction(Intent.ACTION_MAIN);
		i.addCategory(Intent.CATEGORY_HOME);
		if(!getPackageManager().resolveActivity(i, 0).activityInfo.packageName.equalsIgnoreCase(getPackageName())){
			pref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener(){
					@Override
					public boolean onPreferenceClick(Preference p1) {
						//Intent intent = new Intent(Intent.ACTION_MAIN);
						//intent.addCategory(Intent.CATEGORY_HOME);
						i.addCategory(Intent.CATEGORY_DEFAULT);
						i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
						startActivity(Intent.createChooser(i, "Choose your application:"));
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
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch(requestCode){
			// No action
			/*case 0:
				if(resultCode == RESULT_OK){
					if(data.getPackage().equals(getPackageName())){
						startActivity(new Intent(b.this, a.class));
					}
				}
			break;*/
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
