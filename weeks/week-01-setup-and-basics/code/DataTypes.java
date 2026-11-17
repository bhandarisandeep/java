public class DataTypes {
    public static void main(String[] args) {
        byte b = 127;
        short s = 32_000;
        int i = 2_000_000_000;
        long l = 9_000_000_000L;           // note the L suffix
        float f = 3.14f;                   // note the f suffix
        double d = 3.141592653589793;
        char c = 'J';
        boolean ok = true;
        System.out.println(b + " " + s + " " + i + " " + l);
        System.out.println(f + " " + d + " " + c + " " + ok);
        System.out.println("int max  = " + Integer.MAX_VALUE);
        System.out.println("long max = " + Long.MAX_VALUE);
        String str = null;                 // references can be null
        // int x = null;                   // compile error: primitives cannot be null
        System.out.println("str = " + str);
    }
}
