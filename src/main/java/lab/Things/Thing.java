package lab.Things;

import java.io.Serializable;

public abstract class Thing implements Serializable {
    private int x;
    private int y;

    public Thing() {
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
