package com.molabs.aucklandparking;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class Park {
	
	private TypedArray parkInformation;
	private TypedArray parkGeoPosition;
	private ParkingOverlay parkOverlay;
	private String name;
	
	public Park(TypedArray pInfo, TypedArray pGeo, Drawable marker, MapView mapView, String name) {
		parkInformation = pInfo;
		parkGeoPosition = pGeo;
		parkOverlay = new ParkingOverlay(marker, mapView);
		this.name = name;
	}
	public void setParkInformation(TypedArray parkInformation) {
		this.parkInformation = parkInformation;
	}
	public void setParkGeoPosition(TypedArray parkGeoPosition) {
		this.parkGeoPosition = parkGeoPosition;
	}
	
	/**
	 * Add all the parks locations to the ParkingOverlay
	 */
	public void buildParkingOverlay() {
		GeoPoint tempPoint;
		OverlayItem overlayItem;
		for (int i = 0; i < this.parkGeoPosition.length(); i++) {
			String[] geo = this.parkGeoPosition.getString(i).split(",");
			tempPoint = new GeoPoint(
					(int) (Double.parseDouble(geo[0]) * 1e6),
					(int) (Double.parseDouble(geo[1]) * 1e6));
			overlayItem = new OverlayItem(tempPoint, this.name, this.parkInformation.getString(i));
			this.parkOverlay.addOverlay(overlayItem);
		}
	}
	public ParkingOverlay getParkOverlay() {
		return parkOverlay;
	}
}
