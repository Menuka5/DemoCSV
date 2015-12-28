package democsv;

import java.util.Scanner;

public class Solution {

    public static void main(String[] args) {
        Scanner iter = new Scanner(System.in);

        int val = iter.nextInt();

        for (int i = 0; i < val; i++) {
            int target = iter.nextInt();
            int sum = 0;
            int meter = 0;
            int x = 1;
            while (meter < target) {
                sum += x * 3;
                //System.out.println(x*3);
                x++;
                meter = x * 3;
            }
            // 5 * 
            
            int count = 0;
            int y = 1;
            
            while (count < target) {                
                int chk = y*5;
                if ((chk % 3) != 0) {
                    sum += chk;
//                    System.out.println(chk);
                }
                y++;
                count += y*5;
            }
            
            System.out.println(sum);
        }

    }

}
