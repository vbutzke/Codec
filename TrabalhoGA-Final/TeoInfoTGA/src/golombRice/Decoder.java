package golombRice;

public class Decoder {
	private char[] input;
	private String output = "";
	
	public Decoder(String input) {
		this.input = input.toCharArray();
	}
	
	public void decode(){
		String div = "";
		int divisor;
		int inputLoc = 0;
		int asciiCode;
		int prefixSize = 0;
		int suffixSize;
		int restDiv;
		int count = 1;
		
		// Divisior is in the the first part of the file. The scape code is pipe.
		while (inputLoc != input.length) {
			if (!Character.toString(input[inputLoc]).equals("|")){
				div += input[inputLoc];
				inputLoc++;
				continue;
			}
			break;
		}
		
		// disregard the pipe
		inputLoc++;
		
		divisor = Integer.parseInt(div);
	
		while (inputLoc != input.length) {

			// Locate the stop bit
			if (!Character.toString(input[inputLoc]).equals("1")) {
				prefixSize++;
				inputLoc++;
				continue;
			}
			
			// Calculate suffix size
			suffixSize = (int) (Math.log10(divisor)/Math.log10(2));
			
			// Jump over stop bit
			inputLoc += 1;
			
			String suffix = "";
			
			// Get the suffix value
			while (count <= suffixSize) {
				if (inputLoc < input.length){
					suffix += input[inputLoc];
					inputLoc++;
					count++;	
				}		
			}
			
			// Get the rest
			restDiv = Integer.parseInt(suffix, 2);
			
			// Get the ASCII code
			asciiCode = ( divisor * prefixSize ) + restDiv;
			
			// Get character from ASCII code
			output += Character.toString ((char) asciiCode);
			
			prefixSize = 0;
			count = 1;
		}
	}

	public String getOutput() {
		return output;
	}

}
