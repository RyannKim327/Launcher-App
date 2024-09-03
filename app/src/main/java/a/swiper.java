package a;
import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public abstract class swiper implements OnTouchListener {
	private GestureDetector ges;
	public swiper(Context ctx){
		ges = new GestureDetector(ctx, new gesture());
	}
	
	@Override
	public boolean onTouch(View p1, MotionEvent p2) {
		return ges.onTouchEvent(p2);
	}
	private final class gesture extends GestureDetector.SimpleOnGestureListener{
		private static final int SWIPE_T = 75;
		private static final int SWIPE_V = 100;

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			float x = e2.getX() - e1.getX();
			float y = e2.getY() - e1.getY();
			if(Math.abs(x) > Math.abs(y)){
				if(Math.abs(x) > SWIPE_T && Math.abs(velocityX) > SWIPE_V){
					if(x > 0){
						onSwipeRight();
					}else{
						onSwipeLeft();
					}
					return true;
				}
			}else{
				if(Math.abs(y) > SWIPE_T && Math.abs(velocityY) > SWIPE_V){
					if(y > 0){
						onSwipeBottom();
					}else{
						onSwipeTop();
					}
					return true;
				}
			}
			return false;
		}
	}
	public abstract void onSwipeBottom();
	public abstract void onSwipeLeft();
	public abstract void onSwipeRight();
	public abstract void onSwipeTop();
}

