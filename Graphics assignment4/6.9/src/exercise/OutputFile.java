package exercise;

import java.io.FileWriter;
import java.io.IOException;

public class OutputFile {
	int n1, n2;
	double r1, r2;
	String fileName = "torus.dat";
	
	public OutputFile(int n1, double r1, int n2, double r2, String fileName)throws IOException {
		// TODO Auto-generated constructor stub
		this.n1 = n1;
		this.r1 = r1;
		this.n2 = n2;
		this.r2 = r2;
		this.fileName = fileName;
		FileWriter fwFileWriter = new FileWriter(fileName);
		double delta1 = 2 * Math.PI / n1;
		double delta2 = 2 * Math.PI / n2;
		for (int i = 0; i < n1; i++) {
			double alpha = i * delta1,
					cosa = Math.cos(alpha), sina = Math.sin(alpha);
			for (int j = 0; j < n1; j++) {
				double beta = j * delta1, x = r1 + Math.cos(beta);
				float x1 = (float)(cosa * x),
						y1 = (float)(sina * x),
						z1 = (float)Math.sin(beta);
				fwFileWriter.write((i * n1 + j + 1) + " " + x1 + " " + y1 + " " + z1 + "\r\n");
			}
		}
		for (int i = 0; i < n2; i++) {
			double alpha = i * delta2,
					cosa = Math.cos(alpha), sina = Math.sin(alpha);
			for (int j = 0; j < n2; j++) {
				double beta = j * delta2, x = r2 + Math.cos(beta);
				float x1 = (float)(cosa * x + r1),
						y1 = -(float)Math.sin(beta),
						z1 = (float)(sina * x);
				fwFileWriter.write((i * n2 + j + 1 + n1 * n1) + " " + x1 + " " + y1 + " " + z1 + "\r\n");
			}
		}
		fwFileWriter.write("Faces:\r\n");
		for (int i = 0; i < n1; i++) {
			for (int j = 0; j < n1; j++) {
				int i1 = (i + 1) % n1, j1 = (j + 1) % n1,
						a = i * n1 + j + 1,
						b = i1 * n1 + j + 1,
						c = i1 * n1 + j1 + 1,
						d = i * n1 + j1 + 1;
				fwFileWriter.write(a + " " + b + " " + c + " " + d + ".\r\n");
			}
		}
		for (int i = 0; i < n2; i++) {
			for (int j = 0; j < n2; j++) {
				int i1 = (i + 1) % n2, j1 = (j + 1) % n2,
						a = i * n2 + j + 1 + n1 * n1,
						b = i1 * n2 + j + 1 + n1 * n1,
						c = i1 * n2 + j1 + 1 + n1 * n1,
						d = i * n2 + j1 + 1 + n1 * n1;
				fwFileWriter.write(a + " " + b + " " + c + " " + d + ".\r\n");
			}
		}
		fwFileWriter.close();
	}
}

