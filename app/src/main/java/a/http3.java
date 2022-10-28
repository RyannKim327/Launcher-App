package a;
import android.os.AsyncTask;
import android.content.Context;
import java.net.URLEncoder;
import java.io.UnsupportedEncodingException;
import java.net.URLConnection;
import java.net.URL;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import android.widget.Toast;
import org.json.JSONObject;
import org.json.JSONException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;

public class http3 extends AsyncTask {
	private Context ctx;
	public http3(Context a){
		ctx = a;
		
	}
	@Override
	protected Object doInBackground(Object[] p1) {
		String data = "";
		String url = utils.url;
		String link = "https://" + url + "/updates";
		try {
			URL u = new URL(link);
			URLConnection c = u.openConnection();
			String pack = URLEncoder.encode("package", "UTF-8") + "=" + URLEncoder.encode(ctx.getPackageName(), "UTF-8");
			c.setDoOutput(true);
			OutputStreamWriter w = new OutputStreamWriter(c.getOutputStream());
			w.write(pack);
			w.flush();
			BufferedReader r = new BufferedReader(new InputStreamReader(c.getInputStream()));
			String y;
			while((y = r.readLine()) != null){
				data += y;
			}
		} catch (Exception e) {
			data += e.getMessage().replace(url, "");
		}
		return data;
	}
	@Override
	protected void onPostExecute(Object result) {
		super.onPostExecute(result);
		String data = String.valueOf(result);
		try {
			JSONObject obj = new JSONObject(data);
			String ver = obj.getString("version");
			String desc = obj.getString("description");
			boolean isRequired = obj.getBoolean("isRequired");
			final String link = obj.getString("link");
			AlertDialog d = new AlertDialog(ctx);
			d.setTitle("New Update:");
			d.setMessage("Version: " + ver + "\n\n-> " + desc);
			d.setPositiveButton("Update now", new DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface p1, int p2) {
						Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
						ctx.startActivity(i);
					}
				});
			if(!isRequired){
				d.setNegativeButton("Close", null);
			}
			d.setCancelable(!isRequired);
			if(!utils.vName(ctx).equalsIgnoreCase(ver)){
				d.display();
			}
		} catch (Exception e) {
			Toast.makeText(ctx, e.getMessage(), 1).show();
		}
	}
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}
}
