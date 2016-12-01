import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.stream.Stream;
import java.util.stream.Collectors;

import java.util.List;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;

import no.northcode.tdd.*;

public class RoboGraphicsTest {

    RoboGraphics roboGraphics;

    @Before
    public void setup() {
	FileHandler fh = mock(FileHandler.class);
	CommandReader reader = new CommandReader(fh);
	roboGraphics = new RoboGraphics(reader,10);
    }

    @Test public void testPenUp() {
	roboGraphics.execute(Optional.of(RoboGraphics::penUp));
	assertFalse("Pen is not up", roboGraphics.getIsPenDown());
    }

    @Test public void testPenDown() {
	roboGraphics.execute(Optional.of(RoboGraphics::penDown));
	assertTrue("Pen is not down", roboGraphics.getIsPenDown());
    }

    @Test public void testTurnRight() {
	roboGraphics.execute(Optional.of(RoboGraphics::turnRight));
	assertEquals(RoboGraphics.Direction.right, roboGraphics.getDirection());

	roboGraphics.execute(Optional.of(RoboGraphics::turnRight));
	assertEquals(RoboGraphics.Direction.down, roboGraphics.getDirection());

	roboGraphics.execute(Optional.of(RoboGraphics::turnRight));
	roboGraphics.execute(Optional.of(RoboGraphics::turnRight));
	assertEquals(RoboGraphics.Direction.up, roboGraphics.getDirection());
    }

    @Test public void testTurnLeft() {
	roboGraphics.execute(Optional.of(RoboGraphics::turnLeft));
	assertEquals(RoboGraphics.Direction.left, roboGraphics.getDirection());

	roboGraphics.execute(Optional.of(RoboGraphics::turnLeft));
	assertEquals(RoboGraphics.Direction.down, roboGraphics.getDirection());

	roboGraphics.execute(Optional.of(RoboGraphics::turnLeft));
	roboGraphics.execute(Optional.of(RoboGraphics::turnLeft));
	assertEquals(RoboGraphics.Direction.up, roboGraphics.getDirection());
    }

    @Test public void testChangeColor() {
	assertEquals("Wrong starting color!", 'R', roboGraphics.getColor());
	roboGraphics.execute(Optional.of(RoboGraphics::selectColorRed));
	assertEquals("Wrong color!", 'R', roboGraphics.getColor());
	roboGraphics.execute(Optional.of(RoboGraphics::selectColorBlue));
	assertEquals("Wrong color!", 'B', roboGraphics.getColor());
	roboGraphics.execute(Optional.of(RoboGraphics::selectColorRed));
	assertEquals("Wrong color!", 'R', roboGraphics.getColor());
    }

    @Test public void testMove() {
	assertEquals("Wrong starting position!", new Vector2(0,0), roboGraphics.getPosition());
	roboGraphics.execute(Optional.of(RoboGraphics::turnRight));
	roboGraphics.execute(Optional.of((rg) -> rg.move(4)));
	assertEquals("Wrong next position!", new Vector2(4,0), roboGraphics.getPosition());
	roboGraphics.execute(Optional.of(RoboGraphics::turnRight));
	roboGraphics.execute(Optional.of((rg) -> rg.move(4)));
	assertEquals("Wrong next position!", new Vector2(4,4), roboGraphics.getPosition());
    }

    @Test public void testCanvasLines() {
	roboGraphics.execute(Optional.of(RoboGraphics::penDown));
	roboGraphics.execute(Optional.of(RoboGraphics::turnRight));
	roboGraphics.execute(Optional.of((rg) -> rg.move(4)));

	roboGraphics.execute(Optional.of(RoboGraphics::selectColorBlue));
	roboGraphics.execute(Optional.of(RoboGraphics::turnRight));
	roboGraphics.execute(Optional.of((rg) -> rg.move(4)));

	roboGraphics.execute(Optional.of(RoboGraphics::selectColorRed));
	roboGraphics.execute(Optional.of(RoboGraphics::turnLeft));
	roboGraphics.execute(Optional.of((rg) -> rg.move(3)));

	roboGraphics.execute(Optional.of(RoboGraphics::turnLeft));
	roboGraphics.execute(Optional.of((rg) -> rg.move(2)));

	roboGraphics.execute(Optional.of(RoboGraphics::turnLeft));
	roboGraphics.execute(Optional.of((rg) -> rg.move(5)));

	Stream<String> lines = roboGraphics.canvasLines();
	//lines.forEach(System.out::println);

	List<String> validLines = Arrays.asList("RRRRR_____",
						"____B_____",
						"__RRBRRR__",
						"____B__R__",
						"____BRRR__",
						"__________",
						"__________",
						"__________",
						"__________",
						"__________");
	assertEquals("Invalid canvas result!", validLines, lines.collect(Collectors.toList()));
	// wahoo!!
    }

    @Test public void testTerminateProgram() {
	roboGraphics.execute(Optional.of(RoboGraphics::end));

	roboGraphics.execute(Optional.of(RoboGraphics::turnLeft));
	roboGraphics.execute(Optional.of(RoboGraphics::turnLeft));
	roboGraphics.execute(Optional.of(RoboGraphics::turnLeft));

	assertEquals("RoboGraphics kept obeying directions even tho its not supposed to!", RoboGraphics.Direction.up, roboGraphics.getDirection());
    }
}
