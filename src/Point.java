import java.util.Random;
import static java.lang.Math.min;

public class Point {
    public static Integer[] types = {0, 1, 2, 3, 5};
    private int type = 0;
    public Point next;
    public Point prev;
    public Point leftBeltPoint;
    public Point rightBeltPoint;
    public boolean leftBelt=false;
    public boolean rightBelt=false;
    public boolean moved = false;
    public int speed = 0;
    public int maxSpeed = 0;

    public void move() {
        if ((type==1 || type==2 || type==3) && !moved) {
            if (rightBelt) {
                if (speed < maxSpeed && getDistanceToNext(0) <= maxSpeed && leftBeltPoint.getDistanceToPrev(0) >= maxSpeed
                        && leftBeltPoint.getDistanceToNext(0) >= speed) {
                    leftBeltPoint.moveBy(speed - 1, speed + 1, type);
                    type = 0;
                    speed = 0;
                    moved = true;
                    return;
                }
            }
            if (leftBelt){
                if (rightBeltPoint.getDistanceToPrev(0) >= maxSpeed && getDistanceToPrev(0) >= maxSpeed
                        && rightBeltPoint.getDistanceToNext(0) >= speed) {
                    rightBeltPoint.moveBy(speed - 1, speed, type);
                    type = 0;
                    speed = 0;
                    moved = true;
                    return;
                }
            }

            if (speed < maxSpeed)
                speed += 1;
            speed=min(speed,getDistanceToNext(0));
            if (speed>0) {
                moveBy(speed, speed, type);
                type = 0;
                speed = 0;
                moved = true;
            }
        }
    }

    public int getDistanceToNext(int dist){
        if (next.type==1 || next.type==2 || next.type==3)
            return dist;

        if(dist>=6)
            return 7;

        return next.getDistanceToNext(dist+1);
    }

    public int getDistanceToPrev(int dist){
        if (prev.type==1 || prev.type==2 || prev.type==3)
            return dist;

        if(dist>=6)
            return 7;

        return prev.getDistanceToPrev(dist+1);
    }



    public void moveBy(int x, int speed, int type){
        if (x<=0)
            return;
        if (x==1) {
            next.setType(type);
            next.speed = speed;
            next.moved = true;
        }
        else{
            next.moveBy(x-1, speed, type);
        }
    }

    public void clicked() {
        this.type = 0;
    }

    public void setType(int type){
        this.type=type;
        if (type==1)
            maxSpeed=speed=3;
        if (type==2)
            maxSpeed=speed=5;
        if (type==3)
            maxSpeed=speed=7;
    }
    public int getType(){return type;}

    public void clear() {
        type = 0;
    }
}

