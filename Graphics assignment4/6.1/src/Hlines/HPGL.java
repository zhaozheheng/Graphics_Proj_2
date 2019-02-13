package Hlines;

import java.io.FileWriter;
import java.io.IOException;

public class HPGL {
	FileWriter fw;
	public HPGL(Obj3D obj) {
		// TODO Auto-generated constructor stub
		String plotFileName = "", fName = obj.getFName();
		for (int i = 0; i < fName.length(); i++) {
			char ch = fName.charAt(i);
			if (ch == '.') {
				break;
			}
			plotFileName += ch;
		}
		plotFileName += ".plt";
		try {
			fw = new FileWriter(plotFileName);
			fw.write("IN;SP1;\n");
		} catch (IOException ioe) {
			// TODO: handle exception
		}
	}
	
	void write(String s){
		try {
			fw.write(s);
			fw.flush();
		} catch (IOException ioe) {
			// TODO: handle exception
		}
	}
}
