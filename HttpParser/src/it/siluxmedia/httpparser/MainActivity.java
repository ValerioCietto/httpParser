package it.siluxmedia.httpparser;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.parser.Parser;
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



public class MainActivity extends Activity implements SensorEventListener {
	  private TextView textView;
	  public Parser parser;
	  

	  @Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_main);
	    textView = (TextView) findViewById(R.id.hello);
	    
	    String url= "https://www.facebook.com/";
        //Validate.isTrue(args.length == 1, "usage: supply url to fetch");
        //String url = args[0];
        /*

        Document doc = null;
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Elements links = doc.select("a[href]");
        print("\nLinks: (%d)", links.size());
        for (Element link : links) {
            print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 35));
        }
        
        */
	  }
	  
	  private static void print(String msg, Object... args) {
	        System.out.println(String.format(msg, args));
	    }

	    private static String trim(String s, int width) {
	        if (s.length() > width)
	            return s.substring(0, width-1) + ".";
	        else
	            return s;
	    }

	  private class DownloadWebPageTask extends AsyncTask<String, Void, String> {
	    @Override
	    protected String doInBackground(String... urls) {
	      String response = "";
	      for (String url : urls) {
	        DefaultHttpClient client = new DefaultHttpClient();
	        HttpGet httpGet = new HttpGet(url);
	        try {
	          HttpResponse execute = client.execute(httpGet);
	          InputStream content = execute.getEntity().getContent();

	          BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
	          int linestart = 38;
	          int lineend = 70;
	          int i=0;
	          String s = "";
	          while ((s = buffer.readLine()) != null && i<lineend) {
	        	  if(i>linestart){
	        		  
	        		  response += s;
	        	  }
	            
	            i++;
	          }
	          //response+="finished";
	          

	        } catch (Exception e) {
	          e.printStackTrace();
	        }
	      }
	      return response;
	    }

	    @Override
	    protected void onPostExecute(String result) {
	      textView.setText(result);
	    }
	  }

	  public void onClick(View view) {
	    DownloadWebPageTask task = new DownloadWebPageTask();
	    task.execute(new String[] { "http://m.gtt.to.it/m/it/arrivi.jsp?n=185" });

	  }

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	boolean moving = false;
    float lastX;
	float lastY;
	float lastZ;
	final float NOISE = (float)0.2;
	@Override
	public void onSensorChanged(SensorEvent p1) {
		// TODO Auto-generated method stub
		float x = p1.values[0];

		float y = p1.values[1];

		float z = p1.values[2];
		
		if(Math.abs(x-lastX)>NOISE){
			lastX = x;
			moving = true;
		}
		else{
			x=lastX;
			moving = false;
		}
		
		if(Math.abs(y-lastY)>NOISE){
			lastY = y;
			moving = true;
		}
		else{
			y=lastY;
			moving= false;
		}
		
		if(Math.abs(z-lastZ)>NOISE){
			lastZ = z;
			moving = true;
		}
		else{
			z=lastZ;
			moving = false;
		}
		
		if(Math.abs(x)>12){
			onSlash();
		}
		
		if(y<-12){
			onForward();
		}
		
	}

	private void onForward() {
		// TODO Auto-generated method stub
		
	}

	private void onSlash() {
		// TODO Auto-generated method stub
		onClick(null);
	}
	} 
