package BalancedTernaryArithmetic;

import BalancedTernaryArithmetic.*;
import BalancedTernaryArithmetic.BTNum;
import BalancedTernaryArithmetic.Ditryte;
import java.util.Arrays;


public class TestTern {

  public static void main(String[] args) {
   System.out.println(Arrays.toString(BTNum.decToBal(7, 6)));
   
   System.out.println(Arrays.toString(BTNum.decToBal(-7, 6)));
   System.out.println(new Triad(-14));
  }
}
