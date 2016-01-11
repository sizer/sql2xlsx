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
package mrdshinse.sql2xlsx.command;

import mrdshinse.sql2xlsx.consts.Consts;
import mrdshinse.sql2xlsx.json.SqlProperty;

/**
 *
 * @author mrdShinse
 */
public class SqlServerSqlCmd extends AbstractSqlCmd {

    public SqlServerSqlCmd(SqlProperty prop, String fileName) {
        super(prop, fileName);
    }

    @Override
    public String[] getCommand() {
        if (prop == null || fileName == null) {
            throw new RuntimeException("prop:" + prop + " fileName:" + fileName);
        }

        return new String[]{
            "sqlcmd",
            "-S", prop.getServer(),
            "-d", prop.getDbName(),
            "-U", prop.getUser(),
            "-P", prop.getPassword(),
            "-i", Consts.DIR_QUERY + Consts.DELIMITER + fileName + ".sql",
            "-b", //エラー発生時にコマンドを終了する。
            "-s", "\t", //列の区切り文字をタブ(\t)とする。
            "-k2", //制御文字(タブ・改行)を空白で置き換える。
            "-W", //removes trailing spaces from a column.
            "-o", Consts.DIR_TSV + Consts.DELIMITER + fileName + ".tsv"
        };
    }

}
