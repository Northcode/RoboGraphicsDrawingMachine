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

public class RoboGraphicsFileTest {
    RoboGraphics roboGraphics;

    @Before
    public void setup() {
	FileHandler fh = new MyFileHandler();
	fh.openFile("testRbCommands.txt");
	CommandReader reader = new CommandReader(fh);
	roboGraphics = new RoboGraphics(reader,10);
    }

    @Test public void testCorrectCommands() {
	roboGraphics.executeFromReader();
	
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
    }
}
