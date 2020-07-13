package TernaryMath;

public class Test {

	public static void main(String[] args) {
		TritNumber t = new TritNumber(6, 8);
		
		System.out.println(t);
		
		t = t.tritShiftLeft(new TritNumber(6, 1));
		
		System.out.println(t);
	}

}
