package gui;

import golombRice.*;
import huffman.*;

import java.awt.Component;
import java.io.*;
import java.util.ArrayList;

import javax.swing.*;

@SuppressWarnings("serial")
public class Interface extends JFrame{
	JPanel panelMain;
	JButton readme;
	JLabel codeLabel;
	JTextField codeField;
	JTextField pathField;
	JLabel pathLabel;
	JLabel divLabel;
	JTextField divField;
	JLabel decodeLabel;
	JTextField decodeField;
	JButton codeButton;
	JButton decodeButton;

	public static void main(String[] args) throws IOException {
		@SuppressWarnings("unused")
		Interface build = new  Interface();
	}
	
	public Interface() throws FileNotFoundException, IOException{
		panelMain = new JPanel();  
		panelMain.setLayout(new BoxLayout(panelMain, BoxLayout.Y_AXIS));
	
		final JLabel titleLabel   = new JLabel("Welcome to TrabalhoGA");
		final JLabel optionLabel	= new JLabel("Select one Option:");
		final JButton huffmanButton 	= new JButton("Huffman");
		final JButton golombButton	= new JButton("Golomb");
	
		panelMain.add(titleLabel);
		panelMain.add(optionLabel);
		panelMain.add(huffmanButton);
		panelMain.add(golombButton);

		getContentPane().add(panelMain);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  

		pack();  
		setVisible(true); 
		
		huffmanButton.addActionListener(new java.awt.event.ActionListener() {  
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				panelMain.remove(titleLabel);
				panelMain.remove(optionLabel);
				panelMain.remove(huffmanButton);
				panelMain.remove(golombButton);
				buildHuffman(); 
			}
		});

		golombButton.addActionListener(new java.awt.event.ActionListener() {  
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				panelMain.remove(titleLabel);
				panelMain.remove(optionLabel);
				panelMain.remove(huffmanButton);
				panelMain.remove(golombButton);
				buildGolomb(); 
			}
		});  
	}
	
	public void buildHuffman() {
		panelMain.setLayout(new BoxLayout(panelMain, BoxLayout.Y_AXIS));
	
		codeLabel 		= new JLabel("File Path:");
		codeField 		= new JTextField();
		codeButton 		= new JButton("Encode");
		decodeButton 	= new JButton("Decode");
		readme 			= new JButton("How To Use");
	
		readme.setAlignmentX(Component.LEFT_ALIGNMENT);
		panelMain.add(codeLabel);
		panelMain.add(codeField);
		panelMain.add(codeButton);
		panelMain.add(decodeButton);
		panelMain.add(readme);

		getContentPane().add(panelMain);    
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
				AdaptiveHuffman ahBin = new AdaptiveHuffman(text.toCharArray());
				ArrayList<String> code = ah.encode();
				ArrayList<String> codeBin = ahBin.encodeBin();
				FileHandler.writeFile(codeField.getText() + ".enc", catStr(code), true);
				FileHandler.writeFile(codeField.getText() + "Bin.enc", catStr(codeBin), true);
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
	
	public void buildGolomb(){
		pathLabel = new JLabel("File:");
		pathField = new JTextField();
		divLabel = new JLabel("Divisor:");
		divField = new JTextField();
		codeButton 		= new JButton("Encode");
		decodeButton 	= new JButton("Decode");
		
		panelMain.add(pathLabel);
		panelMain.add(pathField);
		panelMain.add(divLabel);
		panelMain.add(divField);
		panelMain.add(codeButton);
		panelMain.add(decodeButton);
		
		getContentPane().add(panelMain);    
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  

		pack();  
		setVisible(true); 
		
		codeButton.addActionListener(new java.awt.event.ActionListener() {  
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				String inputFile = pathField.getText();
				String inputText = FileHandler.readFile(inputFile,true);

				Encoder encoder = new Encoder(inputText);
				encoder.encode(Integer.parseInt(divField.getText()));
				String outputText = encoder.getOutput();
				BufferedWriter output = null;
				String outputFile = pathField.getText() + "_encoded.dat";
				try {
					output = new BufferedWriter(new FileWriter(outputFile));
					output.write(outputText); 
					System.out.println(outputText);
				} catch (Exception ex) { 
					
				}
				
				try {
					output.close();
					JOptionPane.showMessageDialog(null, "File " + outputFile + " successfuly generated", "Information", JOptionPane.INFORMATION_MESSAGE); 
				} catch (IOException ex) {
				
				}
			}
 
		});  

		decodeButton.addActionListener(new java.awt.event.ActionListener() {  
			public void actionPerformed(java.awt.event.ActionEvent evt) { 
				String inputFile = pathField.getText();
				String inputText = FileHandler.readFile(inputFile,true);
				
				Decoder decoder = new Decoder(inputText);
				decoder.decode();
				String outputText = decoder.getOutput();
				BufferedWriter output = null;
				String outputFile = pathField.getText() + "_decoded.dat";
				try {
					output = new BufferedWriter(new FileWriter(outputFile));
					output.write(outputText); 
					System.out.println(outputText);
				} catch (Exception ex) {
				
				}
				
				try {
					output.close();
					JOptionPane.showMessageDialog(null, "File " + outputFile + " successfuly generated", "Information", JOptionPane.INFORMATION_MESSAGE); 
				} catch (IOException ex) {
				
				}				
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
	
	@SuppressWarnings("unused")
	private static String readFile(String path) throws FileNotFoundException, IOException {
		ArrayList<String> lines = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader(path));
		String line = br.readLine();
		while (line != null) {
			lines.add(line);
			line = br.readLine();
		}
		String allLines = "";
		for(int i = 0; i < lines.size(); i++ ) {
			allLines = allLines + lines.get(i);
		}
		br.close();
		
		return allLines;
	}
}
