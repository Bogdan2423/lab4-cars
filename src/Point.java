import java.util.Random;
import static java.lang.Math.min;

public class Point {
    public int type = 0;
    public Point next;
    public boolean moved = false;
    public int speed = 1;
    public static double p = 0.5;
    Random rand = new Random();
    int randInt;

    public void move() {
        if (type==1 && !moved) {
            if (speed < 5)
                speed += 1;
            speed=min(speed,getDistanceToNext());
            randInt=rand.nextInt(100);
            if (randInt<=p*100 && speed>=1)
                speed -= 1;
            if (speed>0) {
                moveBy(speed, speed);
                type = 0;
                speed = 0;
                moved = true;
            }
        }
    }

    public int getDistanceToNext(){
        if (next.type==1)
            return 0;

        if(next.getDistanceToNext()>=4)
            return 5;

        return next.getDistanceToNext()+1;
    }

    public void moveBy(int x, int speed){
        if (x==1) {
            next.type = 1;
            next.speed = speed;
            next.moved = true;
        }
        else{
            next.moveBy(x-1, speed);
        }
    }

    public void clicked() {
        type = 1;
    }

    public void clear() {
        type = 0;
    }
}

