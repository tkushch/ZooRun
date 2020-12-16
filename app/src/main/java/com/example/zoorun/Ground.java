package com.example.zoorun;

public class Ground implements Movable, Drawable {
    private int v;
    private Line[] lines;

    public Ground(int RX, int RY, int LINES_LENGTH, int COUNT_OF_LINES, int DISTANCE, int v) {
        lines = new Line[COUNT_OF_LINES * 2];
        int line_y = 0;
        for (int i = 0; i < COUNT_OF_LINES * 2; i += 2) {
            lines[i] = new Line(333 * RX, line_y);
            lines[i + 1] = new Line(666 * RX, line_y);
            line_y += LINES_LENGTH + DISTANCE;
        }
        this.v = v;
    }

    public void move() {
    }

    @Override
    public void draw() {

    }
}
