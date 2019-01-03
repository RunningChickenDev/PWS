package pws.heightmap;

import com.jme3.terrain.heightmap.AbstractHeightMap;
import com.jme3.terrain.noise.basis.ImprovedNoise;

import pws.Util;

public class PerlinMap extends AbstractHeightMap {

	private int width;
	private float scalar;
	private float zseed;
	
	public PerlinMap(int width, float scalar, float zseed) {
		this.width = width;
		this.scalar = scalar;
		this.zseed = zseed;
	}
	
	@Override
	public boolean load() {
		heightData = new float[width*width];
		long dt = Util.measureExecutionTime(() -> {
			for(int y=0;y<width;y++) {
				for(int x=0;x<width;x++) {
					float h = ImprovedNoise.noise(x*scalar, y*scalar, zseed);
					heightData[y*width+x] = (h+1f)*8f;
				}
			}
		});
		System.out.println("It took " + dt + "ns to generate "+width*width+" vertices!");
		size = width;
		return true;
	}

}
