package org.agh.map.managament;

import java.util.ArrayList;
import java.util.List;

import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.MapView;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.Polyline;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.SimpleLineSymbol;
import com.esri.core.symbol.SimpleMarkerSymbol;
import com.esri.core.symbol.TextSymbol;

public class PointManagament {
	
	public static List<AddressPoint> pointsList = new ArrayList<AddressPoint>();
	private static final float size = 20.0f;
	private static GlobalState globalState = GlobalState.getInstance();
	
	public static void dummyData(){
//		pointsList.add(new AddressPoint(19.914221019, 50.06707139,  "ulica Miechowska 5, 30-055 Krakow"));
//		pointsList.add(new AddressPoint(19.906010424, 50.069732095, "ulica Nawojki 5, 30-072 Krakow"));
//		pointsList.add(new AddressPoint(19.886617101, 50.082377402, "ulica Balicka 5, 30-121 Krakow"));
//		pointsList.add(new AddressPoint(19.885828236, 50.082096763, "ulica Balicka 20, 30-149 Krakow"));
	}
	
	public static Point toWebMercator(Point pnt)
	{
	    double mercatorX_lon = pnt.getX();
	    double mercatorY_lat = pnt.getY();
	    if ((Math.abs(mercatorX_lon) > 180 || Math.abs(mercatorY_lat) > 90))
	        return pnt;

	    double num = mercatorX_lon * 0.017453292519943295;
	    double x = 6378137.0 * num;
	    double a = mercatorY_lat * 0.017453292519943295;

	    mercatorX_lon = x;
	    mercatorY_lat = 3189068.5 * Math.log((1.0 + Math.sin(a)) / (1.0 - Math.sin(a)));
	    return new Point(mercatorX_lon, mercatorY_lat);
	}
	
	public static void markPoints(MapView map, GraphicsLayer graphicsLayer) throws Exception{
		//dummyData();
		
		SimpleLineSymbol lineSymbol = new SimpleLineSymbol(globalState.getLineColor() , 3, SimpleLineSymbol.STYLE.DASH);
		Polyline lineGeometry = new Polyline();
		
		Point point = new Point();
		if(pointsList.isEmpty()) throw new Exception("No points provided PointManagement.java line 53");
		
		point = toWebMercator( pointsList.get(0).getPoint() );
		
		lineGeometry.startPath(point.getX(), point.getY());
		
		boolean flag = false;
		
		for( AddressPoint addressPoint : pointsList ){
			SimpleMarkerSymbol resultSymbol = new SimpleMarkerSymbol( globalState.getNotVisitedColor() , 20, SimpleMarkerSymbol.STYLE.DIAMOND );
			resultSymbol.setColor( globalState.getNotVisitedColor() );
			resultSymbol.setSize( size );
			resultSymbol.setStyle( globalState.getStyle() );
			Graphic resultLocation = new Graphic( PointManagament.toWebMercator( addressPoint.getPoint() ), resultSymbol );
			TextSymbol txtSymbol = new TextSymbol( 10, addressPoint.getAddress(), globalState.getTextColor() );
			Graphic gr = new Graphic( PointManagament.toWebMercator( addressPoint.getPoint() ) , txtSymbol );
			graphicsLayer.addGraphic( gr );
			
			if( flag ){
				point = toWebMercator( addressPoint.getPoint() );
				lineGeometry.lineTo( point.getX(), point.getY() );
			}
			
			graphicsLayer.addGraphic( resultLocation );
			flag = true;
		}
		map.zoomTo(point, 10);
	}
	
}