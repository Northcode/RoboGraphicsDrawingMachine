import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runner.RunWith;

import java.util.stream.Stream;
import java.util.stream.Collectors;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Collection;
import java.util.Optional;

import no.northcode.tdd.*;

@RunWith(Parameterized.class)
public class CommandReaderTest {

    private List<String> lines;
    private List<Boolean> validLines;

    public CommandReaderTest(List<String> lines, List<Boolean> validLines) {
	this.lines = lines;
	this.validLines = validLines;
    }

    @Test public void testParseCommands() {
	Stream<Optional<Command>> commands = lines.stream()
	    .map(CommandReader::parseCommand);
	    // .collect(Collectors.toList());

	// Check that we parsed the correct commands correctly and the wrong ones wrong
	assertEquals(String.format("Command parsing failed for: %s",String.join(",",lines)),
		     validLines,
		     commands.map(Optional::isPresent).collect(Collectors.toList()));
    }

    @Parameterized.Parameters
	public static Collection parameters() {
	return Arrays.asList(new Object[][] {
		{ Arrays.asList("1","2","5,5"), Arrays.asList(true,true,true) },
		{ Arrays.asList("asdf","3","5.asdf","4","5 , 5"), Arrays.asList(false,true,false,true,false) },
		{ Arrays.asList(), Arrays.asList() },
		{ Arrays.asList("1","2","3","4","5,5","6","7","8","9"), listOf(9,true) }
	    });
    }

    public static <T> List<T> listOf(int length, T initial) {
	List<T> list = new ArrayList<T>(length);
	for (int i = 0; i < length; i++)
	    list.add(initial);
	return list;
    }
}
