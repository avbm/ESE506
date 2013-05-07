/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 */
package microsoft.hawaii.sampleapp.pathprediction;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class CustomItemizedOverlay extends ItemizedOverlay<OverlayItem> {

	private ArrayList<OverlayItem> mapOverlays = new ArrayList<OverlayItem>();

	private Context context;

	public CustomItemizedOverlay(Drawable defaultMarker) {
		super(boundCenterBottom(defaultMarker));
	}

	public CustomItemizedOverlay(Drawable defaultMarker, Context context) {
		this(defaultMarker);
		this.context = context;
		this.populate();
	}

	@Override
	protected OverlayItem createItem(int i) {
		return mapOverlays.get(i);
	}

	@Override
	public int size() {
		return mapOverlays.size();
	}

	@Override
	protected boolean onTap(int index) {
		OverlayItem item = mapOverlays.get(index);
		if (item == null) {
			return false;
		}

		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setTitle(item.getTitle());
		dialog.setMessage(item.getSnippet());
		dialog.show();
		return true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event, MapView mapView) {
		if (event.getAction() == 1) {
			GeoPoint p = mapView.getProjection().fromPixels((int) event.getX(),
					(int) event.getY());
			Toast.makeText(context,
					p.getLatitudeE6() / 1E6 + "," + p.getLongitudeE6() / 1E6,
					Toast.LENGTH_SHORT).show();
		}

		return false;
	}

	public void addOverlay(OverlayItem overlay) {
		this.mapOverlays.add(overlay);
		this.populate();
	}

	public void clearOverlay() {
		this.mapOverlays.clear();
		this.populate();
	}

}
