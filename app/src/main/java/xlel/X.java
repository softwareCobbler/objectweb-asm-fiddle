package xlel;

public class X {

    public static void main(String[] args) {
        System.out.println("Should be run as 'xlel/X2'");
    }

    public Integer originalMethod(String a, int b) throws Throwable {
        int x = 42;
        System.out.println("Original method says x++ = " + (x++));
        System.out.println("Original method says x++ = " + (x++));
        System.out.println("Original method says x++ = " + (x++));
        return x;
    }
}
