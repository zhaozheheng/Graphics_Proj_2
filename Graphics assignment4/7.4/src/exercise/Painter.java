package exercise;

import java.awt.Frame;

public class Painter extends Frame{

	public static void main(String[] args) {
		new Fr3D(args.length > 0 ? args[0] : null, new CvPainter(), "Painter");
	}

}
