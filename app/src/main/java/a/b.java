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
		setTheme(android.R.style.Theme_DeviceDefault_Light_DarkActionBar);
		Thread.setDefaultUncaughtExceptionHandler(new err(this));
		getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
		addPreferencesFromResource(R.xml.main);
		final Preference pref = findPreference("def");
		pref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener(){
				@Override
				public boolean onPreferenceClick(Preference p1) {
					Intent intent = new Intent();
					intent.setAction(Intent.ACTION_MAIN);
					intent.addCategory(Intent.CATEGORY_HOME);
					if(!getPackageManager().resolveActivity(intent, 0).activityInfo.packageName.equalsIgnoreCase(getPackageName())){
						intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
						//startActivity(Intent.createChooser(intent, "Choose app:"));
						startActivityForResult(Intent.createChooser(intent, "Choose app:"), 0);
					}else{
						pref.setShouldDisableView(true);
					}
					return false;
				}
			});
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch(requestCode){
			case 0:
				Toast.makeText(b.this, String.valueOf(resultCode), 1).show();
				if(data.getPackage().equals(getPackageName())){
					startActivity(new Intent(b.this, a.class));
				}
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
