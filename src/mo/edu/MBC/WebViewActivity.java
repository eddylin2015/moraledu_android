package mo.edu.MBC;

//import android.support.v7.app.ActionBarActivity;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
//import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
//import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class WebViewActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		url_history=new ArrayList<String>();
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_web_view);
        wv= (WebView)findViewById(R.id.webView1);

        WebSettings webSettings = wv.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setPluginState(PluginState.ON);
        webSettings.setAllowFileAccess(true);
        webSettings.setLoadWithOverviewMode(true);  
        
       
        wv.setWebViewClient(new WebViewClient(){
        	public boolean shouldOverrideUrlLoading(WebView view, String url) {
        		//Toast.makeText(getApplicationContext(), url.substring(url.length()-3), Toast.LENGTH_SHORT).show();
        		if(! url.contains("https://drive.google.com/viewerng/viewer?url=")&& url.substring(url.length()-3).contains("pdf"))
        		{
        			//url="https://drive.google.com/viewerng/viewer?url="+url;
        			Intent intent =new Intent(Intent.ACTION_VIEW,Uri.parse(url));
     			    startActivity(intent);
        			return true;
        		}
        		if(url.contains("http://macauchamson.appspot.com/static/MBC.apk")||
        				url.contains("https://www.facebook.com/")||
        				url.contains("http://goo.gl/"))
        		{
        			Intent intent =new Intent(Intent.ACTION_VIEW,Uri.parse(url));
     			    startActivity(intent);
        			return true;
        		}
                view.loadUrl(url);
                url_history.add(url);
              //  Toast.makeText(getApplicationContext(), ""+url_history.size(), Toast.LENGTH_SHORT).show();
                if(url_history.size()>30){ url_history.clear();}
                //Toast.makeText(getApplicationContext(), url, Toast.LENGTH_SHORT).show();
                return true;
            }
        	
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl)
                { // Handle the error
                    
                    if(app_host_url.equals("http://202.175.185.186/mbcapp/"))
                    {WebViewActivity.this.finish();
                    Toast.makeText(getApplicationContext(), "網絡連接失敗!", Toast.LENGTH_SHORT).show();
                    }
                    app_host_url="http://202.175.185.186/mbcapp/";
                    view.loadUrl(app_host_url);
                    
                }
        });
        wv.loadUrl(app_host_url);
        url_history.add(app_host_url);
        //Toast.makeText(getApplicationContext(), ""+url_history.size(), Toast.LENGTH_SHORT).show();
        //wv.loadUrl("http://192.168.115.1/mbcapp/index.html");
        wv.requestFocus();
	}
	//private String app_host_url="http://202.175.185.186/mbcapp/";
	private String app_host_url="http://macauchamson.appspot.com/";
	private WebView wv;
	private List<String> url_history=null;
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.web_view, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	  @Override
	  public boolean onKeyDown(int keyCode, KeyEvent event){
		  if(keyCode==KeyEvent.KEYCODE_BACK){
			  if(url_history.size()<1){
			        wv.loadUrl(app_host_url);
			        url_history.add(app_host_url);
			        return true;
			  }
			  /*if(url_history.get(url_history.size()-1).contains("/mbcapp/")){
				  return super.onKeyDown(keyCode, event);
			  }*/
			  if(url_history.get(url_history.size()-1).equals(app_host_url)){
				  return super.onKeyDown(keyCode, event);}
			  if(url_history.get(url_history.size()-1).equals("http://202.175.185.186/mbcapp/")){
			  return super.onKeyDown(keyCode, event);}
			  if(url_history.get(url_history.size()-1).equals("http://202.175.185.186/mbcapp/index.html")){
			  return super.onKeyDown(keyCode, event);}
			  if(url_history.get(url_history.size()-1).equals("http://202.175.185.186/mbcapp/index.php")){
			  return super.onKeyDown(keyCode, event);}

			  
			  if(url_history.size()>1){
				    url_history.remove(url_history.size()-1);	
				    //Toast.makeText(getApplicationContext(), ""+url_history.size(), Toast.LENGTH_SHORT).show();
			        wv.loadUrl(url_history.get(url_history.size()-1));
			        
			        return true;			  
			  }
			  
			  
			  return true;
		  }
		  return super.onKeyDown(keyCode, event);
	  }
	    
}
