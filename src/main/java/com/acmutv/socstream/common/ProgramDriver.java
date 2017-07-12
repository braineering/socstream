/*
  The MIT License (MIT)

  Copyright (c) 2017 Giacomo Marciani and Michele Porretta

  Permission is hereby granted, free of charge, to any person obtaining a copy
  of this software and associated documentation files (the "Software"), to deal
  in the Software without restriction, including without limitation the rights
  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
  copies of the Software, and to permit persons to whom the Software is
  furnished to do so, subject to the following conditions:


  The above copyright notice and this permission notice shall be included in
  all copies or substantial portions of the Software.


  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.  IN NO EVENT SHALL THE
  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
  THE SOFTWARE.
 */
package com.acmutv.socstream.common;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.TreeMap;

/**
 * The general program driver.
 * @author Giacomo Marciani {@literal <gmarciani@acm.org>}
 * @author Michele Porretta {@literal <mporretta@acm.org>}
 * @since 1.0
 */
@Data
@NoArgsConstructor
public class ProgramDriver {

  /**
   * The programs description.
   */
  Map<String,ProgramDriver.ProgramDescription> programs = new TreeMap<>();

  /**
   * Adds a program to the driver list.
   * @param name the program name.
   * @param mainClass the program main class.
   * @param description the program description.
   * @throws Throwable when the program cannot be added to the driver.
   */
  public void addClass(String name, Class<?> mainClass, String description) throws Throwable {
    this.programs.put(name, new ProgramDriver.ProgramDescription(mainClass, description));
  }

  /**
   * Prints program usage.
   * @param programs the list of programs to print the usage of.
   */
  private static void printUsage(Map<String,ProgramDriver.ProgramDescription> programs) {
    System.out.println("Valid program names are:");
    programs.forEach((name, program) -> System.out.printf("%s: %s\n", name, program.getDescription()));
  }

  /**
   * The driver main method.
   * @param args the arguments.
   * @return the exit code.
   * @throws Throwable when the driver cannot run.
   */
  public int run(String[] args) throws Throwable {
    if (args.length == 0) {
      System.out.println("A program name must be given as the first argument.");
      printUsage(this.programs);
      return -1;
    } else {
      ProgramDriver.ProgramDescription pgm = this.programs.get(args[0]);
      if (pgm == null) {
        System.out.println("Unknown program '" + args[0] + "' chosen.");
        printUsage(this.programs);
        return -1;
      } else {
        String[] new_args = new String[args.length - 1];

        System.arraycopy(args, 1, new_args, 0, args.length - 1);

        pgm.invoke(new_args);

        return 0;
      }
    }
  }

  /**
   * The program description for a program to be executed by the driver.
   */
  @Data
  private static class ProgramDescription {

    /**
     * The program main method.
     */
    private Method main;

    /**
     * The program description.
     */
    private String description;

    /**
     * The program parameters type.
     */
    static final Class<?>[] paramTypes = new Class[]{String[].class};

    /**
     * Creates a new program descriptor.
     * @param mainClass the program main class.
     * @param description the program description.
     * @throws SecurityException when the program cannot be executed.
     * @throws NoSuchMethodException when the program main method does not exist.
     */
    public ProgramDescription(Class<?> mainClass, String description) throws SecurityException, NoSuchMethodException {
      this.main = mainClass.getMethod("main", paramTypes);
      this.description = description;
    }

    /**
     * Executes the program main method.
     * @param args the arguments to pass to the program main method.
     * @throws Throwable when the program main method cannot be executed.
     */
    public void invoke(String[] args) throws Throwable {
      try {
        this.main.invoke((Object)null, new Object[]{args});
      } catch (InvocationTargetException exc) {
        throw exc.getCause();
      }
    }
  }
}
