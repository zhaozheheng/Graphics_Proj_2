package Exercise1;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CubRot2 extends Frame{

	public static void main(String[] args) {
		new CubRot2();
	}

	CubRot2() {
		super("Rotating cubes (double buffering)");
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
		add("Center", new CvCubRot2());
		Dimension dimension = getToolkit().getScreenSize();
		setSize(3 * dimension.width / 4, dimension.height / 2);
		setLocation(dimension.width / 8, dimension.height / 4);
		show();
	}
	
	class CvCubRot2 extends Canvas implements Runnable{
		int centerX, centerY, w, h;
		Obj2 obj = new Obj2();
		Image image;
		Graphics gImage;
		
		double alpha = 0;
		Thread thread = new Thread(this);
		
		public void run(){
			try {
				for (;;) {
					alpha += 0.01;
					repaint();
					Thread.sleep(20);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		CvCubRot2() {
			thread.start();
		}
		
		public void update(Graphics g){
			paint(g);
		}
		
		int iX(float x){
			return Math.round(centerX + x);
		}
		int iY(float y){
			return Math.round(centerY - y);
		}
		
		void line(int i, int j){
			Point2D P = obj.vScr[i],
					Q = obj.vScr[j];
			gImage.drawLine(iX(P.x), iY(P.y), iX(Q.x), iY(Q.y));
		}
		
		public void paint(Graphics g){
			Dimension dimension = getSize();
			int maxX = dimension.width - 1, maxY = dimension.height - 1;
			centerX = maxX / 2;
			centerY = maxY / 2;
			int minMaxXY = Math.min(maxX, maxY);
			obj.d = obj.rho * minMaxXY / obj.objSize;
			obj.rotateCube(alpha);
			obj.eyeAndScreen();
			if (w != dimension.width || h != dimension.height) {
				w = dimension.width;
				h = dimension.height;
				image = createImage(w, h);
				gImage = image.getGraphics();
			}
			gImage.clearRect(0, 0, w, h);
			line(0, 1);line(1, 2);line(2, 3);line(3, 0);
			line(4, 5);line(5, 6);line(6, 7);line(7, 4);
			line(0, 4);line(1, 5);line(2, 6);line(3, 7);
			line(8, 9);line(9, 10);line(10, 11);line(11, 8);
			line(12, 13);line(13, 14);line(14, 15);line(15, 12);
			line(8, 12);line(9, 13);line(10, 14);line(11, 15);
			g.drawImage(image, 0, 0, null);
		}
	}
	
	class Obj2{
		float rho, theta = 0F, phi = 1.3F, d;
		Point3D[] s, w;
		Point2D[] vScr;
		float v11, v12, v13, v21, v22, v23, v32, v33, v43,
		xe, ye, ze, objSize = 8;
		
		Obj2(){
			s = new Point3D[16];
			w = new Point3D[16];
			vScr = new Point2D[16];
			
			s[0] = new Point3D(1,-3,-1);
			s[1] = new Point3D(1,-1,-1);
			s[2] = new Point3D(-1,-1,-1);
			s[3] = new Point3D(-1,-3,-1);
			
			s[4] = new Point3D(1,-3,1);
			s[5] = new Point3D(1,-1,1);
			s[6] = new Point3D(-1,-1,1);
			s[7] = new Point3D(-1,-3,1);
			
			s[8] = new Point3D(1,1,-1);
			s[9] = new Point3D(1,3,-1);
			s[10] = new Point3D(-1,3,-1);
			s[11] = new Point3D(-1,1,-1);
			
			s[12] = new Point3D(1,1,1);
			s[13] = new Point3D(1,3,1);
			s[14] = new Point3D(-1,3,1);
			s[15] = new Point3D(-1,1,1);
			rho = 15;
		}
		
		void rotateCube(double alpha){
			Rota3D.initRotate(s[0], s[4], alpha);
			for (int i = 0; i < 8; i++) {
				w[i] = Rota3D.rotate(s[i]);
			}
			Rota3D.initRotate(s[13], s[9], 2 * alpha);
			for (int i = 8; i < 16; i++) {
				w[i] = Rota3D.rotate(s[i]);
			}
		}
		
		void initPersp(){
			float costh = (float)Math.cos(theta),
			sinth = (float)Math.sin(theta),
			cosph = (float)Math.cos(phi),
			sinph = (float)Math.sin(phi);
			v11 = -sinth;
			v12 = -cosph * costh;
			v13 = sinph * costh;
			v21 = costh;
			v22 = -cosph * sinth;
			v23 = sinph * sinth;
			v32 = sinph;
			v33 = cosph;
			v43 = -rho;
		}
		
		void eyeAndScreen(){
			initPersp();
			for (int i = 0; i < 16; i++) {
				Point3D P = w[i];
				float x = v11 * P.x + v21 * P.y;
				float y = v12 * P.x + v22 * P.y + v32 * P.z;
				float z = v13 * P.x + v23 * P.y + v33 * P.z + v43;
				Point3D Pe = new Point3D(x, y, z);
				vScr[i] = new Point2D(-d * Pe.x/Pe.z, -d * Pe.y/Pe.z);
			}
		}
	}
}
