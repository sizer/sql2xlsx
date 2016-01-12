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

import java.io.File;
import java.io.FileFilter;
import java.util.List;
import mrdshinse.sql2xlsx.consts.Consts;
import mrdshinse.sql2xlsx.csv.AbstractCsv;
import mrdshinse.sql2xlsx.logic.ExcelBuilder;
import mrdshinse.sql2xlsx.logic.Initializer;
import mrdshinse.sql2xlsx.logic.SqlExecuter;
import mrdshinse.sql2xlsx.logic.SqlServerCsvReader;

/**
 *
 * @author mrdShinse
 */
public class Db2xlsx {

    public static void main(String[] args) {
        System.out.println("----------start");
        if (args.length != 1) {
            howto();
            return;
        }

        if (args[0].equals("init")) {
            new Initializer().exe();
        }
        if (args[0].equals("exe")) {
            System.out.print("executing sql files...");
            new SqlExecuter().exe();
            System.out.println("finished");

            File[] tsvFiles = new File(Consts.DIR_TSV).listFiles(new FileFilter() {
                @Override
                public boolean accept(File f) {
                    return f.isFile() && f.getName().endsWith("tsv");
                }
            });

            if (tsvFiles == null) {
                return;
            }

            for (File tsv : tsvFiles) {
                System.out.print("reading tsv files...");
                List<AbstractCsv> list = new SqlServerCsvReader().exe(tsv);
                System.out.println("finished");
                System.out.print("create excel files...");
                new ExcelBuilder().exe(tsv, list);
                System.out.println("finished");
            }
        }

        System.out.println("----------end");
    }

    private static void howto() {
        System.out.println("autoSql init -> create configuration file and directory.");
        System.out.println("autoSql exe -> execute command by configration.");
    }

}
