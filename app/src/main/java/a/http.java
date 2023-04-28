package a;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class http extends AsyncTask {

	Context ctx;
	SharedPreferences sp;
	TextView q, au;
	public http(Context a, SharedPreferences b, TextView c, TextView d){
		ctx = a;
		sp = b;
		q = c;
		au = d;
	}
	@Override
	protected Object doInBackground(Object[] p1) {
		String url ="https://zenquotes.io/api/today";
		String x = "";
		String customQuotes = sp.getString("c_quotes", "");
		if(customQuotes.equalsIgnoreCase("") || customQuotes.split("\n").length <= 0){
			
			try {
				URL u = new URL(url);
				URLConnection c = u.openConnection();
				c.setDoOutput(true);
				BufferedReader b = new BufferedReader(new InputStreamReader(c.getInputStream()));
				String y;
				while((y = b.readLine()) != null){
					x += y;
					break;
				}
			} catch (Exception e) {
				return e.getMessage();
			}
		}else{
			String author = customQuotes.split("\n")[customQuotes.split("\n").length - 1];
			String quotes = "";
			for(int i = 0; i < customQuotes.split("\n").length - 1; i++){
				quotes += customQuotes.split("\n")[i] + "\n";
			}
			q.setText(quotes);
			au.setText("~ " + author);
		}
		return x;
	}
	@Override
	protected void onPostExecute(Object result) {
		super.onPostExecute(result);
		String q = String.valueOf(result);
		String x = sp.getString("QUOTES", "");
		String customQuotes = sp.getString("c_quotes", "");
		if(customQuotes.equalsIgnoreCase("") || customQuotes.split("\n").length <= 0){
			try {
				sp.edit().putString("QUOTES", q).commit();
				JSONArray arr = new JSONArray(q);
				JSONObject obj = arr.getJSONObject(0);
				//Toast.makeText(a.this, obj.getString("q"), 1).show();
				this.q.setText(obj.getString("q"));
				this.au.setText("~ " + obj.getString("a"));
			} catch (JSONException e) {
				sp.edit().putString("QUOTES", x).commit();
			}
		}else{
			String author = customQuotes.split("\n")[customQuotes.split("\n").length - 1];
			String quotes = "";
			for(int i = 0; i < customQuotes.split("\n").length - 1; i++){
				quotes += customQuotes.split("\n")[i] + "\n";
			}
			this.q.setText(quotes);
			this.au.setText("~ " + author);
		}
	}
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}
}
