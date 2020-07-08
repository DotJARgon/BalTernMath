package BalancedTernaryArithmetic;

public class Ditryte extends BTNum {

  public Ditryte() {
    super();
  }
  public Ditryte(byte[] arr) {
    super(arr);
  }
  public Ditryte(long num) {
    super(num);
  }
  public Ditryte(String bal) {
    super(bal);
  }
  public Ditryte(long num, String bal) {
    super(num, bal);
  }
  
  @Override
  protected void setup() {
    this.tritNum = 12;
    super.setup();
  }
}
