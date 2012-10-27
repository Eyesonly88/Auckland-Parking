/* Author : Mohammed Basim
 * Site : mbasim.me
 * Date : 29/01/2012
 */

package com.molabs.aucklandparking;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
import com.readystatesoftware.mapviewballoons.BalloonItemizedOverlay;

public class ParkingOverlay extends BalloonItemizedOverlay<OverlayItem>{

	private Context mContext;
	private ArrayList<OverlayItem> parks = new ArrayList<OverlayItem>();
	@SuppressWarnings("unused")
	private Location currentLocation = null;


	public ParkingOverlay(Drawable defaultMarker, MapView mapView) {
		super(defaultMarker, mapView);
		boundCenter(defaultMarker);
		mContext = mapView.getContext();
	}

	@Override
	protected OverlayItem createItem(int i) {
		return parks.get(i);
	}

	@Override
	public int size() {
		return parks.size();
	}

	public void addOverlay(OverlayItem overlay) {
		parks.add(overlay);
		populate();
	}

	@Override
	protected boolean onBalloonTap(int index, OverlayItem item) {
		/*
		String tmp = parks.get(index).getTitle();
	    GeoPoint mallPoint = parks.get(index).getPoint();
	    Location tmpLoc = convertGpToLoc(mallPoint);
	    double distance = ((currentLocation).distanceTo(tmpLoc))*(0.000621371192);
	    DecimalFormat df = new DecimalFormat("#.##");
	    tmp = tmp + " is " + String.valueOf(df.format(distance)) + " miles away.";
	    */
	    Toast.makeText(mContext,"more features coming soon!",Toast.LENGTH_LONG).show();
	    return true;
	}

	public void setCurrentLocation(Location loc) {
		this.currentLocation = loc;
	}
//	public Location convertGpToLoc(GeoPoint gp){
//	    Location convertedLocation = new Location("");
//	    convertedLocation.setLatitude(gp.getLatitudeE6() / 1e6);
//	    convertedLocation.setLongitude(gp.getLongitudeE6() / 1e6);
//	    return convertedLocation;
//	}

}
