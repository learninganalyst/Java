
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//JChecbox = A GUI component that can be selected or deselected

public class MyFrame extends JFrame implements ActionListener{
	
	JComboBox comboBox;
	JFrame frame;
	

	MyFrame(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new FlowLayout());
		
		String[] animals = {"pig", "horse", "dog"};
		
		comboBox = new JComboBox(animals);
		comboBox.addActionListener(this);
		
		//comboBox.setEditable(rootPaneCheckingEnabled);
		//comboBox.addItem("horse");
		//comboBox.insertItemAt("pig",0);
		//comboBox.setSelectedIndex(0);
		//comboBox.removeItemAt(0);
		this.add(comboBox);
		this.pack();
		this.setVisible(true);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==comboBox) {
			System.out.println(comboBox.getSelectedItem());
			System.out.println(comboBox.getSelectedIndex());
		}
	
}
	
	
}
