package a;
import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TextView;

public class c extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Thread.setDefaultUncaughtExceptionHandler(new err(this));
		setTheme(android.R.style.Theme_DeviceDefault_Light);
		ScrollView base = new ScrollView(this);
		TextView tv = new TextView(this);
		
		tv.setText(getIntent().getStringExtra("error"));
		tv.setTextSize(15);
		tv.setTypeface(Typeface.SERIF, Typeface.BOLD_ITALIC);
		tv.setSelected(true);
		
		http2 h = new http2(this);
		h.execute(getIntent().getStringExtra("error"));
		
		base.addView(tv);
		setContentView(base);
	}
}
