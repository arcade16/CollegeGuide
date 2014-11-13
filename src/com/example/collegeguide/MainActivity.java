package com.example.collegeguide;

import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import android.os.Build;

import com.example.collegeguide.R;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.CameraUpdateFactory;

public class MainActivity extends ActionBarActivity 
{
	
	//Vars for tab widget
	private TabHost tabs;
	private WebView collegeSites;
	private Spinner pullDown;
	private GoogleMap greaterBostonArea = null;
	
	@Override
	protected void onCreate(Bundle bundleVar) 
	{
		super.onCreate(bundleVar);
		setContentView(R.layout.activity_main);
		
		tabs = (TabHost) findViewById(R.id.tabhost);
		tabs.setup();
		TabHost.TabSpec populate;

		collegeSites = (WebView) findViewById(R.id.collegePage);
		
		//populate tabs
		populate = tabs.newTabSpec("tag1");
		populate.setContent(R.id.collegeMapTab);
		populate.setIndicator("College Map");
		tabs.addTab(populate);

		populate = tabs.newTabSpec("tag2");
		populate.setContent(R.id.tab2);
		populate.setIndicator("Item Site");
		tabs.addTab(populate);

		populate = tabs.newTabSpec("tag3");
		populate.setContent(R.id.tab3);
		populate.setIndicator("Sightseeing");
		tabs.addTab(populate);
		
		//Setup map
		pullDown = (Spinner) findViewById(R.id.sightseeing);
		greaterBostonArea = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment)).getMap();
		
		greaterBostonArea.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		
		greaterBostonArea.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(42.335, -71.168), 11));

		addMarkers(greaterBostonArea);

		collegeSites.setWebViewClient(new WebViewClient() 
		{
			public boolean shouldOverrideUrlLoading(WebView view, String url) 
			{
				view.loadUrl(url);
				return true;
			}
		});

		pullDown.setOnItemSelectedListener(new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,int position, long id) 
		{
			
			String pullDownSelection = parent.getItemAtPosition(position).toString();

			if (pullDownSelection.equals("Back Bay")) {
				
				tabs.setCurrentTab(1);
				collegeSites.loadUrl("http://www.cityofboston.gov/neighborhoods/backbay.asp");
				
			} else if (pullDownSelection.equals("Freedom Trail")) {
				
				tabs.setCurrentTab(1);
				collegeSites.loadUrl("http://www.greaterBostonAreaducktours.com/");
				
			} else if (pullDownSelection.equals("USS Constitution")) {
				
				tabs.setCurrentTab(1);
				collegeSites.loadUrl("http://www.cityofboston.gov/freedomtrail/ussconstitution.asp");
				
			} else if (pullDownSelection.equals("Skyzone")) {
				
				tabs.setCurrentTab(1);
				collegeSites.loadUrl("http://www.skyzone.com/Boston");
				
			} else if (pullDownSelection.equals("Union Oyster House")) {
				
				tabs.setCurrentTab(1);
				collegeSites.loadUrl("http://www.unionoysterhouse.com/Pages/history.html");
				
			}

		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {

		}

	});

		greaterBostonArea.setOnMarkerClickListener(new OnMarkerClickListener() {

			public boolean onMarkerClick(Marker m) {

				String title = m.getTitle();
				String snip = m.getSnippet();

				Toast.makeText(getApplicationContext(), title + "\n" + snip,
						Toast.LENGTH_SHORT).show();

				if (title.equals("Harvard University")) 
				{
					
					tabs.setCurrentTab(1);
					collegeSites.loadUrl("http://www.harvard.edu/");
					
				} else if (title.equals("Suffolk University")) {

					tabs.setCurrentTab(1);
					collegeSites.loadUrl("http://www.suffolk.edu/");
					
				} else if (title.equals("Boston College")) {

					tabs.setCurrentTab(1);
					collegeSites.loadUrl("http://www.bc.edu/");
					
				} else if (title.equals("Bentley University")) {

					tabs.setCurrentTab(1);
					collegeSites.loadUrl("http://www.bentley.edu/");
					
				}

				return true;
		}});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		
		int id = item.getItemId();
		if (id == R.id.action_settings) 
		{
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public boolean onKeyDown(int keyCode, KeyEvent someEvent) 
	{
		if ((keyCode == KeyEvent.KEYCODE_BACK) && collegeSites.canGoBack()) 
		{
			
			collegeSites.goBack();
			return true;
			
		}
		return super.onKeyDown(keyCode, someEvent);
	}
	

	public static class PlacetabsFragment extends Fragment 
	{

		public PlacetabsFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundleVar) 
		{
			
			View view = inflater.inflate(R.layout.fragment_main, viewGroup, false);
			return view;
			
		}
	}

	public void addMarkers(GoogleMap greaterBostonArea) 
	{

		greaterBostonArea.addMarker(new MarkerOptions()
				.position(new LatLng(42.3770029, -71.116660))
				.title("Harvard University").snippet("Acceptance rate of 5.9% , need we say more?")
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));

		greaterBostonArea.addMarker(new MarkerOptions()
				.position(new LatLng(42.3589891, -71.0618763))
				.title("Suffolk University").snippet("Eigth largest unersity in Metro Boston.")
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));

		greaterBostonArea.addMarker(new MarkerOptions()
				.position(new LatLng(42.3355488, -71.1684945))
				.title("Boston College").snippet("A private Jesuit research university.")
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));

		greaterBostonArea.addMarker(new MarkerOptions()
				.position(new LatLng(42.3856989, -71.22168))
				.title("Bentley University").snippet("Don't go to Seasons.")
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));

	}

}
