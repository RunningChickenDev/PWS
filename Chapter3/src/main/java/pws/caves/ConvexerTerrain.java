package pws.caves;

import java.util.List;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.terrain.heightmap.HeightMap;

/**
 * Makes deadpoints not go to y=0.
 * This creates a low flat plateau in the terrain.
 * 
 * @author MisterCavespider
 *
 */
public class ConvexerTerrain extends SimpleTerrain {

	public ConvexerTerrain(HeightMap h, List<Vector2f> deadPoints) {
		super(h, deadPoints);
	}

	@Override
	protected boolean deadPoint(Vector3f vert) {
		vert.setY(0f);
		return true;
	}

}
