package pws.heightmap;

import com.jme3.app.DebugKeysAppState;
import com.jme3.app.FlyCamAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.app.StatsAppState;
import com.jme3.audio.AudioListenerState;

import pws.heightmap.HeightmapState.Type;

public class Main extends SimpleApplication {

	public static void main(String[] args) {
		switch(args[0].toLowerCase()) {
		case "-p":
		case "p":
			new Main(new HeightmapState(Type.PERLIN)).start();
			break;
		case "-s":
		case "s":
			new Main(new HeightmapState(Type.SIMPLEX)).start();
			break;
		case "test":
			String[] nextArgs = new String[args.length-1];
			System.arraycopy(args, 1, nextArgs, 0, args.length-1);
			SpeedTest.main(nextArgs);
		}
		
	}

	public Main(HeightmapState hstate) {
		super(new StatsAppState(), new FlyCamAppState(), new AudioListenerState(), new DebugKeysAppState(),
				hstate);
	}
	
	@Override
	public void simpleInitApp() {
		flyCam.setMoveSpeed(65f);
	}
	
}
