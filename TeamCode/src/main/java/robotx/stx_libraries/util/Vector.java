package robotx.stx_libraries.util;

public class Vector {

    double x;
    double y;

    public Vector(double x, double y){
        this.x = x;
        this.y = y;
    }

    public static Vector subtractVectors(Vector vec1, Vector vec2){
        return new Vector(vec1.x - vec2.x, vec1.y - vec2.y);
    }

    public static double crossProduct(Vector vec1, Vector vec2){
        return vec1.x * vec2.y - vec1.y * vec2.x;
    }
}
