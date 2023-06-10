package a;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.widget.TextView;

public class Toast {
	public static int LENGTH_LONG = 1;
	public static int LENGTH_SHORT = 0;
	
	public static int STYLE_SUCCESS = Color.parseColor("#FFA6FFAB");
	
	public static android.widget.Toast makeText(Context ctx, String msg, int length, int style){
		android.widget.Toast a = new android.widget.Toast(ctx);
		TextView b = new TextView(ctx);
		float c = 5f;
		ShapeDrawable d = new ShapeDrawable(new RoundRectShape(new float[]{c, c, c, c, c, c, c, c}, null, null));
		
		d.getPaint().setColor(style);
		
		b.setPadding(10, 5, 10, 5);
		b.setBackgroundDrawable(d);
		b.setText(msg);
		b.setTextColor(Color.BLACK);
		
		a.setView(b);
		a.setDuration(length);
		a.show();
		
		return a;
	}
}
