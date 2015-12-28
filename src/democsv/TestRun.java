package democsv;

import java.util.ArrayList;

public class TestRun {
    static String[] store = {"One", "Two", "Three", "Four"};
   
    public static void main(String[] args) throws RuntimeException{
        for (int i = 0; i< store.length; i++) {
            //ArrayList<String> store[i] = new ArrayList<>();
            System.out.println(store[i]);
        }
    }
    
}
