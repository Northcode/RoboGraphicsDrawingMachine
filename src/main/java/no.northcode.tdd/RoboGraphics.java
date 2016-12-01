package no.northcode.tdd;

import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.Arrays;
import java.util.List;

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

    public static final char BLANK_CELL = '_';

    CommandReader reader;
    Direction penDirection;
    Vector2 penPosition;
    boolean isPenDown; 
    char currentColor;
    boolean terminated;

    char[][] canvas;

    public RoboGraphics(CommandReader reader, int canvasSize) {
	this.reader = reader;
	penDirection = Direction.up;
	penPosition = new Vector2(0,0);
	isPenDown = false;
	currentColor = 'R';
	terminated = false;

	canvas = new char[canvasSize][canvasSize];
	clearCanvas();
    }

    public void clearCanvas() {
	for (int i = 0; i < canvas.length; i++)
	    for (int k = 0; k < canvas[i].length; k++)
		canvas[i][k] = BLANK_CELL;
    }

    public void executeFromReader() {
	reader.commands().forEach((cmd) -> this.execute(cmd));
    }

    public void execute(List<Optional<Command>> commands) {
	commands.stream().forEach((cmd) -> this.execute(cmd));
    }

    public void execute(Optional<Command> command) {
	if (!terminated) {
	    command.ifPresent((cmd) -> cmd.apply(this));
	}
    }

    public Direction getDirection() { return penDirection; }
    public Vector2 getPosition() { return penPosition; }
    public boolean getIsPenDown() { return isPenDown; }
    public char getColor() { return currentColor; }
    
    public void penUp() { isPenDown = false; }
    public void penDown() {
	isPenDown = true;
	// when we place the pen we should draw
	draw();
    }
    public void turnRight() { penDirection = penDirection.next(); }
    public void turnLeft() { penDirection = penDirection.prev(); }

    public void selectColorRed() { selectColor('R'); }
    public void selectColorBlue() { selectColor('B'); }
    public void selectColor(char c) { currentColor = c; }

    public void move(int steps) {
	IntStream.rangeClosed(1,steps)
	    .forEach(i -> move());
    }

    public void move() {
	/* 
	   up    =>  0,-1
	   right =>  1, 0
	   down  =>  0, 1
	   left  => -1, 0
	*/

	// Find direction to move
	
	Vector2 dirMod;
	switch (penDirection) {
	case up:
	    dirMod = new Vector2(0,-1);
	    break;
	case right:
	    dirMod = new Vector2(1,0);
	    break;
	case down:
	    dirMod = new Vector2(0,1);
	    break;
	case left:
	    dirMod = new Vector2(-1,0);
	    break;
	default:
	    dirMod = new Vector2(0,0);
	}

	// move
	Vector2 newpos = penPosition.add(dirMod);
	if (newpos.x < 0 || newpos.x >= canvas.length || newpos.y < 0 || newpos.y >= canvas.length)
	    return; // if we can't move, do nothing
	penPosition = newpos;

	draw();
    }

    public char getCurrentCell() {
	return canvas[penPosition.y][penPosition.x];
    }

    public void setCurrentCell(char c) {
	canvas[penPosition.y][penPosition.x] = c;
    }

    public Stream<String> canvasLines() {
	return Arrays.stream(canvas).map((lineArr) -> new String(lineArr));
    }

    public void printCanvas() {
	canvasLines().forEach(System.out::println);
    }

    public void draw() {
	if (isPenDown && getCurrentCell() == BLANK_CELL) {
	    setCurrentCell(currentColor);
	}
    }

    public void end() {
	terminated = true;
    }
    
}
