package no.northcode.tdd;

import java.util.stream.Stream;

public interface FileHandler {
    boolean openFile(String name);
    String nextLine();
    boolean hasMore();

    Stream<String> lines();
}
