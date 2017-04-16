package trabalhoTeoriaDaInformação;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

public class Decoder {
	Boolean readFile() throws IOException{
		
		FileInputStream fileInput = new FileInputStream("encoded.txt");
		FileWriter fileWriter = new FileWriter("decoded.txt");
		
		int r;
		String hexValue;
		while ((r = fileInput.read()) != -1) {
			char c = (char) r;
			hexValue = Character.toString(c);
			r = fileInput.read();
			c = (char) r;
			hexValue = hexValue + Character.toString(c);
			
			//System.out.println(hexValue + " | " + Integer.parseInt(hexValue, 16) + " | " + (char) Integer.parseInt(hexValue, 16));
			fileWriter.write((char) Integer.parseInt(hexValue, 16));
			
		}
		fileInput.close();
		fileWriter.close();
						
		return true;
	}

}
