package exercise;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class userSetting extends JPanel{
	Dto dto;
	OutputFile outputFile = null;
	private int n1, n2;
	private double r1, r2;
	String fileName = "torus.dat";

	public userSetting(Dto dto){
		// TODO Auto-generated constructor stub
		this.dto = dto;
		this.setLayout(new GridLayout(5, 1));
		/*
		 * 参数N1设置
		 */
		JTextField n1Patameter = new JTextField();
		JLabel n1Factor = new JLabel("N1: ");
		
		this.add(n1Factor);
		this.add(n1Patameter);
		/*
		 * 参数R1设置
		 */
		JTextField r1Patameter = new JTextField();
		JLabel r1Factor = new JLabel("R1: ");
		
		this.add(r1Factor);
		this.add(r1Patameter);
		/*
		 * 参数N2设
		 */
		JTextField n2Patameter = new JTextField();
		JLabel n2Factor = new JLabel("N2: ");
		
		this.add(n2Factor);
		this.add(n2Patameter);
		/*
		 * 参数R2设置
		 */
		JTextField r2Patameter = new JTextField();
		JLabel r2Factor = new JLabel("R2: ");
		
		this.add(r2Factor);
		this.add(r2Patameter);
		/*
		 * 确定按钮
		 */
		JButton startBt = new JButton("Generate File");
		startBt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				n1 = Integer.parseInt(n1Patameter.getText().toString());
				r1 = Double.parseDouble(r1Patameter.getText().toString());
				n2 = Integer.parseInt(n2Patameter.getText().toString());
				r2 = Double.parseDouble(r2Patameter.getText().toString());
				if (n1!=0&&n2!=0&&r1!=0&&r2!=0) {
					if (Math.min(r1, r2) >= 2 && Math.max(r1, r2) <= 2 * (Math.min(r1, r2) - 1)) {
						dto.setN1(n1);
						dto.setR1(r1);
						dto.setN2(n2);
						dto.setR2(r2);
						try {
							outputFile = new OutputFile(dto.getN1(), dto.getR1(), dto.getN2(), dto.getR2(), fileName);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					else {
						System.out.println("R1 or R2 is too small");
					}
					System.out.println(n1);
					System.out.println(r1);
					System.out.println(n2);
					System.out.println(r2);
				}
			}
		});
		this.add(startBt);
	}
}
