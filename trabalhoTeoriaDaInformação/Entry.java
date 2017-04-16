package trabalhoTeoriaDaInformação;

import java.io.IOException;

public class Entry {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		System.out.println("Teste");
		
		Encoder encoder = new Encoder();
		encoder.readFile();
		
		Decoder decoder = new Decoder();
		decoder.readFile();
		
		System.out.println("Execucao acabou");

	}

}
