package a;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import java.util.ArrayList;
import mpop.revii.launcher.R;

public class lists extends ArrayAdapter<Object> {
	Context a;
	SharedPreferences sp;
	TextView appName, packageName;
	ImageView icon, menu;
	public lists(Context ctx, ArrayList<Object> list){
		super(ctx,R.layout.layout_lists, list);
		a = ctx;
		sp = PreferenceManager.getDefaultSharedPreferences(ctx);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		convertView = LayoutInflater.from(a).inflate(R.layout.layout_lists, parent, false);
		icon = convertView.findViewById(R.id.appIcon);
		appName = convertView.findViewById(R.id.appName);
		packageName = convertView.findViewById(R.id.packageName);
		menu = convertView.findViewById(R.id.list_menu);
		final ResolveInfo res = (ResolveInfo) getItem(position);
		reload();
		icon.setImageDrawable(res.loadIcon(a.getPackageManager()));
		appName.setText(res.loadLabel(a.getPackageManager()));
		packageName.setText(res.activityInfo.packageName);
		menu.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View p1) {
					PopupMenu m = new PopupMenu(a, p1);
					m.inflate(R.menu.app_menu);
					m.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){
							@Override
							public boolean onMenuItemClick(MenuItem p1) {
								switch(p1.getItemId()){
									case R.id.app_info:
										Intent i = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
										i.addCategory(Intent.CATEGORY_DEFAULT);
										i.setData(Uri.parse("package:" + res.activityInfo.packageName));
										a.startActivity(i);
									break;
									case R.id.uninstall:
										Intent i2 = new Intent(Intent.ACTION_DELETE);
										i2.setData(Uri.parse("package:" + res.activityInfo.packageName));
										a.startActivity(i2);
									break;
								}
								return false;
							}
						});
						m.show();
				}
			});
		return convertView;
	}
	
	public void reload(){
		int size;

		try{
			size = Integer.parseInt(sp.getString("size", "12"));
			if(size <= 10 || size > 25){
				size = 12;
				sp.edit().putString("size", "12").commit();
			}
		}catch(Exception e){
			size = 12;
		}

		appName.setTextSize(size);
		packageName.setTextSize(appName.getTextSize() * 0.7f);
		new Handler().postDelayed(new Runnable(){
				@Override
				public void run() {
					reload();
				}
			}, 1500);
	}
}
