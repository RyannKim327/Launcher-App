package a;
import android.content.Context;
import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import android.widget.Toast;
import java.net.URLEncoder;
import java.io.OutputStreamWriter;

public class http2 extends AsyncTask {
	Context a;
	public http2(Context ctx){
		a = ctx;
	}
	@Override
	protected Object doInBackground(Object[] p1) {
		String link2 = "Your website here";
		String url = "https://" + link2 + "/feed";
		String data = String.valueOf(p1[0]);
		String x = "";
		try {
			URL link = new URL(url);
			URLConnection c = link.openConnection();
			String s = URLEncoder.encode("error", "UTF-8") + "=" + URLEncoder.encode(data, "UTF-8");
			c.setDoOutput(true);
			OutputStreamWriter writer = new OutputStreamWriter(c.getOutputStream());
			writer.write(s);
			writer.flush();
			BufferedReader r = new BufferedReader(new InputStreamReader(c.getInputStream()));
			String y;
			while((y = r.readLine()) != null){
				x += y;
				break;
			}
		} catch (Exception e) {
			x += e.getMessage().replace(link2, "");
		}
		return x;
	}
	@Override
	protected void onPostExecute(Object result) {
		super.onPostExecute(result);
		Toast.makeText(a, String.valueOf(result), 1).show();
	}
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}
}
