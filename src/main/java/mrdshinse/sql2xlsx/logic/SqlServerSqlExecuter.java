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

import com.orangesignal.csv.Csv;
import com.orangesignal.csv.CsvConfig;
import com.orangesignal.csv.handlers.ResultSetHandler;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import mrdshinse.sql2xlsx.consts.Consts;
import mrdshinse.sql2xlsx.util.FileUtil;

/**
 *
 * @author mrdShinse
 */
public class SqlServerSqlExecuter extends SqlExecuter {

    @Override
    protected void exeQuery(String fileName) {
        Driver d;
        try {
            d = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
            String connUrl = "jdbc:sqlserver://" + prop.getServer() + ";database=" + prop.getDbName() + ";"
                    + "integratedSecurity=false;user=" + prop.getUser() + ";password=" + prop.getPassword();
            Connection con = d.connect(connUrl, new Properties());

            File sqlFile = new File(Consts.DIR_QUERY + Consts.DELIMITER + fileName + ".sql");
            String sql = FileUtil.toString(sqlFile);

            try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

                Csv.save(rs, new File(Consts.DIR_TSV + Consts.DELIMITER + fileName + ".tsv"), new CsvConfig('\t'), new ResultSetHandler());
            } catch (IOException ex) {
                Logger.getLogger(SqlServerSqlExecuter.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex) {
            Logger.getLogger(SqlServerSqlExecuter.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
