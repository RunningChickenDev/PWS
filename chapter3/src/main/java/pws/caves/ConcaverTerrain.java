package pws.caves;

import java.util.List;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.terrain.heightmap.HeightMap;

/**
 * Makes the deadpoint not connect, resulting in a gap
 * 
 * @author MisterCavespider
 *
 */
public class ConcaverTerrain extends SimpleTerrain {

	public ConcaverTerrain(HeightMap h, List<Vector2f> deadPoints) {
		super(h, deadPoints);
	}

	@Override
	protected boolean deadPoint(Vector3f vert) {
		return false;
	}

}
