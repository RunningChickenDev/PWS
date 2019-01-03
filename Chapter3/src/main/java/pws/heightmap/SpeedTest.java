package pws.heightmap;

import com.jme3.terrain.heightmap.AbstractHeightMap;

public class SpeedTest {

	public static void main(String[] args) {
		int width = 1024;
		float scalar = 0.084543213f;
		float zseed = -1.2f;
		int iter = 3;
		AbstractHeightMap hmap;
		
		
		// PERLIN
		for(int i=0; i<iter; i++) {
			hmap = new PerlinMap(width, scalar, zseed);
			System.out.print(String.format("Perlin %d\n\t",i));
			hmap.load();
		}
		
		// SIMPLEX
		for(int i=0; i<iter; i++) {
			hmap = new SimplexMap(width, scalar);
			System.out.print(String.format("Simplex %d\n\t",i));
			hmap.load();
		}
	}
	
}
