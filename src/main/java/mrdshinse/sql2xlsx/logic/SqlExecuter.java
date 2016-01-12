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

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Arrays;
import mrdshinse.sql2xlsx.command.SqlServerSqlCmd;
import mrdshinse.sql2xlsx.consts.Consts;
import mrdshinse.sql2xlsx.helper.RuntimeExecuter;
import mrdshinse.sql2xlsx.json.SqlProperty;
import mrdshinse.sql2xlsx.util.JsonUtil;

/**
 *
 * @author mrdShinse
 */
public class SqlExecuter {

    private SqlProperty prop;
    private RuntimeExecuter runtime;

    public SqlExecuter() {
        prop = JsonUtil.parse(new File(Consts.PROP_SQL), SqlProperty.class);
        runtime = new RuntimeExecuter();
    }

    public void exe() {
        File[] sqlFiles = new File(Consts.DIR_QUERY).listFiles(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.isFile() && f.getName().endsWith("sql");
            }
        });

        if (sqlFiles == null) {
            return;
        }
        for (File f : Arrays.asList(sqlFiles)) {
            String fileName = f.getName();
            execCmd(fileName.substring(0, fileName.length() - 4));
        }
    }

    private void execCmd(String fileName) {
        String[] cmd = new SqlServerSqlCmd(prop, fileName).getCommand();

        try {
            runtime.execCmd(cmd);
        } catch (IOException | InterruptedException e) {
            System.out.println("SQLコマンドの実行に失敗しました。");
        }
    }
}
