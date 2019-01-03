package pws.caves;

import java.util.ArrayList;
import java.util.List;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.terrain.heightmap.HeightMap;
import com.jme3.util.BufferUtils;

public abstract class SimpleTerrain extends Mesh {

	public SimpleTerrain(HeightMap h, List<Vector2f> deadPoints) {
		int size = h.getSize();
		
		List<Integer> deadIdx = new ArrayList<>();
		for(Vector2f p : deadPoints) {
			deadIdx.add((int) (p.y*size+p.x));
		}
		
		Vector3f[] vertices = new Vector3f[size*size];
		Vector3f[] normals = new Vector3f[size*size];
		int[] indices = new int[(size-1)*(size-1)*6];
		int idC = 0;
		
		for(int z=0;z<size;z++) {
			for(int x=0;x<size;x++) {
				int idx = z*size+x;
				vertices[idx] = new Vector3f(x, h.getScaledHeightAtPoint(x, z), z);
				if(x != 0 && x != size-1 && z != 0 && z != size-1) {
					normals[idx] = getNormal(x, z, h);
				} else {
					normals[idx] = Vector3f.UNIT_Y;
				}
				
				boolean connect = (deadIdx.contains(idx))? deadPoint(vertices[idx]) : true;
				if(z != size-1 && x != size-1 && connect) {
					indices[idC++] = idx;
					indices[idC++] = idx+size+1;
					indices[idC++] = idx+size;
					indices[idC++] = idx;
					indices[idC++] = idx+1;
					indices[idC++] = idx+1+size;
				}
			}
		}
		
		setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(vertices));
		setBuffer(Type.Normal, 3, BufferUtils.createFloatBuffer(normals));
		setBuffer(Type.Index, 3, indices);
		updateBound();
		updateCounts();
	}
	
	protected abstract boolean deadPoint(Vector3f vert);
	
	private Vector3f getNormal(int x, int z, HeightMap h) {
		Vector3f v0 = new Vector3f(x, h.getScaledHeightAtPoint(x, z), z);
		Vector3f v1 = new Vector3f(x+1, h.getScaledHeightAtPoint(x+1, z), z);
		Vector3f v2 = new Vector3f(x, h.getScaledHeightAtPoint(x, z+1), z+1);
		Vector3f v3 = new Vector3f(x-1, h.getScaledHeightAtPoint(x-1, z), z);
		Vector3f v4 = new Vector3f(x, h.getScaledHeightAtPoint(x, z-1), z-1);
		Vector3f r1, r2, r3, r4, normal;

		// Next populate (.set) the temp verts
		// I'll leave this up to you

		// Next subtract the center vert (v0)
		v1.subtractLocal(v0);
		v2.subtractLocal(v0);
		v3.subtractLocal(v0);
		v4.subtractLocal(v0);

		// Next get the normalized cross product for each surrounding vert
		r1 = v1.cross(v2).normalize();
		r2 = v2.cross(v3).normalize();
		r3 = v3.cross(v4).normalize();
		r4 = v4.cross(v1).normalize();

		// Lastly, get the sum of the above cross products
		normal = new Vector3f(r1);
		normal.addLocal(r2);
		normal.addLocal(r3);
		normal.addLocal(r4);
		return normal;
	}
}
