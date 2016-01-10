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
package mrdshinse.sql2xlsx.consts;

import java.io.File;

/**
 *
 * @author mrdShinse
 */
public interface Consts {

    final String CURRENT_DIR = System.getProperty("user.dir");
    final String DELIMITER = File.separator;

    final String CONFIG = CURRENT_DIR + DELIMITER + "config";
    final String PROP_SQL = CONFIG + DELIMITER + "sqlProp.json";
    final String DIR_QUERY = CURRENT_DIR + DELIMITER + "data" + DELIMITER + "query";
    final String DIR_TSV = CURRENT_DIR + DELIMITER + "data" + DELIMITER + "tmp" + DELIMITER + "tsv";
    final String DIR_TEMPLATE = CURRENT_DIR + DELIMITER + "data" + DELIMITER + "tmp" + DELIMITER + "template";
    final String DIR_RESULT = CURRENT_DIR + DELIMITER + "data" + DELIMITER + "result";

}
