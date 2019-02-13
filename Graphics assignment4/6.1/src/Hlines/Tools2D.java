package Hlines;

public class Tools2D {
	public static float area2(Point2D a, Point2D b, Point2D c){
		return (a.x - c.x) * (b.y - c.y) - (a.y - c.y) * (b.x - c.x);
	}

	public static boolean insideTriangle(Point2D a, Point2D b, Point2D c, Point2D p) {
		// TODO Auto-generated method stub
		return Tools2D.area2(a, b, p) >= 0 &&
				Tools2D.area2(b, c, p) >= 0 &&
				Tools2D.area2(c, a, p) >= 0;
	}

	static void triangulate(Point2D[] p, Triangle[] tr){
		int n = p.length, j = n - 1, iA = 0, iB, iC;
		int[] next = new int[n];
		for (int i = 0; i < n; i++) {
			next[j] = i;
			j = i;
		}
		for (int k = 0; k < n - 2; k++) {
			Point2D a, b, c;
			boolean triaFound = false;
			int count = 0;
			while (!triaFound && ++count < n) {
				iB = next[iA]; iC = next[iB];
				a = p[iA]; b = p[iB]; c = p[iC];
				if (Tools2D.area2(a, b, c) >= 0) {
					j = next[iC];
					while (j != iA && !insideTriangle(a, b, c, p[j])) {
						j = next[j];
					}
					if (j == iA) {
						tr[k] = new Triangle(a, b, c);
						next[iA] = iC;
						triaFound = true;
					}
				}
				iA = next[iA];
			}
			if (count == n) {
				System.out.println("Not a simple polygon" + " or vertex sequence not counter-clockwise.");
				System.exit(1);
			}
		}
	}
	
	public static float distance2(Point2D p, Point2D q) {
		float dx = p.x - q.x,
				dy = p.y - q.y;
		return dx * dx + dy * dy;
	}
}
