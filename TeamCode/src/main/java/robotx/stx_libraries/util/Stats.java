package robotx.stx_libraries.util;

public final class Stats {
    public static double bind(double value, double min, double max){
        return Math.max(min, Math.min(max, value));
    }

    public static double bind(float value, float min, float max){
        return Math.max(min, Math.min(max, value));
    }

    public static double bind(long value, long min, long max){
        return Math.max(min, Math.min(max, value));
    }

    public static int bind(int value, int min, int max){
        return Math.max(min, Math.min(max, value));
    }

    public static double maxAbs(double a, double b) {
        final double absA = Math.abs(a);
        final double absB = Math.abs(b);

        if (absA > absB) return a;
        return b;
    }

    public static float maxAbs(float a, float b) {
        final float absA = Math.abs(a);
        final float absB = Math.abs(b);

        if (absA > absB) return a;
        return b;
    }

    public static int maxAbs(int a, int b) {
        final int absA = Math.abs(a);
        final int absB = Math.abs(b);

        if (absA > absB) return a;
        return b;
    }

    public static long maxAbs(long a, long b) {
        final long absA = Math.abs(a);
        final long absB = Math.abs(b);

        if (absA > absB) return a;
        return b;
    }

    public static double minAbs(double a, double b) {
        final double absA = Math.abs(a);
        final double absB = Math.abs(b);

        if (absA < absB) return a;
        return b;
    }

    public static float minAbs(float a, float b) {
        final float absA = Math.abs(a);
        final float absB = Math.abs(b);

        if (absA < absB) return a;
        return b;
    }

    public static int minAbs(int a, int b) {
        final int absA = Math.abs(a);
        final int absB = Math.abs(b);

        if (absA < absB) return a;
        return b;
    }

    public static long minAbs(long a, long b) {
        final long absA = Math.abs(a);
        final long absB = Math.abs(b);

        if (absA < absB) return a;
        return b;
    }

}
