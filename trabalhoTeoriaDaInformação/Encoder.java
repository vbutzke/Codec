package trabalhoTeoriaDaInformação;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class Encoder {
	Boolean readFile(String input, String output) throws IOException{
		//int maxValue = Integer.MIN_VALUE;
		//int minValue = Integer.MAX_VALUE;
		//int charNumber =0;
		String hexString;		
		
		FileInputStream fileInput = new FileInputStream(input);
		FileWriter fileWriter = new FileWriter(output);
		
		
		
		
		int r;
		System.out.println("arquivo aberto");
		while ((r = fileInput.read()) != -1) {
			char c = (char) r;
			//int charValue =  c;
			//charNumber++;
			hexString = Integer.toHexString(c);			
			if (c <16){
				hexString = "0" + Integer.toHexString(c);
			}else{
				hexString = Integer.toHexString(c);
			}			
			//stringNumber = Integer.valueOf(hexString);
			
			
			//System.out.println(c + " | " + charValue  + " | " + hexString);
			
			/*
			if (charValue > maxValue){
				maxValue = charValue;
			}
			if (charValue < minvalue){
				minValue = charValue;
			}
			*/
			fileWriter.write(hexString);
			//fileOutput.write(hexString);
			

		}
		fileInput.close();
		fileWriter.close();
		System.out.println("Arquivo fechado");
		//max 122
		//System.out.println("Valor Maximo:" + maxValue);
		//min 10
		//System.out.println("Valor Minimo:" + minValue);
		//numero de caracter 152089
		//System.out.println("numero de caracteres:" + charNumber);

		
		return true;
	}

}
