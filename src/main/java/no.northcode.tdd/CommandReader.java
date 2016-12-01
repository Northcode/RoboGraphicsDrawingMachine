package no.northcode.tdd;

import java.lang.FunctionalInterface;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

import java.util.stream.Stream;

public class CommandReader {

    FileHandler fileHandler;

    // Dependency injection! Loose coupling!
    public CommandReader(FileHandler fileHandler) {
	this.fileHandler = fileHandler;
    }

    public Stream<Optional<Command>> commands() {
	// grab all the lines from fileHandler as a Stream, then map over the stream using parseCommand
	return fileHandler.lines().map(CommandReader::parseCommand);
    }

    static Map<String, Command> validCommands = new HashMap<String, Command>();

    static {
	validCommands.put("1", RoboGraphics::penUp);
	validCommands.put("2", RoboGraphics::penDown);
	validCommands.put("3", RoboGraphics::turnRight);
	validCommands.put("4", RoboGraphics::turnLeft);
	validCommands.put("6", RoboGraphics::selectColorRed);
	validCommands.put("7", RoboGraphics::selectColorBlue);
	validCommands.put("8", RoboGraphics::printCanvas);
	validCommands.put("9", RoboGraphics::end);
    }
    
    public static Optional<Command> parseCommand(String line) {
	if (validCommands.containsKey(line)) {
	    return Optional.of(validCommands.get(line));
	} else if (line.startsWith("5,")) {
	    try {
		int steps = Integer.parseInt(line.substring(2));
		return Optional.of((rg) -> rg.move(steps));
	    } catch (Exception ex) {
		return Optional.empty();
	    }
	} else {
	    return Optional.empty();
	}
    }
    
}

