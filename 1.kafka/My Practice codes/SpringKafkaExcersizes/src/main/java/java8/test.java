package java8;

import java.util.Arrays;

public class test {
    public static void main(String[] args) {
        int[] a=new int[]{1,2,3};
        System.out.println(Arrays.stream(a).max().getAsInt());
    }
}
