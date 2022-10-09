package a;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;

public class AlertDialog extends android.app.AlertDialog.Builder{
	private static Context context;
	private ShapeDrawable body;
	
	public static int THEME_DARK = 4;
	public static int THEME_LIGHT = 5;
	
	public AlertDialog(Context ctx){
		super(ctx);
		context = ctx;
		float radii = 15f;
		RoundRectShape shape = new RoundRectShape(new float[]{
			radii, radii, radii, radii,
			radii, radii, radii, radii
		}, null, null);
		body = new ShapeDrawable(shape);
		body.getPaint().setColor(Color.parseColor("#ff333333"));
	}
	
	public AlertDialog(Context ctx, int theme){
		super(ctx, theme);
		context = ctx;
		float radii = 15f;
		RoundRectShape shape = new RoundRectShape(new float[]{
			radii, radii, radii, radii,
			radii, radii, radii, radii
		}, null, null);
		body = new ShapeDrawable(shape);
	}
	
	public void display(){
		android.app.AlertDialog dialog = create();
		dialog.getWindow().setBackgroundDrawable(body);
		dialog.show();
	}
	
	public void setBackground(int color){
		try{
			body.getPaint().setColor(color);
		}catch(Exception e){
			body.getPaint().setColor(Color.parseColor("#ff333333"));
		}
	}
}
