package democsv;

import java.util.ArrayList;
import java.util.List;

public class CodeTest {

    public static void main(String[] args){
        List<List<String>> dict = new ArrayList<>();
        ArrayList<String> dict2 = new ArrayList<>();
        
        dict2.add("One");
        dict2.add("Two");
        dict2.add("Three");
        dict2.add("Four");
        
//        SSystem.out.println(dict2.get());
        int len = 5;
        
        while (dict.size() < len) {            
            dict.add(new ArrayList<>());
               
        }
        System.out.println(dict.size());
        
        dict.get(1).add("Test");
        
        System.out.println(dict.get(1).get(0));
        
    }
}

