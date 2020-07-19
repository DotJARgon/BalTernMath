package TernaryMath;

import java.util.Arrays;

public class Trits {
	
	public static final long powOf3[] = new long[39]; //Pretty much the max value that long can hold using powers of 3
	public static final long balVal[] = new long[39]; //this is specifically for the values of min and max of tritlength
	
	static {
		long k = 1;
		
		for(int i = 0; i < powOf3.length; i++) {
			powOf3[i] = k;
			balVal[i] = (k-1)/2;
			k*=3;
		}
	}
	
	private long n = Long.MIN_VALUE;//No trit subclass should reach this
	
	private byte[] trits;
	
	private byte tritNumber;
	
	public Trits(byte tritNumber, long n) {
		this.tritNumber = tritNumber;
		this.n = n;
		checkN();
	}
	public Trits(byte tritNumber, long n, boolean createTrits) {
		this.tritNumber = tritNumber;
		this.n = n;
		checkN();
		
		if(createTrits) {
			calcTrits();
		}
	}
	public Trits(byte tritNumber, byte[] trits) {
		this.tritNumber = tritNumber;
		this.trits = trits;
	}
	public Trits(byte tritNumber, String tritString) {
		this.trits = new byte[tritNumber];
		stringToTrits(tritString);
	}
	
	public void changeValue(long n) {
		this.n = n;
		checkN();
		this.trits = null;
	}
	public void changeValue(byte[] trits) {
		this.trits = trits;
		this.n = Long.MIN_VALUE;
	}
	public void changeValue(String s) {
		this.trits = new byte[tritNumber];
		stringToTrits(s);
	}
	public void changeValue(Trits t) {
		if(t.hasN()) {
			changeValue(t.getN());
		}
		else if(t.hasTrits()) {
			changeValue(t.getTrits());
		}
	}
	
	
	public void checkN() {
		if(hasN()) {
			if(abs(n) > mpi()) { //if out of range
				if(n < 0) { //if less than 0, negative
					n = -(abs(n)%mpi());
				}
				else {  //if positive
					n = n%mpi();
				}
			}
		}
	}
	
	public long getN() {
		if(!hasN()) {
			calcN();
		}
		return n;
	}
	public byte[] getTrits() {
		if(!hasTrits()) {
			calcTrits();
		}
		return Arrays.copyOf(trits, trits.length); //return a copy of the array
	}
	
	private void calcN() {
		n = 0;
		
		for(int i = 0; i < trits.length; i++) {
			n += trits[i] * powOf3[i];
		}
	}
	private void calcTrits() {
		long num = n;
		trits = new byte[tritNumber];

		int i = 0;

		while (num != 0 && i < tritNumber) {
			
			trits[i] = switch((int) (num%3)) {
				case 1 -> 1;
				case 2 ->-1;
				default-> 0;
			};
			
			// 0 is already what the bytes are, so it is ignored

			num = (num + ((num < 0) ? -1 : 1)) / 3; // automatically floored

			i++;
		}
		
	}
	
	private void stringToTrits(String s) {
		for(int i = 0; i < s.length(); i++) {
			trits[i] = switch (s.charAt(i)) {
				case '+' -> 1;
				case '-' ->-1;
				default  -> 0;
			};
		}
	}
	
	private String getString() {
		byte[] out = getTrits();
		for(int i = 0; i < out.length; i++) {
			out[out.length-1-i] = switch(out[i]) {
				case 1 -> '+';
				case-1 -> '-';
				default-> '0';
			};
		}
		return new String(out);
	}
	
	public boolean hasN() {
		return n != Long.MIN_VALUE;
	}
	public boolean hasTrits() {
		return trits != null;
	}
	
	@Override
	public String toString() {
		return getString();
	}
	
	//Arithmetic
	public Trits add(Trits arg) {
		return new Trits(
				max(this.tritNumber, arg.tritNumber),
				getN() + arg.getN()
		);
	}
	
	public Trits sub(Trits arg) {
		return new Trits(
				max(this.tritNumber, arg.tritNumber),
				getN() - arg.getN()
		);
	}
	
