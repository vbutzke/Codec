package golombRice;

public class Encoder {
	private char[] input;
	private String output;
	
	public Encoder(String input) {
		this.input = input.toCharArray();
	}
	
	public void encode( int divisor) {
		int inputLoc = 0;
		int asciiCode;
		int prefixSize;
		int suffixSize;
		int restDiv;
		String tempOut = "";
		
		//write the divisor in the output
		output = String.valueOf(divisor + "|");
		
		while (inputLoc != input.length) {
			
			// get ASCII code from input
			asciiCode = (int) input[inputLoc];
			
			// calculate prefix size
			prefixSize = asciiCode / divisor;		
			
			// calculate suffix size
			suffixSize = (int) (Math.log10(divisor)/Math.log10(2)); 
			
			//calculate the rest of the division
			restDiv = asciiCode % divisor;
			
			// assemble the prefix
			for (int j = 0; j < prefixSize; j++){
				tempOut += "0";
			}
			
			// add stop bit
			tempOut += "1";
			
			// assemble suffix
			char[] suffix = Integer.toBinaryString(restDiv).toCharArray();

			if (suffixSize > suffix.length) {
				for (int j = suffix.length; j < suffixSize; j++) {
					tempOut += "0";
				}
			}
			
			for (int j = 0; j < suffix.length; j++){
				tempOut += suffix[j];
			}
			
			inputLoc++;
		}
		
		output += tempOut;
	}

	public String getOutput() {
		return output;
	}
	
}
