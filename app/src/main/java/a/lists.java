package a;
import android.widget.ArrayAdapter;
import android.content.Context;
import mpop.revii.launcher.R;
import java.util.ArrayList;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.ImageView;
import android.net.Uri;
import android.graphics.drawable.Drawable;
import android.content.pm.ResolveInfo;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Handler;

public class lists extends ArrayAdapter<Object> {
	Context a;
	SharedPreferences sp;
	TextView appName, packageName;
	ImageView icon;
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
		ResolveInfo res = (ResolveInfo) getItem(position);
		reload();
		icon.setImageDrawable(res.loadIcon(a.getPackageManager()));
		appName.setText(res.loadLabel(a.getPackageManager()));
		packageName.setText(res.activityInfo.packageName);
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
