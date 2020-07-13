package TernaryMath;

public class BenchMarker {

	public static void main(String[] args) {
		//Math benching
		
		long t0 = System.currentTimeMillis();
		
		TritNumber t = new TritNumber(24, 10000);
		
		for(int i = 0; i < 1000000000; i++) { //one billion iterations
//			t = t.tritShiftRight(new TritNumber(6, 3));
			t = t.add(new TritNumber(6, 3));
		}
		
		long t1 = System.currentTimeMillis();
		
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
	}
}
