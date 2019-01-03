package pws.heightmap;

import com.jme3.terrain.heightmap.AbstractHeightMap;

import pws.Util;

public class SimplexMap extends AbstractHeightMap {

	private int width;
	private float scalar;
	
	public SimplexMap(int width, float scalar) {
		this.width = width;
		this.scalar = scalar;
	}
	
	@Override
	public boolean load() {
		heightData = new float[width*width];
		long dt = Util.measureExecutionTime(() -> {
			for(int y=0;y<width;y++) {
				for(int x=0;x<width;x++) {
					float h = (float) SimplexNoise.noise(x*scalar, y*scalar);
					heightData[y*width+x] = (h+1f)*8f;
				}
			}
		});
		System.out.println("It took " + dt + "ns to generate this map!");
		size = width;
		return true;
	}

}
