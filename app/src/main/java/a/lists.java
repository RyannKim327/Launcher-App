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

public class lists extends ArrayAdapter<Object> {
	Context a;
	public lists(Context ctx, ArrayList<Object> list){
		super(ctx,R.layout.layout_lists, list);
		a = ctx;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		convertView = LayoutInflater.from(a).inflate(R.layout.layout_lists, parent, false);
		ImageView icon = convertView.findViewById(R.id.appIcon);
		TextView appName = convertView.findViewById(R.id.appName);
		TextView packageName = convertView.findViewById(R.id.packageName);
		ResolveInfo res = (ResolveInfo) getItem(position);
		icon.setImageDrawable(res.loadIcon(a.getPackageManager()));
		appName.setText(res.loadLabel(a.getPackageManager()));
		packageName.setText(res.activityInfo.packageName);
		return convertView;
	}
}
