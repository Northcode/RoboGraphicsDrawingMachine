import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import no.northcode.tdd.*;

public class MyFileHandlerTest extends FileHandlerTest {
    @Override @Before public void setup() {
	fileHandler = new MyFileHandler();
    }
}
