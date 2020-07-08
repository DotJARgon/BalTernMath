package BalancedTernaryArithmetic;

import BalancedTernaryArithmetic.BTNum;
import java.util.Arrays;


public class BTNum {
    protected int tritNum = 6; //Automatically a tryte
    
    private long MAX = 0;
    private long MIN = 0;
    
    private byte[] balTern;
    private long num = 0;
    
    private boolean calcNum = false;
    private boolean calcBal = false;
    
    public BTNum() {
      setup();
      this.num = 0;
      this.calcNum = true;
      this.calcBal = true;
    }
    
    public BTNum(byte[] balTern) {
      setup();
      
      if(tritNum == balTern.length) {
       this.balTern = balTern;
       this.calcBal = true; 
      }
      
    }
    
    public BTNum(long num) {
      setup();
      
      //rollover
      if(num > MAX) {
        this.num = MIN + (num%MAX);
      }
      else if(num < MIN) {
        this.num = -(abs(num) -(abs(num)%MAX));
      }
      else {
        this.num = num;
      }
    
      this.calcNum = true;
    }
    public BTNum(String balT) {
      setup();
      //prepare the array
      for(int i = 0; i < tritNum && i < balT.length(); i++) {
        switch(balT.charAt(balT.length() - 1 - i)) {
          case '+' : balTern[i] = 1; break;
          case '-' : balTern[i] =-1; break;
        }
      }
     
      this.calcBal = true; 
    }
    public BTNum(long num, String balT) {
      setup();
      this.num = num;
      //prepare the array
      for(int i = 0; i < tritNum && i < balT.length(); i++) {
        switch(balT.charAt(balT.length() - 1 - i)) {
          case '+' : balTern[i] = 1; break;
          case '-' : balTern[i] =-1; break;
        }
      }
      
      this.calcNum = true;
      this.calcBal = true;
    }
    
    public long getNum() {
      
      if(!calcNum) {
        this.num = balToDec(this.balTern);
        calcNum = true;
      }
      
      return this.num;
    }
    
    public byte[] getBalArray() {
      if(!calcBal) {
        this.balTern = decToBal(this.num, this.tritNum);
      }
      
      byte[] out = new byte[tritNum];
      for(int i = 0; i < tritNum; i++) {
        out[i] = this.balTern[i];
      }
      
      //I dont want a direct version of the array returned
      
      return out;
    }
    
    public BTNum add(BTNum bt) {
      return new BTNum(getNum() + bt.getNum());
    }
    public BTNum sub(BTNum bt) {
      return new BTNum(getNum() - bt.getNum()); 
    }
    public BTNum mul(BTNum bt) {
      return new BTNum(getNum() * bt.getNum());
    }
    public BTNum div(BTNum bt) {
      return new BTNum(getNum() / bt.getNum());
    }
    public BTNum mod(BTNum bt) {
      return new BTNum(mod(getNum(), bt.getNum()));
    }
    public BTNum rem(BTNum bt) {
      return new BTNum(getNum() % bt.getNum());
    }
    public BTNum abs() {
      return new BTNum(abs(getNum()));
    }
    public BTNum nabs() {
      return new BTNum(-abs(getNum()));
    }
    
    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder();
      
      byte[] arr = getBalArray();
      
      for(int i = 0; i < arr.length; i++) {
      
        switch(arr[i]) {
          case 1 : sb.append('+'); break;
          case 0 : sb.append('0'); break;
          case-1 : sb.append('-'); break;
        }
        
      }
      
      return sb.reverse().toString();
    }
    
    private static long balToDec(byte[] balTern) {
      long n = 1;
      
      long numOut = 0;
      
      for(int i = 0; i < balTern.length; i++) {
        numOut += balTern[i]*n;
        n*=3;
      }
      
      return numOut;  
    }
    
    private static byte[] decToBal(long num, int tritNum) {
      
      long n = num;
      byte[] out = new byte[tritNum];
      
      int i = 0;
      
      while(n != 0 && i < tritNum) {
        if(mod(n, 3) == 1) {
          out[i] = 1;
        }
        else if(mod(n, 3) == 2) {
          out[i] = -1;
        }
        
        //0 is already what the bytes are, so it is ignored
        
        n = (n + 1) / 3; //automatically floored
        
        i++;  
      }
      
      return out;  
    }
    
    //getting the abs of the number
    private static long abs(long num) {
      return (num < 0) ? -num : num;
    }
    
    private static long mod(long n, long m) {
      return (n < 0) ? (m - (abs(n) % m) ) %m : (n % m);
    }
    
    private static byte[] flip(byte[] arr) {
      byte[] out = new byte[arr.length];
      for(int i = 0; i < out.length; i++) {
        out[i] = (byte) -arr[i];
      }
      return out;
    }
    
    private static long pow3(int tritNum) {
      int n = 1;
      for(int i = 0; i < tritNum; i++) {
        n *= 3;
      }
      return n;
    }
        
    protected void setup() {
      MAX = (pow3(tritNum) - 1)/2;
      MIN = -MAX;
      this.balTern = new byte[tritNum];
    }
}
