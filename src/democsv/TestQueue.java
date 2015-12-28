package democsv;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class TestQueue {
    public static void main(String[] args) {
        Queue llist = new LinkedList();
        Queue queueA = new PriorityQueue();
        queueA.add("Element 1");
        queueA.add("Element 2");
        queueA.add("Element 3");
        queueA.add("Element 4");
        
        Iterator iter = queueA.iterator();
        
        while (iter.hasNext()) {            
            String ele = (String)iter.next();
            System.out.println(ele);
        }
        
    }
}
