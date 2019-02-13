package exercise;

import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFrame;

public class Tours extends JFrame{
	
	public static void main(String[] args)throws IOException {
		// TODO Auto-generated method stub
		new Tours();
	}
	
	public Tours() {
		// TODO Auto-generated constructor stub
		this.setSize(400, 400);
		Dto dto = new Dto();
		userSetting userSetting = new userSetting(dto);
		this.setContentPane(userSetting);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}
}

