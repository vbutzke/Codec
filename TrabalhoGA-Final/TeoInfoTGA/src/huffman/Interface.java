package huffman;

import java.awt.Component;
import java.io.*;
import java.util.ArrayList;

import javax.swing.*;

@SuppressWarnings("serial")
public class Interface extends JFrame{
	JPanel panel;
	JButton readme;
	JLabel codeLabel;
	JTextField codeField;
	JLabel decodeLabel;
	JTextField decodeField;
	JButton codeButton;
	JButton decodeButton;

	public static void main(String[] args) throws IOException {
		@SuppressWarnings("unused")
		Interface build = new  Interface();
	}

	public Interface() throws FileNotFoundException, IOException{
		panel = new JPanel();  
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	
		codeLabel 		= new JLabel("File Path:");
		codeField 		= new JTextField();
		codeButton 		= new JButton("Encode");
		decodeButton 	= new JButton("Decode");
		readme 			= new JButton("How To Use");
	
		readme.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.add(codeLabel);
		panel.add(codeField);
		panel.add(codeButton);
		panel.add(decodeButton);
		panel.add(readme);

		getContentPane().add(panel);    
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  

		pack();  
		setVisible(true); 

		readme.addActionListener(new java.awt.event.ActionListener() {  
			public void actionPerformed(java.awt.event.ActionEvent evt) {  
				JOptionPane.showMessageDialog(null, 
				"This is a simple way to use Huffman Adaptive in JAVA.\n"+
				"you only have to inform a File Path, and click on Encode,\n"+
				"doing this, an .enc file will be created in the same path\n"+
				"as your first file.\n"+
				"When Decode button is clicked, an .dec file will be\n"+
				"created in the same path as your .enc file.\n\n"+
				"Created/Adapted by: Matheus and Leticia",
				"Read-me",
				JOptionPane.INFORMATION_MESSAGE);  
			}  
		});  

		codeButton.addActionListener(new java.awt.event.ActionListener() {  
			public void actionPerformed(java.awt.event.ActionEvent evt) {  
				String inputFile = codeField.getText();
				
				String text = FileHandler.readFile(inputFile,true);
				AdaptiveHuffman ah = new AdaptiveHuffman(text.toCharArray());
				ArrayList<String> code = ah.encode();
				FileHandler.writeFile(codeField.getText() + ".enc", catStr(code), true);
			}  
		});  

		decodeButton.addActionListener(new java.awt.event.ActionListener() {  
			public void actionPerformed(java.awt.event.ActionEvent evt) { 
				String inputFile = codeField.getText();

				String decode = FileHandler.readFile(inputFile, false);
				AdaptiveHuffman ah = new AdaptiveHuffman(decode.toCharArray());
				String result = ah.decode();
				FileHandler.writeFile(codeField.getText() + ".dec", result, false);
			}
		});
	}
	private static String catStr(ArrayList<String> l) {
		// TODO Auto-generated method stub
		String result = "";
		for (String s : l) {
			result += s;
		}
		return result;
	}
}
