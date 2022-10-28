package a;
import android.content.Context;
import android.content.pm.PackageManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class utils {
	public static String url = "enter your website here";
	public static String readAsset(Context c , String file){
		StringBuffer str = new StringBuffer();
		try {
			Reader r = new BufferedReader(new  InputStreamReader(c.getAssets().open(file)));
			char[] ch = new char[1024];
			while(true){
				int b = r.read(ch, 0, ch.length);
				if(b <= 0){
					break;
				}
				str.append(ch, 0,ch.length);
			}
		} catch (IOException e) {}

		return String.valueOf(str);
	}
	public class JSON{
		JSONObject o;
		public final JSON(String txt){
			try {
				o = new JSONObject(txt);
			} catch (JSONException e) {}
		}
		public final Object getValue(String key){
			Object s = null;
			try {
				s = o.get(key);
			} catch (JSONException e) {}
			return s;
		}
		public final JSONArray getArr(String key){
			JSONArray s = null;
			try {
				s = o.getJSONArray(key);
			} catch (JSONException e) {}
			return s;
		}
	}
	public static String vName(Context ctx){
		String str = "";
		try {
			str = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0).versionName;
		} catch (PackageManager.NameNotFoundException e) {}
		return str;
	}
}
