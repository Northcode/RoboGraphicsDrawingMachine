import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.stream.Stream;
import java.util.stream.Collectors;

import java.util.List;
import java.util.Arrays;
import java.util.Iterator;

import no.northcode.tdd.*;

public class FileHandlerTest {

    FileHandler fileHandler;

    @Before
    public void setup() {
	// need Mockito for mocking..,
	fileHandler = mock(FileHandler.class);
	when(fileHandler.openFile("test.txt")).thenReturn(true);
	when(fileHandler.hasMore()).thenReturn(true,true,false);
	when(fileHandler.nextLine()).thenReturn("1","2");
	when(fileHandler.lines()).thenReturn(Stream.of("1","2"));
    }

    @Test
    public void testOpenFile() {
	assertTrue("Did not open file test.txt",fileHandler.openFile("test.txt"));
	assertFalse("Somehow opened non-existent file bob.txt?",fileHandler.openFile("bob.txt"));
    }

    @Test public void testHasMore() {
	fileHandler.openFile("test.txt"); // needed for hasMore to work
	assertTrue("hasMore failed for first line", fileHandler.hasMore());
	fileHandler.nextLine();
	assertTrue("hasMore failed for second line", fileHandler.hasMore());
	fileHandler.nextLine();
	assertFalse("hasMore somehow succeeded for third line", fileHandler.hasMore());
    }

    @Test public void testNextLine() {
	fileHandler.openFile("test.txt");
	assertEquals(fileHandler.nextLine(),"1");
	assertEquals(fileHandler.nextLine(),"2");
    }
    
    @Test public void testLines() {
	fileHandler.openFile("test.txt");

	List<String> expected = Arrays.asList("1","2");
	Stream<String> actual = fileHandler.lines();
	// List<String> actual   = fileHandler.lines().collect(Collectors.toList());

	// Iterate over each list and compare
	Iterator<String> expItr = expected.iterator();

	actual.forEach((line) -> {
		if (expItr.hasNext()) {
		    assertEquals(expItr.next(), line);
		} else {
		    fail("too many lines!");
		}
	    });
	assertFalse("Not enough lines!" , expItr.hasNext());
    }
}
