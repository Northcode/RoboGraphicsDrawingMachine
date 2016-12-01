package no.northcode.tdd;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

import java.util.stream.Stream;

import java.io.IOException;
import java.io.FileNotFoundException;

public class MyFileHandler implements FileHandler {

    public BufferedReader inputReader;

    public MyFileHandler() {}

    public boolean openFile(String name) {
	try {
	    inputReader = new BufferedReader(new FileReader(name));
	    return true;
	} catch (FileNotFoundException ex) {
	    return false;
	}
    }

    public String nextLine() {
	try {
	    return inputReader.readLine();
	} catch (IOException ex) {
	    return null;
	}
    }

    public boolean hasMore() {
	try {
	    return inputReader.ready();
	} catch (IOException ex) {
	    return false;
	}
    }

    public Stream<String> lines() {
	return inputReader.lines();
    }
}
