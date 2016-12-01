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
	roboGraphics = new RoboGraphics(reader);
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
    
}
