package pws;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {

	public static void parseArgs(String[] args) {
		System.out.println("args: " + Arrays.toString(args));
		
		String[] nextArgs = new String[args.length-1];
		System.arraycopy(args, 1, nextArgs, 0, args.length-1);
		
		switch(args[0].toLowerCase()) {
		case "caves":
			pws.caves.Main.main(nextArgs);
			break;
		case "heightmap":
			pws.heightmap.Main.main(nextArgs);
			break;
		}
	}
	
	public static void main(String[] args) throws IOException {
		if(args.length == 0) {
			System.err.println("No arguments passed!");
			System.err.println("Please enter arguments below:");
			
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String argR = br.readLine();
			parseArgs(argR.split(" "));
		} else {
			parseArgs(args);
		}
	}
}
