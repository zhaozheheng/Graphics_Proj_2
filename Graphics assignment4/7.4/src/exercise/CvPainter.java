package exercise;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Vector;

class CvPainter extends Canvas3D implements Runnable{
	Image image;
	Graphics gImage;
	double sunTheta = 0, sunPhi = 30;
	Thread thr = new Thread(this);
	
	double nowSunX, nowSunY, nowSunZ;
	private int maxX, maxY, centerX, centerY;
	private Obj3D obj;
	private Point2D imgCenter;
	
	public void run() {
		try {
			for (;;) {
				sunTheta += 0.02;
				Thread.sleep(50);
				repaint();
				//System.out.println(sunTheta);
			}
		} catch (InterruptedException e) {
			
		}
		
	}
	
	int w, h;
	public CvPainter() {
		thr.start();
	}
	
	Obj3D getObj(){
		return obj;
	}
	
	void setObj(Obj3D obj){
		this.obj = obj;
	}
	
	int iX(float x){
		return Math.round(centerX + x - imgCenter.x);
	}
	
	int iY(float y){
		return Math.round(centerY - y + imgCenter.y);
	}
	
	void sort(Tria[] tr, int[] colorCode, float[] zTr, int l, int r){
		int i = l, j = r, wInt;
		float x = zTr[(i+j)/2], w;
		Tria wTria;
		do {
			while (zTr[i] < x) {
				i++;
			}
			while (zTr[j] > x) {
				j--;
			}
			if (i < j) {
				w = zTr[i]; zTr[i] = zTr[j]; zTr[j] = w;
				wTria = tr[i]; tr[i] = tr[j]; tr[j] = wTria;
				wInt = colorCode[i]; colorCode[i] = colorCode[j];
				colorCode[j] = wInt;
				i++;
				j--;
			}else if (i == j) {
				i++;
				j--;
			}
		} while (i <= j);
		if (l < j) {
			sort(tr, colorCode, zTr, l, j);
		}
		if (i < r) {
			sort(tr, colorCode, zTr, i, r);
		}
	}
	
	public void paint(Graphics g){
		if (obj == null) {
			return;
		}
		Vector polyList = obj.getPolyList();
		nowSunX = Math.sin(sunPhi)*Math.cos(sunTheta);
		obj.setSunX(nowSunX);
		nowSunY = Math.sin(sunPhi)*Math.sin(sunTheta);
		obj.setSunY(nowSunY);
		nowSunZ = Math.cos(sunPhi);
		obj.setSunZ(nowSunZ);
		if (polyList == null) {
			return;
		}
		int nFaces = polyList.size();
		if (nFaces == 0) {
			return;
		}
		
		Dimension dim = getSize();
		maxX = dim.width - 1;
		maxY = dim.height - 1;
		centerX = maxX/2;
		centerY = maxY/2;
		
		obj.eyeAndScreen(dim);
		
		imgCenter = obj.getImgCenter();
		obj.planeCoeff();
		
		int nTria = 0;
		for (int j = 0; j < nFaces; j++) {
			Polygon3D pol = (Polygon3D)(polyList.elementAt(j));
			if (pol.getNrs().length < 3 || pol.getH() >= 0) {
				continue;
			}
			pol.triangulate(obj);
			nTria += pol.getT().length;
		}
		
		Tria[] tr = new Tria[nTria];
		int[] colorCode = new int[nTria];
		float[] zTr = new float[nTria];
		int iTria = 0;
		Point3D[] e = obj.getE();
		Point2D[] vScr = obj.getVScr();
		
		for (int j = 0; j < nFaces; j++) {
			Polygon3D pol = (Polygon3D)(polyList.elementAt(j));
			if (pol.getNrs().length < 3 || pol.getH() >= 0) {
				continue;
			}
			int cCode = obj.colorCode(pol.getA(), pol.getB(), pol.getC());
			Tria[] t = pol.getT();
			for (int i = 0; i < t.length; i++) {
				Tria tri = t[i];
				tr[iTria] = tri;
				colorCode[iTria] = cCode;
				float zA = e[tri.iA].z, zB = e[tri.iB].z, zC = e[tri.iC].z;
				zTr[iTria++] = zA + zB + zC;
			}
		}
		
		sort(tr, colorCode, zTr, 0, nTria - 1);
		
		for (iTria = 0; iTria < nTria; iTria++) {
			Tria tri = tr[iTria];
			Point2D a = vScr[tri.iA], b = vScr[tri.iB], c = vScr[tri.iC];
			int cCode = colorCode[iTria];
			/*
			int xc = (int)(cCode*Math.cos(sunTheta)-cCode*Math.sin(sunTheta));
			int yc = (int)(cCode*Math.cos(sunTheta+cCode*Math.sin(sunTheta)));
			if (xc<0 && yc<0) {
				sunTheta += Math.PI/2;
				xc = 0;
				yc = 0;
			}else if (xc<0) {
				xc = 0;
			}else if (yc<0) {
				yc = 0;
			}
			*/
			g.setColor(new Color(cCode, cCode, 0));
			int[] x = {iX(a.x), iX(b.x), iX(c.x)};
			int[] y = {iY(a.y), iY(b.y), iY(c.y)};
			g.fillPolygon(x, y, 3);
		}
	}

	/*
	public void update(Graphics g) {
		paint(g);
	}
	*/
}
