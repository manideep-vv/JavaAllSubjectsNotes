package java8;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HappyNumbers {
    public static  String getHappyNumbers(int start , int end){
        StringBuffer  fn=new StringBuffer();
        for (int i = start; i <= end; i++) {
            Set<Integer> set = new HashSet<Integer>();
            int num = i;
            while (set.add(num)) {
                int sum = 0;
                while (num > 0) {
                    int rem = num % 10;
                    sum += rem * rem;
                    num /= 10;
                }
                if (sum == 1) {
                    fn.append(i+" ");
                    break;
                }
                num = sum;
            }
        }
        fn.trimToSize();
        return fn.toString();
    }

    public static void main(String[] args) {
        String fn = getHappyNumbers(1, 2000);

//        happyNumbers.stream().forEach(ele->fn.append(ele+" "));
        System.out.println(fn);
//        fn.trimToSize();
//        System.out.println(fn.trimToSize());
    }
}

