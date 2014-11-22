package  com.ade.cityville;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.content.Intent;

public class ThemesActivity extends ActionBarActivity {
	private Button android;
	private Button iphone;
	private Button codeRed;
	private Button babyBlue;
	private Button button;  //user's own theme
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_themes);
	        
	        android = (Button)findViewById(R.id.android);
	        iphone = (Button)findViewById(R.id.iphone);
	        codeRed = (Button)findViewById(R.id.code_red);
	        babyBlue = (Button)findViewById(R.id.baby_blue);
	        button = (Button)findViewById(R.id.button);
	    }
	 @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        // Inflate the menu; this adds items to the action bar if it is present.
	        getMenuInflater().inflate(R.menu.settings, menu);
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
	    
	    public void onClickAndroid(){
	    	


	    	
	    }
	    
	    public void onClickiPhone(){
	    	
	    	
	    	
	    	
	    }
	    
	    public void onClickCodeRed(){
	    	
	    	
	    	
	    	
	    }
	    
	    public void onClickBabyBlue(){
	    	
	    	
	    	
	    	
	    	
	    }
	    
	    public void onClickButton(){
	    	
	    	
	    	
	    	
	    	
	    }
	
}
