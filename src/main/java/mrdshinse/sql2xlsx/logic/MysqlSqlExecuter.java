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

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import mrdshinse.sql2xlsx.consts.Consts;

/**
 *
 * @author mrdShinse
 */
public class MysqlSqlExecuter extends SqlExecuter {

    @Override
    protected Connection getConnection() {
        Driver d;
        Connection c;
        try {
            d = (Driver) Class.forName("com.mysql.jdbc.Driver").newInstance();
            String connUrl = "jdbc:mysql://" + prop.getServer() + Consts.DELIMITER + prop.getDbName();
            c = DriverManager
                    .getConnection(connUrl, prop.getUser(), prop.getPassword());

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex) {
            c = null;
            Logger.getLogger(MysqlSqlExecuter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return c;
    }

}
