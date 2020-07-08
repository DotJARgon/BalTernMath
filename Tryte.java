package BalancedTernaryArithmetic;

public class Tryte extends BTNum {

  public Tryte() {
    super();
  }
  public Tryte(byte[] arr) {
    super(arr);
  }
  public Tryte(long num) {
    super(num);
  }
  public Tryte(String bal) {
    super(bal);
  }
  public Tryte(long num, String bal) {
    super(num, bal);
  }
  
  @Override
  protected void setup() {
    this.tritNum = 6;
    super.setup();
  }
}