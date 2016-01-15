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
package mrdshinse.sql2xlsx.logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import mrdshinse.sql2xlsx.consts.Consts;
import mrdshinse.sql2xlsx.dto.SqlProperty;
import mrdshinse.sql2xlsx.util.JsonUtil;

/**
 *
 * @author mrdShinse
 */
public class Initializer {

    private SqlProperty sqlProp;

    public void exe() {
        createProperty();
        createDirectory();
    }

    private void createProperty() {
        sqlProp = new SqlProperty();

        InputHelper helper = new InputHelper();

        sqlProp.setDbType(helper.getInput("database (ex.) mysql"));
        sqlProp.setServer(helper.getInput("server name"));
        sqlProp.setDbName(helper.getInput("database name"));
        sqlProp.setUser(helper.getInput("user name"));
        sqlProp.setPassword(helper.getInput("password"));
    }

    private void createDirectory() {
        new File(Consts.CONFIG).mkdirs();
        JsonUtil.createFile(sqlProp, new File(Consts.PROP_SQL));

        new File(Consts.DIR_QUERY).mkdirs();
        new File(Consts.DIR_TSV).mkdirs();
        new File(Consts.DIR_TEMPLATE).mkdirs();
        new File(Consts.DIR_RESULT).mkdirs();
    }

    /**
     * helper class of standard input.
     */
    class InputHelper {

        BufferedReader br;

        public InputHelper() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        public String getInput() {
            try {
                return br.readLine();
            } catch (IOException ex) {
                return "";
            }
        }

        public String getInput(String str) {
            System.out.println("input " + str);
            return getInput();
        }

    }
}
