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

public class CommandReaderFileTest {

    CommandReader reader;

    @Before public void setup() {
	FileHandler fh = mock(FileHandler.class);
	when(fh.lines()).thenReturn(Stream.of("1","2","3","4"));
	reader = new CommandReader(fh);
    }

    @Test public void testParseFromFile() {
	Stream<Optional<Command>> commands = reader.commands();

	List<Boolean> validLines = Arrays.asList(true,true,true,true);

	assertEquals(String.format("Command parsing failed from file"),
		     validLines,
		     commands.map(Optional::isPresent).collect(Collectors.toList()));
    }
}
