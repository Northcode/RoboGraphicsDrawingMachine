package no.northcode.tdd;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileReader;
import java.util.Scanner;

import java.util.stream.Stream;

import java.io.IOException;
import java.io.FileNotFoundException;

public class MyFileAndStdInHandler extends MyFileHandler {
    BufferedReader stdinReader;

    public MyFileAndStdInHandler() {
	super();
	stdinReader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public Stream<String> lines() {
	return Stream.concat(super.lines(),stdinReader.lines());
    }
}
