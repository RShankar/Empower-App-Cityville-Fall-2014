package com.ade.cityville;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity implements AsyncTaskCompleteListener<String> {
	private String URL = "http://lamp.cse.fau.edu/~lwill137/cityvilleLogin.php";
	private EditText email;
	private EditText pass;
	private TextView result;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		email = (EditText) findViewById(R.id.loginEmail);
		pass = (EditText) findViewById(R.id.loginPass);
		result = (TextView) findViewById(R.id.LoginResult);
	}
	
	public void register(View view){
		Intent intent = new Intent(this, RegisterActivity.class);
		startActivity(intent);
	}
	public void login(View view){
		if(email.getText().length()>0 && pass.getText().length()>0){
			launchTask(URL);
		} else {
			result.setText("Fields Required!");
		}
	}
	@Override
	public void onTaskComplete(String asyncResult) {
		result.setText(asyncResult);
		if(asyncResult.contains("Welcome")){
			loginSuccessful();
		}
	}
    public void launchTask(String url) {
        A a = new A(getApplicationContext(), this, email.getText().toString(), pass.getText().toString());
        a.execute(url);
    }
	class A extends AsyncTask<String, Void, String> {
	    private AsyncTaskCompleteListener<String> callback;
	    String bodyHtml;
	    String Email;
	    String Pass;
	    public A(Context context, AsyncTaskCompleteListener<String> cb, String email, String pass) {
	        this.callback = cb;
	        Email = email;
	        Pass = pass;
	    }
		@Override
		protected String doInBackground(String... arg0) {
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost post = new HttpPost(URL);
			HttpResponse response;
			try {
				// Add your data
			    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			    nameValuePairs.add(new BasicNameValuePair("email", Email));
			    nameValuePairs.add(new BasicNameValuePair("password", Pass));
			    post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				response = httpClient.execute(post);
				bodyHtml = EntityUtils.toString(response.getEntity());
			} catch (IOException e) {
				e.printStackTrace();
			}
			return bodyHtml;
		}  
	    protected void onPostExecute(String result) {
	       System.out.println("on Post execute called");
	       callback.onTaskComplete(result);
	   }
	}
	private void loginSuccessful(){
		Toast.makeText(getApplicationContext(), "Your Logged In!", Toast.LENGTH_SHORT).show();

		AppData.loggedIn = true;
		Intent intent = new Intent(this, HomeActivity.class);
		startActivity(intent);
	}
}
