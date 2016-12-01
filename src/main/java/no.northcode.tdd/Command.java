package no.northcode.tdd;

import java.lang.FunctionalInterface;

@FunctionalInterface
public interface Command {
    void apply(RoboGraphics rg);
}
