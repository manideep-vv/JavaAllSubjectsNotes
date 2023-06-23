package java8;

import java.util.Scanner;

public class prieNumbers {
    public static void main(String[] args) {
        System.out.println("abc".hashCode());
        String x = Integer.toHexString(96354);
        System.out.println(x);
//        System.out.println(getAllPrimes(1,2000));
    }
public static String getAllPrimes(int start,int end) {
    StringBuffer fn = new StringBuffer();
    for (int i = start; i <= end; i++) {
        if (isPrime(i)) {
            fn.append(i + " ");
        }
    }
    fn.trimToSize();
    return fn.toString();
}
    public static boolean isPrime(int n) {
        if (n <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

}
