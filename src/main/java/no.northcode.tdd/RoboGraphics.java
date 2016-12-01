package no.northcode.tdd;

import java.util.Optional;

public class RoboGraphics {

    public enum Direction {
	up,right,down,left;

	public Direction next() {
	    return values()[(ordinal() + 1) % values().length];
        }

	public Direction prev() {
	    return next().next().next();
        }
    }

    CommandReader reader;
    Direction penDirection;
    boolean isPenDown; 
    char currentColor;

    public RoboGraphics(CommandReader reader) {
	this.reader = reader;
	penDirection = Direction.up;
	isPenDown = false;
	currentColor = 'R';
    }

    public void execute(Optional<Command> command) {
	command.ifPresent((cmd) -> cmd.apply(this));
    }

    public Direction getDirection() { return penDirection; }
    public boolean getIsPenDown() { return isPenDown; }
    public char getColor() { return currentColor; }
    
    public void penUp() { isPenDown = false; }
    public void penDown() { isPenDown = true;}
    public void turnRight() { penDirection = penDirection.next(); }
    public void turnLeft() { penDirection = penDirection.prev(); }

    public void selectColorRed() { selectColor('R'); }
    public void selectColorBlue() { selectColor('B'); }
    public void selectColor(char c) { currentColor = c; }

    public void move(int steps) {}
    
}
