package a;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class d extends Activity {
	@Override
	public void onCreate(Bundle bundle){
		super.onCreate(bundle);
		Thread.setDefaultUncaughtExceptionHandler(new err(this));
		new Handler().postDelayed(new Runnable(){
				@Override
				public void run() {
					startActivity(new Intent(d.this, b.class));
					finish();
				}
			}, 1000);
	}
}
