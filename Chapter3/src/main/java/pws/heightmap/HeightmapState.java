package pws.heightmap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.material.Material;
import com.jme3.renderer.Camera;
import com.jme3.terrain.geomipmap.TerrainLodControl;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.jme3.terrain.heightmap.AbstractHeightMap;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;

public class HeightmapState extends BaseAppState {

	public static enum Type {
		PERLIN,
		SIMPLEX,
		WORLEY
	}
	
	private Type t;
	
	public HeightmapState(Type t) {
		this.t = t;
	}
	
	@Override
	protected void initialize(Application app) {
		AbstractHeightMap hmap = null;
		/**
		 * Uses xz-scale of:
		 *    84 543 213 / 1 000 000 000
		 */
		switch(t) {
		default:
		case PERLIN:
			hmap = new PerlinMap(64, 0.084543213f, -1.2f);
			break;
		case SIMPLEX:
			hmap = new SimplexMap(64, 0.084543213f);
			break;
		case WORLEY:
			break;
		}
		hmap.load();
		System.out.println(Arrays.toString(hmap.getHeightMap()));
		
		Material mat = new Material(app.getAssetManager(), "Common/MatDefs/Terrain/Terrain.j3md");
		mat.setTexture("Alpha", app.getAssetManager().loadTexture("psychedelicland.png"));
		Texture r = app.getAssetManager().loadTexture("clay.png");
		r.setWrap(WrapMode.Repeat);
		mat.setTexture("Tex1", r);
		mat.setFloat("Tex1Scale", 128f);
		Texture g = app.getAssetManager().loadTexture("sand.png");
		g.setWrap(WrapMode.Repeat);
		mat.setTexture("Tex2", g);
		mat.setFloat("Tex2Scale", 128f);
		Texture b = app.getAssetManager().loadTexture("water.png");
		b.setWrap(WrapMode.Repeat);
		mat.setTexture("Tex3", b);
		mat.setFloat("Tex3Scale", 128f);
		
		
		TerrainQuad terrain = new TerrainQuad("Perlin Terrain", 65, 65, hmap.getHeightMap());
		terrain.setMaterial(mat);
		List<Camera> cameras = new ArrayList<>();
		cameras.add(app.getCamera());
		TerrainLodControl control = new TerrainLodControl(terrain, cameras);
		terrain.addControl(control);
		((SimpleApplication)app).getRootNode().attachChild(terrain);
	}

	@Override
	protected void cleanup(Application app) {
		
	}

	@Override
	protected void onEnable() {
	}

	@Override
	protected void onDisable() {
	}

}
