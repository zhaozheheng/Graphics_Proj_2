package Hlines;

import java.awt.Frame;

public class Hlines extends Frame{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Fr3DH(args.length > 0 ? args[0] : null, new CvHlines(), "Hidden-lines algorithm");
	}

}
