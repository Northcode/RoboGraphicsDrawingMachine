package no.northcode.tdd;

public class Vector2 {
    public int x,y;

    public Vector2(int X,int Y) {
	x=X;y=Y;
    }

    public Vector2 add(Vector2 other) {
	return new Vector2(x + other.x, y + other.y);
    }

    @Override
    public boolean equals(Object other) {
	if (!(other instanceof Vector2)) return false;
	Vector2 othervec = (Vector2)other;
	return x == othervec.x && y == othervec.y;
    }

    @Override
    public String toString() {
	return String.format("[%d,%d]", x, y);
    }

}
