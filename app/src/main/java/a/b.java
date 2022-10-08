package a;

import android.preference.PreferenceActivity;
import android.os.Bundle;
import mpop.revii.launcher.R;
import android.preference.Preference;
import android.provider.Settings;
import android.content.Intent;

public class b extends PreferenceActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.main);
		final Preference pref = findPreference("def");
		pref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener(){
				@Override
				public boolean onPreferenceClick(Preference p1) {
					Intent intent = new Intent();
					intent.setAction(Intent.ACTION_MAIN);
					intent.addCategory(Intent.CATEGORY_HOME);
					if(!getPackageManager().resolveActivity(intent, 0).activityInfo.packageName.equalsIgnoreCase(getPackageName())){
						Intent in = new Intent();
						in.setAction(Intent.ACTION_MAIN);
						in.addCategory(Intent.CATEGORY_HOME);
						in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
						startActivity(Intent.createChooser(in, "Choose app:"));
						startActivity(in);
					}else{
						pref.setShouldDisableView(true);
					}
					return false;
				}
			});
	}
}