	public Trits mul(Trits arg) {
		return new Trits(
				max(this.tritNumber, arg.tritNumber),
				getN() * arg.getN()
		);
	}
	
	public Trits div(Trits arg) {
		return new Trits(
				max(this.tritNumber, arg.tritNumber),
				getN() / arg.getN()
		);
	}
	
	//Comparisons
	public Trits cmp(Trits arg) {
		if(getN() < arg.getN()) { //less than
			return new Trits((byte) 3, -1);
		}
		else if(getN() > arg.getN()) {//greater than
			return new Trits((byte) 3,  1);
		}
		
		return new Trits((byte) 3,  0); //equal to
	}
	
	public boolean lt(Trits arg) {
		return getN() < arg.getN();
	}
	public boolean le(Trits arg) {
		return getN() <= arg.getN();
	}
	
	public boolean gt(Trits arg) {
		return getN() > arg.getN();
	}
	public boolean ge(Trits arg) {
		return getN() >= arg.getN();
	}
	
	public boolean ne(Trits arg) {
		return getN() != arg.getN();
	}
	public boolean eq(Trits arg) {
		return getN() == arg.getN();
	}
	
	public byte len() {
		return tritNumber;
	}
	
	//others
	public Trits abs() {
		return new Trits(this.tritNumber, abs(getN()));
	}
	public Trits nabs() {
		return new Trits(this.tritNumber, -abs(getN()));
	}
	public Trits neg() {
		return new Trits(this.tritNumber, -getN());
	}
	
	//Tritshifting
	public Trits tritShiftRight(Trits arg) {
		byte[] shift = new byte[tritNumber];
			
		for(int i = 0; i < tritNumber - arg.getN(); i++) {
			shift[i] = trits[(int) (i + arg.getN())];
		}
			
		return new Trits(tritNumber, shift);
	}
	public Trits tritShiftLeft(Trits arg) {
		return new Trits(tritNumber, getN()*3);
	}
		
	public Trits castTo(byte newTritNum) {
		return new Trits(newTritNum, getN());
	}
	
	public Trits toTriad() {
		return castTo((byte) 3);
	}
	public Trits toTryte() {
		return castTo((byte) 6);
	}
	public Trits toDitryte() {
		return castTo((byte) 12);
	}
	public Trits toQuadtryte() {
		return castTo((byte) 24);
	}
	
	public long mpi() { //max positive int
		return balVal[tritNumber];
	}
	public long mni() { //min negative int
		return-balVal[tritNumber];
	}
	public long mcv() { //max current value
		return powOf3[tritNumber];
	}
	
	public String dataType() {
		return switch(tritNumber) {
			case  3 -> "Triad";
			case  6 -> "Tryte";
			case 12 -> "Ditryte";
			case 24 -> "Quadtryte";
			default -> "Malformed_Tritword";
		};
	}
	
	private byte max(byte a, byte b) {
		return (a > b) ? a : b;
	}
	private long abs(long n) {
		return (n < 0) ? -n : n;
	}
	
	public static void main(String args[]) {
		Trits trit = new Trits((byte) 24, 0);

		long t0 = System.currentTimeMillis();
		
		for(int i = 0; i < 1000000000; i++) { //one billion iterations
			trit.changeValue(trit.add(new Trits((byte) 24, 1)));
		}
		
		long t1 = System.currentTimeMillis();
		
		System.out.println((t1 - t0) + " ms");
		
		double seconds = (t1-t0)/1000.0;
		
		double hz = 1000000000.0/seconds;
		
		if(hz >= 1000000000.0) {
			System.out.println(hz/1000000000.0 + " GHz");
		}
		else if(hz >= 1000000.0) {
			System.out.println(hz/1000000.0 + " MHz");
		}
		else if(hz >= 1000.0) {
			System.out.println(hz/1000.0 + " KHz");
		}
		else {
			System.out.println(hz + " Hz");
		}
		
		System.out.println(trit.getN());
	}
	
	
}
