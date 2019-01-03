package pws.caves;

import java.util.ArrayList;
import java.util.List;

import com.jme3.app.SimpleApplication;
import com.jme3.collision.CollisionResults;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.material.RenderState.FaceCullMode;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.terrain.heightmap.AbstractHeightMap;
import com.jme3.terrain.heightmap.ImageBasedHeightMap;
import com.jme3.texture.Texture;

public class Main extends SimpleApplication implements ActionListener {

	private static boolean concave = true;
	
	public static void main(String[] args) {
		if(args.length > 0) {
			System.out.println("Caves recieved argument: " + args[0]);
			switch(args[0]) {
			case "convex":
				System.out.println("Using convex");
				concave = false;
				break;
			default:
				System.out.println("Defaulting to concave ...");
			case "concave":
				System.out.println("Using concave");
//				concave = true;		// This is unnecessary, as it is initialized as true
				break;
			}
		}
		Main m = new Main();
		m.start();
	}
	
	@Override
	public void simpleInitApp() {
		flyCam.setMoveSpeed(65f);
		
		Material mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
		mat.getAdditionalRenderState().setFaceCullMode(FaceCullMode.Off);
		Texture hTex = assetManager.loadTexture("heightmap.png");
		AbstractHeightMap h = new ImageBasedHeightMap(hTex.getImage(), 1f);
		h.load();
		
		/**
		 * Currently set to use dead points
		 */
		List<Vector2f> deadPoints = holeOut(130, 100, 140, 120);
		SimpleTerrain tt = (concave)? new ConcaverTerrain(h, deadPoints) : new ConvexerTerrain(h, deadPoints);
		Geometry t = new Geometry("Simple Terrain", tt);
		t.setMaterial(mat);
		rootNode.attachChild(t);
		
		DirectionalLight dl = new DirectionalLight(new Vector3f(+.13f, +.95f, -.85f).normalizeLocal());
		rootNode.addLight(dl);
		AmbientLight al = new AmbientLight(ColorRGBA.Gray.multLocal(0.2f));
		rootNode.addLight(al);
		
		inputManager.addMapping("RAY", new KeyTrigger(KeyInput.KEY_SPACE));
		inputManager.addListener(this, "RAY");
	}

	private List<Vector2f> holeOut(int x0, int z0, int x1, int z1) {
		List<Vector2f> r = new ArrayList<>();
		for(int z=z0;z<z1;z++) {
			for(int x=x0;x<x1;x++) {
				r.add(new Vector2f(x,z));
			}
		}
		return r;
	}
	
	private void ray() {
		CollisionResults results = new CollisionResults();
		Ray ray = new Ray(cam.getLocation(), cam.getDirection());
		rootNode.collideWith(ray, results);
		System.out.println("Tridex: " + results.getClosestCollision().getContactPoint());
	}
	
	@Override
	public void onAction(String name, boolean isPressed, float tpf) {
		if(isPressed) return;
		ray();
	}

}
