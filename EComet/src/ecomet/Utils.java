package ecomet;

public class Utils {
    public static void sleepMillis(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException ignored) { Thread.currentThread().interrupt(); }
    }
}
