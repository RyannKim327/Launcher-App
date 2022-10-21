package a;

import android.content.Context;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.io.PrintWriter;
import android.os.Build;
import android.content.Intent;
import android.os.Process;

public class err implements UncaughtExceptionHandler {
	Context a;
	public err(Context ctx){
		a = ctx;
	}
	@Override
	public void uncaughtException(Thread p1, Throwable p2) {
		Writer w = new StringWriter();
		p2.printStackTrace(new PrintWriter(w));
		String s = "Thank you for using the app, for now the app has an error, kindly send this text to the developer, or just open your internet connection (With data) to send this error to the developer (server) and fix it as soon as posible. Thank you for your cooperation.\n";
		s += "\nError:\n\n";
		s += String.valueOf(w) + "\n";
		s += "\n\n-- Device Specifications --\n\n";
		s += add("Device",Build.DEVICE);
		s += add("Version", Build.VERSION.SDK);
		s += add("Display", Build.DISPLAY);
		s += add("Bootloader", Build.BOOTLOADER);
		
		try{
			Intent i = new Intent(a, c.class);
			i.putExtra("error", s);
			i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			a.startActivity(i);
			Process.killProcess(Process.myPid());
			System.exit(10);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	private String add(String n, String f){
		return n + ": " + f + "\n";
	}
}
