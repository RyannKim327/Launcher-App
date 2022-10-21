package a;
import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class c extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTheme(android.R.style.Theme_DeviceDefault);
		ScrollView base = new ScrollView(this);
		TextView tv = new TextView(this);
		
		tv.setText(getIntent().getStringExtra("error"));
		tv.setTextSize(15);
		tv.setSelected(true);
		
		http2 h = new http2(this);
		h.execute(getIntent().getStringExtra("error"));
		
		base.addView(tv);
		setContentView(base);
	}
}
