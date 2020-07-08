package BalancedTernaryArithmetic;

import BalancedTernaryArithmetic.*;
import BalancedTernaryArithmetic.BTNum;
import BalancedTernaryArithmetic.Ditryte;


public class TestTern {

  public static void main(String[] args) {
   BTNum bt  = new BTNum(27);
   Triad tr = new Triad(13);
   Tryte ty = new Tryte(200);
   Ditryte dt = new Ditryte(2000);
   
   System.out.println("Super class: " + bt);
   System.out.println("Triad:       " + tr);
   System.out.println("Tryte:       " + ty);
   System.out.println("Ditryte:     " + dt);
   
   System.out.println("Triad given 28 rollover: " + new Triad(-10));
   
  }
}
