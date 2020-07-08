package BalancedTernaryArithmetic;

public class Triad extends BTNum {
  public Triad() {
    super();
  }
  public Triad(byte[] arr) {
    super(arr);
  }
  public Triad(long num) {
    super(num);
  }
  public Triad(String bal) {
    super(bal);
  }
  public Triad(long num, String bal) {
    super(num, bal);
  }
  
  @Override
  protected void setup() {
    this.tritNum = 3;
    super.setup();
  }
}
