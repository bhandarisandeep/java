public class Secrets {
    // VULNERABLE: visible to anyone who decompiles the .class (try: javap -c -constants Secrets)
    private static final String API_KEY = "sk-demo-1234567890";

    public static void main(String[] args) {
        System.out.println("Hardcoded key length: " + API_KEY.length());

        // SECURE: read from the environment (or a secrets manager)
        String key = System.getenv("MY_API_KEY");
        System.out.println(key == null ? "MY_API_KEY not set" : "Key loaded from env");
    }
}
