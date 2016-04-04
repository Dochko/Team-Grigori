package Utilities;

public class Projectiles {


    public double Cosine_A(Point pointA, Point pointB){
        int sideA = pointA.x - pointB.x;
        int sideB = pointA.y - pointB.y;
        double hypotenuse = Math.sqrt(Math.pow(sideA,2) + Math.pow(sideB,2));

        return sideA / hypotenuse;
    }
}
