package trabalhoTeoriaDaInformação;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Entry {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		
		System.out.println("Teste");
		
		Encoder encoder = new Encoder();
		encoder.readFile("alice29.txt","encoded.txt");
		
		Decoder decoder = new Decoder();
		decoder.readFile("encoded.txt","decoded.txt");
		Huffman huffman = new Huffman();
		
		String contents = new String(Files.readAllBytes(Paths.get("encoded.txt")));
				
		huffman.compress(contents);
		try {
			FileWriter fileWriter = new FileWriter("encoded.txt");	
			fileWriter.write(huffman.expand());
			fileWriter.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		decoder.readFile("encoded.txt","decoded.txt");
		System.out.println("Execucao acabou");

	}

}
