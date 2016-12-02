package no.northcode.tdd;

import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class RoboGraphics {

    @FunctionalInterface
    public interface ExecCommandErrorHandler {
	void apply(RoboGraphics rg,String reason);
    }

    @FunctionalInterface
    public interface MoveErrorHandler {
	void apply(RoboGraphics rg, Vector2 triedPosition);
    }

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

    public Optional<Exception> execute(Optional<Command> command) {
	if (!terminated) {
	    return command.flatMap(cmd -> cmd.apply(this));
	} else return Optional.of(new Exception("Program terminated, cannot continue"));
    }

    public Direction getDirection() { return penDirection; }
    public Vector2 getPosition() { return penPosition; }
    public boolean getIsPenDown() { return isPenDown; }
    public char getColor() { return currentColor; }
    
    public Optional<Exception> penUp() { isPenDown = false; return Optional.empty(); }
    public Optional<Exception> penDown() {
	isPenDown = true;
	// when we place the pen we should draw
	draw(); return Optional.empty();
    }
    public Optional<Exception> turnRight() { penDirection = penDirection.next();  return Optional.empty();}
    public Optional<Exception> turnLeft() { penDirection = penDirection.prev();  return Optional.empty();}

    public Optional<Exception> selectColorRed() { selectColor('R');  return Optional.empty();}
    public Optional<Exception> selectColorBlue() { selectColor('B');  return Optional.empty();}
    public void selectColor(char c) { currentColor = c; }

    public Optional<Exception> move(int steps) {
	// try to move everystep, exit on the first error and return the error. (keeps evaluating as long as move() returns "Optional.empty()")
	return IntStream.rangeClosed(1,steps)
	    .mapToObj(i -> move())
	    .flatMap(o -> o.isPresent() ? Stream.of(o.get()) : Stream.empty())
	    .findFirst();
    }

    public Optional<Exception> move() {
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
	if (newpos.x < 0 || newpos.x >= canvas.length || newpos.y < 0 || newpos.y >= canvas.length) {
	    return Optional.of(new Exception("Cannot move to position outside grid")); // if we can't move, return exception
	}

	penPosition = newpos;

	draw();

	return Optional.empty();
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

    public Optional<Exception> printCanvas() {
	canvasLines().forEach(System.out::println);
	return Optional.empty();
    }

    public Optional<Exception> draw() {
	if (isPenDown && getCurrentCell() == BLANK_CELL) {
	    setCurrentCell(currentColor);
	}
	return Optional.empty();
    }

    public Optional<Exception> end() {
	terminated = true;
	return Optional.empty();
    }
    
}
