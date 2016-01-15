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
package mrdShinse.sql2xlsx;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import mrdshinse.sql2xlsx.Cui;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

/**
 *
 * @author mrdShinse
 */
public class CuiSteps {

    ByteArrayOutputStream out;

    @Given("初期化")
    public void initialize() {
        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
    }

    @When("オプション無しで実行する")
    public void executeWithoutOption() {
        Cui.main(null);
    }

    @Then("ヘルプがシステム出力に表示される")
    public void outputtedHelp() {
        assertThat(out.toString(), is(
                "autoSql init -> create configuration file and directory."
                + System.lineSeparator()
                + "autoSql exe -> execute command by configration."
                + System.lineSeparator())
        );
    }
}
