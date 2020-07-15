package TernaryMath;

public class TritNumber {
	public static final long powOf3[] = new long[39]; //Pretty much the max value that long can hold using powers of 3
	public static final long balVal[] = new long[39]; //this is specifically for the values of min and max of tritlength
	
	public static final TritNumber FALSE = new TritNumber(1, -1);
	public static final TritNumber NEUTRAL = new TritNumber(1, 0);
	public static final TritNumber TRUE = new TritNumber(1, 1);
	
	static {
		long k = 1;
		
		for(int i = 0; i < powOf3.length; i++) {
			powOf3[i] = k;
			balVal[i] = (k-1)/2;
			k*=3;
		}
		
	}
	
	private final long n; //The actual number stored in the trit
	private final int tritNum;
	private byte[] trits;
	
	public TritNumber(final int tritNum, final long n) { //No changes can be made to these
		this.tritNum = tritNum;
		
		if(abs(n) > balVal[tritNum]) { //Here we check if the value is out of bounds
			this.n = signOf(n) * (abs(n)%tritNum);
		}
		else {
			this.n = n;
		}
		
	}
	
	public TritNumber(final int tritNum, final byte[] trits) { //We are getting the number straight from tritvalues
		this.tritNum = tritNum;
		
		long v = tritsToDec(trits);
		
		if(abs(v) > balVal[tritNum]) { //Here we check if the value is out of bounds
			this.n = signOf(v) * (abs(v)%tritNum);
		}
		else {
			this.n = v;
			this.trits = trits; //if there are no diff, we just go right on ahead and set the trits now!
		}
	}
	
	//arithmetic
	public TritNumber add(TritNumber arg) {
		return new TritNumber((int) max(this.tritNum, arg.tritNum), this.n + arg.n);
	}
	public TritNumber sub(TritNumber arg) {
		return new TritNumber((int) max(this.tritNum, arg.tritNum), this.n - arg.n);
	}
	public TritNumber mul(TritNumber arg) {
		return new TritNumber((int) max(this.tritNum, arg.tritNum), this.n * arg.n);
	}
	public TritNumber div(TritNumber arg) {
		return new TritNumber((int) max(this.tritNum, arg.tritNum), this.n / arg.n);
	}
	public TritNumber rem(TritNumber arg) {
		return new TritNumber((int) max(this.tritNum, arg.tritNum), this.n % arg.n);
	}
	
	//shift ops
	public TritNumber tritShiftRight(TritNumber arg) {
		byte[] shift = new byte[tritNum];
		
		for(int i = 0; i < tritNum - arg.n; i++) {
			shift[i] = trits[(int) (i + arg.n)];
		}
		
		return new TritNumber(tritNum, shift);
	}
	public TritNumber tritShiftLeft(TritNumber arg) {
		byte[] shift = new byte[tritNum];
		
		for(int i = (int) arg.n; i < tritNum; i++) {
			shift[i] = trits[(int) (i - arg.n)];
		}
		
		return new TritNumber(tritNum, shift);
	}
	
	//basic unary ops
	public TritNumber abs() {
		return new TritNumber(this.tritNum, abs(this.n));
	}
	public TritNumber nabs() {
		return new TritNumber(this.tritNum, -abs(this.n));
	}
	public TritNumber signOf() {
		return new TritNumber(this.tritNum, signOf(this.n));
	}
	
	//cmp
	public TritNumber lt(TritNumber arg) {
		if(this.n < arg.n) {
			return TRUE;
		}
		return FALSE;
	}
	public TritNumber le(TritNumber arg) {
		return (lt(arg).equals(TRUE) || eq(arg).equals(TRUE)) ? TRUE : FALSE;
	}
	public TritNumber eq(TritNumber arg) {
		if(this.n < arg.n) {
			return TRUE;
		}
		return FALSE;
	}
	public TritNumber ge(TritNumber arg) {
		return (gt(arg).equals(TRUE) || eq(arg).equals(TRUE)) ? TRUE : FALSE;
	}
	public TritNumber gt(TritNumber arg) {
		if(this.n > arg.n) {
			return TRUE;
		}
		return FALSE;
	}
	
