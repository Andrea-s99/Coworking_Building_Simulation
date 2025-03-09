package coworking.styles;

import java.awt.Color;

import java.awt.Font;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import coworking.agents.Aosta;
import gov.nasa.worldwind.WorldWind;
import gov.nasa.worldwind.render.BasicWWTexture;
import gov.nasa.worldwind.render.Material;
import gov.nasa.worldwind.render.Offset;
import gov.nasa.worldwind.render.WWTexture;
import repast.simphony.visualization.gis3D.PlaceMark;
import repast.simphony.visualization.gis3D.style.MarkStyle;

public class AostaStyle implements MarkStyle<Aosta>{

		private Map<String, WWTexture> textureMap;
		
		public AostaStyle() {
			/**
			 * Use of a map to store textures significantly reduces CPU and memory use
			 * since the same texture can be reused.  Textures can be created for different
			 * agent states and re-used when needed.
			 */
			textureMap = new HashMap<String, WWTexture>();
		
			String fileNameAosta = "icons/office1.png";
			
			URL localUrl = WorldWind.getDataFileStore().requestFile(fileNameAosta);
			if (localUrl != null)	{
				textureMap.put("aosta", new BasicWWTexture(localUrl, false));
			}
			
		}
		
		@Override
		public PlaceMark getPlaceMark(Aosta agent, PlaceMark mark) {
			// PlaceMark is null on first call.
			if (mark == null)
				mark = new PlaceMark();
			
			/**
			 * The Altitude mode determines how the mark appears using the elevation.
			 *   WorldWind.ABSOLUTE places the mark at elevation relative to sea level
			 *   WorldWind.RELATIVE_TO_GROUND places the mark at elevation relative to ground elevation
			 *   WorldWind.CLAMP_TO_GROUND places the mark at ground elevation
			 */
			mark.setAltitudeMode(WorldWind.RELATIVE_TO_GROUND);
			mark.setLineEnabled(false);
			
			return mark;
		}

		/**
		 * Here we set the appearance of the TowerAgent using a non-changing icon.
		 */
		@Override
		public WWTexture getTexture(Aosta agent, WWTexture currentTexture) {
				
			// If the texture is already defined, then just return the same texture since
			//  we don't want to update the tower agent appearance.  The only time the 
			//  below code will actually be used is on the initialization of the display
			//  when the icons are created.
			if (currentTexture != null)
				return currentTexture;
			
			return textureMap.get("aosta");			
		}
		
		@Override
		public Offset getIconOffset(Aosta agent){
			return Offset.CENTER;
		}

		@Override
		public double getElevation(Aosta obj) {
			return 0;
		}

		@Override
		public double getScale(Aosta obj) {
			return 1;
		}

		@Override
		public double getHeading(Aosta obj) {
			return 0;
		}

		@Override
		public String getLabel(Aosta obj) {
			return null;
		}

		@Override
		public Color getLabelColor(Aosta obj) {
			return null;
		}

		@Override
		public Font getLabelFont(Aosta obj) {
			return null;
		}

		@Override
		public Offset getLabelOffset(Aosta obj) {
			return null;
		}

		@Override
		public double getLineWidth(Aosta obj) {
			return 0;
		}

		@Override
		public Material getLineMaterial(Aosta obj, Material lineMaterial) {
			return null;
		}
		
}

