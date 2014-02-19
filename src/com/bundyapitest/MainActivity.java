package com.bundyapitest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.xmlpull.v1.XmlPullParserException;

import android.app.ListActivity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bundyapitest.model.ChurchGroup;
import com.bundyapitest.model.ChurchGroupXmlParser;

public class MainActivity extends ListActivity  {
	List<ChurchGroup> groups;

	static final String CHURCH_URL = "https://bundoorapresbyterian.ccbchurch.com/api.php?srv=group_search";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
	}

	private void refreshList() {
		if(groups.size() > 0){
		ArrayAdapter<ChurchGroup> adapter = new ArrayAdapter<ChurchGroup>(this, android.R.layout.simple_list_item_1, groups);
		setListAdapter(adapter);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void onButtonClicked(View view) {
		switch (view.getId()) {
		case R.id.btLoad:

			ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
			if (networkInfo != null && networkInfo.isConnected()) {
				new DownLoadPageTask().execute(CHURCH_URL);
			} else {
				Toast.makeText(this, "Network is not connected",
						Toast.LENGTH_LONG);
			}
			
			
			break;

		default:
			break;
		}
	}

	private class DownLoadPageTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... urls) {

			String searchURL = urls[0];
			try {
				return getOnlineContent(searchURL);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "Unable to retrieve web page";

		}
		
		@Override
		protected void onPostExecute(String result) {
			refreshList();
		}

		private String getOnlineContent(String myUrl) throws IOException, URISyntaxException {
			URL url = new URL(myUrl);
			InputStream is = null;
			ChurchGroupXmlParser parser = new ChurchGroupXmlParser();

			try {
				
				HttpClient httpClient = new DefaultHttpClient();
				HttpGet httpGet = new HttpGet();
				URI uri = new URI(myUrl);
				httpGet.setURI(uri);
				httpGet.addHeader(BasicScheme.authenticate(
						new UsernamePasswordCredentials("liucescs", "qazscsace123"), HTTP.UTF_8, false));
				HttpResponse httpResponse = httpClient.execute(httpGet);
				is = httpResponse.getEntity().getContent();
				
				
				groups = parser.parse(is);
				is.close();
				return null;

			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (is != null) {
					is.close();
				}
			}
			return "nonsense";
		}

		private String readIt(InputStream stream) throws IOException {
			BufferedReader reader = null;
			reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
			StringBuffer data = new StringBuffer();
			String l = "";
			String nl = System.getProperty("line.separator");

			while ((l = reader.readLine()) != null) {
				data.append(l + nl);
			}
			return data.toString();
		}

	}

	
}