	//other utilities
	public String getBalancedTernary() {
		
		if (trits == null) { //this only ever occurs once
			buildTrits();
		}
		
		return toString();
	}
	
	private void buildTrits() {
		long num = n;
		byte[] out = new byte[tritNum];

		int i = 0;

		while (num != 0 && i < tritNum) {
			if (num % 3 == 1) {
				out[i] = 1;
			} else if (num % 3 == 2) {
				out[i] = -1;
			}

			// 0 is already what the bytes are, so it is ignored

			num = (num + ((num < 0) ? -1 : 1)) / 3; // automatically floored

			i++;
		}
		
		trits = out;
	}
	
	public int getTritLength() {
		return tritNum;
	}
	public long getRawLong() {
		return n;
	}
	
	@Override
	public String toString() {
		
		if(trits == null) {
			buildTrits();
		}
		
		StringBuilder sb = new StringBuilder();
	      
	      byte[] arr = trits;
	      
	      for(int i = 0; i < arr.length; i++) {
	      
	        switch(arr[i]) {
	          case 1 : sb.append('+'); break;
	          case 0 : sb.append('0'); break;
	          case-1 : sb.append('-'); break;
	        }
	        
	      }
	      
	      return sb.reverse().toString();
	}
	
	@Override
	public boolean equals(Object o) {
		
		if(o instanceof TritNumber) {
			return this.n == ((TritNumber) o).n;
		}
		
		return super.equals(o);
	}
	
	private static long tritsToDec(byte[] arr) {
		long out = 0;
		
		for(int i = 0; i < arr.length; i++) {
			out += arr[i] * powOf3[i];
		}
		
		return out;
	}
	
	
	public TritNumber getTriad(int t) {
		if(t*3 < tritNum) {
			if(trits == null) {
				buildTrits();
			}
			
			return new TritNumber(3, new byte[] {trits[t], trits[t+1], trits[t+2]});
		}
		return null; //there is no way to get this triad
	}
	
	public TritNumber getTryte(int t) {
		if(t*6 < tritNum) {
			if(trits == null) {
				buildTrits();
			}
			
			return new TritNumber(6, new byte[] {trits[t], trits[t+1], trits[t+2], trits[t+3], trits[t+4], trits[t+5]});
		}
		return null; //there is no way to get this tryte
	}
	public TritNumber getDiTryte(int t) {
		if(t*12 < tritNum) {
			if(trits == null) {
				buildTrits();
			}
			
			return new TritNumber(12, new byte[] {
					trits[t], trits[t+1], trits[t+2], trits[t+3], trits[t+4], trits[t+5],
					trits[t+6], trits[t+7], trits[t+8], trits[t+9], trits[t+10], trits[t+11]
			});
		}
		return null; //there is no way to get this ditryte
	}
	
	public TritNumber castTo(int newTritNumber) {
		return new TritNumber(newTritNumber, n);
	}
	public TritNumber joinWith(TritNumber arg) {
		byte[] out = new byte[this.tritNum + arg.tritNum];
		for(int i = 0; i < this.tritNum; i++) {
			out[i] = this.trits[i];
		}
		for(int i = 0; i < arg.tritNum; i++) {
			out[i + this.tritNum] = arg.trits[i];
		}
		
		return new TritNumber(out.length, out);
	}
	
	public String dataType() {
		return "Trit length: " + tritNum;
	}
	
	private static long abs(long n) {
		return (n < 0) ? -n : n;
	}
	private static byte signOf(long n) {
		return (byte) ((n < 0) ? -1 : 1);
	}
	private static long max(long a, long b) {
		return (a > b) ? a : b;
	}
	private static long min(long a, long b) {
		return (a > b) ? a : b;
	}
}
