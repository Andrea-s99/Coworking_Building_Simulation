package coworking.styles;

import java.awt.Color;



import java.awt.Font;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import coworking.agents.Coworker;
import gov.nasa.worldwind.WorldWind;
import gov.nasa.worldwind.render.BasicWWTexture;
import gov.nasa.worldwind.render.Material;
import gov.nasa.worldwind.render.Offset;
import gov.nasa.worldwind.render.WWTexture;
import repast.simphony.visualization.gis3D.PlaceMark;
import repast.simphony.visualization.gis3D.style.MarkStyle;

public class CoworkerStyle implements MarkStyle<Coworker>{

		private Map<String, WWTexture> textureMap;
		
		public CoworkerStyle() {
			/**
			 * Use of a map to store textures significantly reduces CPU and memory use
			 * since the same texture can be reused.  Textures can be created for different
			 * agent states and re-used when needed.
			 */
			textureMap = new HashMap<String, WWTexture>();
			
			loadTexture("coworker_travelling", "icons/1person.png");
	        loadTexture("coworker_working", "icons/male_avatar2.png");
			
		}
		
		private void loadTexture(String key, String filePath) {
	        
			URL localUrl = WorldWind.getDataFileStore().requestFile(filePath);
	        if (localUrl != null) {
	            textureMap.put(key, new BasicWWTexture(localUrl, false));
	        } else {
	            System.err.println("Error: Unable to load texture for " + key + " from " + filePath);
	        }
	        
	    }

		@Override
		public PlaceMark getPlaceMark(Coworker agent, PlaceMark mark) {
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
		public WWTexture getTexture(Coworker agent, WWTexture currentTexture) {
			/*	
			// If the texture is already defined, then just return the same texture since
			//  we don't want to update the tower agent appearance.  The only time the 
			//  below code will actually be used is on the initialization of the display
			//  when the icons are created.
			if (currentTexture != null)
				return currentTexture;
			
			return textureMap.get("coworker");
			*/
			
			if (agent.isWorking()) {
		    	return textureMap.get("coworker_working");
		    } else {
		    	return textureMap.get("coworker_travelling");
		    }
			
		}
		
		@Override
		public Offset getIconOffset(Coworker agent){
			return Offset.CENTER;
		}

		@Override
		public double getElevation(Coworker obj) {
			return 0;
		}

		@Override
		public double getScale(Coworker obj) {
			return 1;
		}

		@Override
		public double getHeading(Coworker obj) {
			return 0;
		}

		@Override
		public String getLabel(Coworker obj) {
			return null;
		}

		@Override
		public Color getLabelColor(Coworker obj) {
			return null;
		}

		@Override
		public Font getLabelFont(Coworker obj) {
			return null;
		}

		@Override
		public Offset getLabelOffset(Coworker obj) {
			return null;
		}

		@Override
		public double getLineWidth(Coworker obj) {
			return 0;
		}

		@Override
		public Material getLineMaterial(Coworker obj, Material lineMaterial) {
			return null;
		}
		
}
