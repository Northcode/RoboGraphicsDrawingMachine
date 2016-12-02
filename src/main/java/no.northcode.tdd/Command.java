package no.northcode.tdd;

import java.util.Optional;

import java.lang.FunctionalInterface;

/**
   Command inteface
   
   "In programming, if you throw an exception, kittens die" - Mario Fusco
   Therefore, when a command is executed, it returns and Exception if execution failed, if not returns Optional.none()
 */
@FunctionalInterface
public interface Command {
    Optional<Exception> apply(RoboGraphics rg);
}
