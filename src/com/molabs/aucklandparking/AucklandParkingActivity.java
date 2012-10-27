/* Author : Mohammed Basim
 * Site : mbasim.me
 * Date : 29/01/2012
 */

package com.molabs.aucklandparking;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class AucklandParkingActivity extends MapActivity implements
		LocationListener {
	private static final double AUCKNORTHLON = 174.77426290512085;
	private static final double AUCKNORTHLAT = -36.78749768272416;
	private static final double AUCKCBDLAT = -36.852634105359435;
	private static final double AUCKCBDLON = 174.76587295532227;
	private MapController mapController;
	private MapView mapView;
	private LocationManager locationManager;
	private GeoPoint currentPoint;
	private Location currentLocation = null;
	@SuppressWarnings("unused")
	private ParkingOverlay currPos;
	private String region = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Intent sender = getIntent();
		region = sender.getExtras().getString("Region");

		mapView = (MapView) findViewById(R.id.mapView);
		mapView.setBuiltInZoomControls(true);
		mapView.setSatellite(false);
		mapView.setStreetView(true);
		mapController = mapView.getController();
		mapController.setZoom(16);

		setLatestLocation();
		animateToCurrentLocation();
		// drawCurrPositionOverlay();
		renderParksOnMap();

	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	
	public void renderParksOnMap() {
		TypedArray wilsonParkInfo = null, wilsonParkGeo = null, tournamentParkInfo = null, tournamentParkGeo = null;
		Drawable wilsonMarker = null, tournamentMarker = null;
		
		wilsonMarker = getResources().getDrawable(R.drawable.wilsonmarker);
		tournamentMarker = getResources().getDrawable(R.drawable.tournamentmarker);

		// Create parks
		Park wilsonPark = new Park(wilsonParkInfo, wilsonParkGeo, wilsonMarker, mapView, "Wilson Parking");
		Park tournamentPark = new Park(tournamentParkInfo, tournamentParkGeo, tournamentMarker, mapView, "Tournament Parking");
		
		List<Overlay> overlayList = mapView.getOverlays();
		Resources res = getResources();

		if (region.equals("CBD")) {
			wilsonPark.setParkInformation(res.obtainTypedArray(R.array.wilsonParkInfo));
			wilsonPark.setParkGeoPosition(res.obtainTypedArray(R.array.wilsonParkGeo));
			
			tournamentPark.setParkInformation(res.obtainTypedArray(R.array.TournamentParkInfo));
			tournamentPark.setParkGeoPosition(res.obtainTypedArray(R.array.TournamentParkGeo));

			// adding wilson parks
			wilsonPark.buildParkingOverlay();
			// adding tournament parks
			tournamentPark.buildParkingOverlay();
			
			overlayList.add(wilsonPark.getParkOverlay());
			overlayList.add(tournamentPark.getParkOverlay());

		} else if (region.equals("NORTH")) {
			wilsonPark.setParkInformation(res.obtainTypedArray(R.array.wilsonParkNorthInfo));
			wilsonPark.setParkGeoPosition(res.obtainTypedArray(R.array.wilsonParkNorthGeo));

			wilsonPark.buildParkingOverlay();
			overlayList.add(wilsonPark.getParkOverlay());
		}

		if (currentLocation != null) {
			wilsonPark.getParkOverlay().setCurrentLocation(currentLocation);
		}

	}

	

	public void setLatestLocation() {
		String provider = getBestProvider();
		if (provider != null && this.locationManager != null) {
			currentLocation = this.locationManager
					.getLastKnownLocation(provider);
		}

		if (region.equals("CBD")) {
			setCurrentLocation(currentLocation, AUCKCBDLAT, AUCKCBDLON);
		} else if (region.equals("NORTH")) {
			setCurrentLocation(currentLocation, AUCKNORTHLAT, AUCKNORTHLON);
		}
		
		if (currentLocation != null) {
			if (region.equals("CBD")) {
				setCurrentLocation(currentLocation, AUCKCBDLAT, AUCKCBDLON);
			} else if (region.equals("NORTH")) {
				setCurrentLocation(currentLocation, AUCKNORTHLAT, AUCKNORTHLON);
			}
		} else {
			Toast.makeText(this, "Location not yet acquired", Toast.LENGTH_LONG)
					.show();
		}
	}

	public void animateToCurrentLocation() {
		if (currentPoint != null) {
			mapController.animateTo(currentPoint);
		}
	}

	public String getBestProvider() {
		String bestProvider = null;
		this.locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		criteria.setPowerRequirement(Criteria.NO_REQUIREMENT);
		criteria.setAccuracy(Criteria.NO_REQUIREMENT);
		if (this.locationManager != null) {
			bestProvider = this.locationManager.getBestProvider(criteria,true);
		}
		return bestProvider;
	}

	public void setCurrentLocation(Location location, double lattitude, double longitude) {
		Double lat = (lattitude * 1e6);
		Double lon = (longitude * 1e6);
		currentPoint = new GeoPoint(lat.intValue(), lon.intValue());
		currentLocation = new Location("");
		currentLocation.setLatitude(lattitude);
		currentLocation.setLongitude(longitude);
	}

	@Override
	public void onLocationChanged(Location location) {
		if (region.equals("CBD")) {
			setCurrentLocation(currentLocation, AUCKCBDLAT, AUCKCBDLON);
		} else if (region.equals("NORTH")) {
			setCurrentLocation(currentLocation, AUCKNORTHLAT, AUCKNORTHLON);
		}

	}

	@Override
	protected void onResume() {
		super.onResume();
		if (this.locationManager != null) {
			this.locationManager.requestLocationUpdates(getBestProvider(), 1000, 1,
					this);
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (this.locationManager != null) {
			this.locationManager.removeUpdates(this);
		}
	}

	@Override
	public void onProviderDisabled(String provider) {
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		
	}

}