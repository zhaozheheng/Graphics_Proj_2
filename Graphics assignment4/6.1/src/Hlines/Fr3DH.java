package Hlines;

import java.awt.MenuItem;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

public class Fr3DH extends Fr3D{
	private MenuItem exportHPGL;
	CvHlines cv;
	
	public Fr3DH(String argFileName, CvHlines cv, String textTitle) {
		// TODO Auto-generated constructor stub
		super(argFileName, cv, textTitle);
		exportHPGL = new MenuItem("Export HP-GL");
		mF.add(exportHPGL);
		exportHPGL.addActionListener(this);
		this.cv = cv;
	}
	
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() instanceof MenuItem) {
			MenuItem mi = (MenuItem)ae.getSource();
			if (mi == exportHPGL) {
				Obj3D obj = cv.getObj();
				if (obj != null) {
					cv.setHPGL(new HPGL(obj));
					cv.repaint();
				}
				else {
					Toolkit.getDefaultToolkit().beep();
				}
			}
			else {
				super.actionPerformed(ae);
			}
		}
	}
}
