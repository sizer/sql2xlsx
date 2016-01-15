/*
 * The MIT License
 *
 * Copyright 2016 mrdShinse.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package mrdshinse.sql2xlsx;

import mrdshinse.sql2xlsx.facade.Execution;
import mrdshinse.sql2xlsx.facade.Facade;
import mrdshinse.sql2xlsx.facade.Help;
import mrdshinse.sql2xlsx.facade.Initialization;

/**
 * CommandLine execution class having main method.
 *
 * @author mrdShinse
 */
public class Cui {

    /**
     * Commandline option
     */
    private String[] options;
    /**
     * Facade class of logics;
     */
    private Facade facade;

    /**
     * <pre>
     * Constractor
     * It's private for called by only main method.
     * </pre>
     *
     * @param options commandline options
     */
    private Cui(String[] options) {
        this.options = options;
    }

    /**
     * main method
     *
     * @param args arguments
     */
    public static void main(String[] args) {
        new Cui(null).exe();
    }

    /**
     * <pre>
     * execution method.
     * branching by options.
     * </pre>
     */
    private void exe() {

        if (options == null || options.length != 1) {
            facade = new Help();
        } else if (options[0].equals("init")) {
            facade = new Initialization();
        } else if (options[0].equals("exe")) {
            facade = new Execution();
        }

        facade.exe();

    }

}
