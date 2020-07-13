package TernaryMath;

public class KleeneUnary {
	private final byte[] operations;
	
	public KleeneUnary(int n0, int n1, int n2) { //given value false return n0, neut give n1, true gives n2;
		operations = new byte[] {(byte) n0, (byte) n1, (byte) n2};
	}
	
	public byte[] operation(byte[] arr) {
		byte[] out = new byte[arr.length];
		
		for(int i = 0; i < arr.length; i++) {
			switch(arr[i]) {
				case -1 : out[i] = operations[0]; break;
				case  0 : out[i] = operations[1]; break;
				case  1 : out[i] = operations[2]; break;
			}
		}
		
		return out;
	}
	
	public static final KleeneUnary NOT = new KleeneUnary(1, 0, -1);
}
