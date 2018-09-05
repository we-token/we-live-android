package show.we.lib.widget.animation;

/**
 * @author ll
 * @version 1.0.0
 */
public class Shape {
    public float x;
    public float y;
    public float destX;
    public float destY;
    public float sx;
    public float sy;

    public void update() {
        x += sx;
        y += sy;
        if (sx > 0 && x > destX) {
            x = destX;
        }
        if (sx < 0 && x < destX) {
            x = destX;
        }

        if (sy > 0 && y > destY) {
            y = destY;
        }
        if (sy < 0 && y < destY) {
            y = destY;
        }
    }
}
