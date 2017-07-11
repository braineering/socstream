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
package com.acmutv.socstream.tool.physics;

/**
 * This class provides all physics-related utilities.
 *
 * @author Giacomo Marciani {@literal <gmarciani@acm.org>}
 * @author Michele Porretta {@literal <mporretta@acm.org>}
 * @since 1.0
 */
public class PhysicsUtil {

  /**
   * The time interval (frequency=200Hz).
   */
  private static final double DELTA_T = 1.0 / 200.0;

  /**
   * The time interval square (frequency=200Hz).
   */
  private static final double DELTA_T_SQUARE = Math.pow(DELTA_T, 2);

  /**
   * The speed conversion factor (10^-10).
   */
  private static final double SPEED_CONVERSION_FACTOR = Math.pow(10, -10);

  /**
   * The acceleration conversion factor (10^-10).
   */
  private static final double ACCELERATION_CONVERSION_FACTOR = Math.pow(10, -10);

  /**
   * Computes the distance.
   * @param v the speed modulo.
   * @param vx the x-speed.
   * @param vy the y-speed.
   * @param a the acceleration modulo.
   * @param ax the x-acceleration.
   * @param ay the y-acceleration.
   * @return the distance.
   */
  public static double computeDistance(long v, long vx, long vy, long a, long ax, long ay) {
    final double vX = v * vx * SPEED_CONVERSION_FACTOR;
    final double vY = v * vy * SPEED_CONVERSION_FACTOR;
    final double aX = a * ax * ACCELERATION_CONVERSION_FACTOR;
    final double aY = a * ay * ACCELERATION_CONVERSION_FACTOR;
    final double distX = vX * DELTA_T + 0.5 * aX * DELTA_T_SQUARE;
    final double distY = vY * DELTA_T + 0.5 * aY * DELTA_T_SQUARE;

    return Math.sqrt(Math.pow(distX, 2) + Math.pow(distY, 2));
  }

  /**
   * Computes the speed.
   * @param v the speed modulo.
   * @param vx the x-speed.
   * @param vy the y-speed.
   * @param a the acceleration modulo.
   * @param ax the x-acceleration.
   * @param ay the y-acceleration.
   * @return the speed.
   */
  public static double computeSpeed(long v, long vx, long vy, long a, long ax, long ay) {
    final double vX = v * vx * SPEED_CONVERSION_FACTOR;
    final double vY = v * vy * SPEED_CONVERSION_FACTOR;
    final double aX = a * ax * ACCELERATION_CONVERSION_FACTOR;
    final double aY = a * ay * ACCELERATION_CONVERSION_FACTOR;
    final double speedX = vX + aX * DELTA_T;
    final double speedY = vY + aY * DELTA_T;

    return Math.sqrt(Math.pow(speedX, 2) + Math.pow(speedY, 2));
  }

  /**
   * Computes the pair (speed,distance).
   * @param v the speed modulo.
   * @param vx the x-speed.
   * @param vy the y-speed.
   * @param a the acceleration modulo.
   * @param ax the x-acceleration.
   * @param ay the y-acceleration.
   * @return the pair (speed,distance).
   */
  public static double[] computeSpeedDistance(long v, long vx, long vy, long a, long ax, long ay) {
    double results[] = new double[2];

    final double vX = v * vx * SPEED_CONVERSION_FACTOR;
    final double vY = v * vy * SPEED_CONVERSION_FACTOR;
    final double aX = a * ax * ACCELERATION_CONVERSION_FACTOR;
    final double aY = a * ay * ACCELERATION_CONVERSION_FACTOR;

    final double speedX = vX + aX * DELTA_T;
    final double speedY = vY + aY * DELTA_T;

    results[0] = Math.sqrt(Math.pow(speedX, 2) + Math.pow(speedY, 2));

    final double distX = vX * DELTA_T + 0.5 * aX * DELTA_T_SQUARE;
    final double distY = vY * DELTA_T + 0.5 * aY * DELTA_T_SQUARE;

    results[1] =  Math.sqrt(Math.pow(distX, 2) + Math.pow(distY, 2));

    return results;
  }
}
