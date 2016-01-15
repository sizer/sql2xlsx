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
package mrdshinse.sql2xlsx.facade;

import java.io.File;
import java.io.FileFilter;
import java.util.List;
import mrdshinse.sql2xlsx.consts.Consts;
import mrdshinse.sql2xlsx.csv.AbstractCsv;
import mrdshinse.sql2xlsx.dto.SqlProperty;
import mrdshinse.sql2xlsx.logic.CsvReader;
import mrdshinse.sql2xlsx.logic.ExcelBuilder;
import mrdshinse.sql2xlsx.logic.MysqlCsvReader;
import mrdshinse.sql2xlsx.logic.MysqlSqlExecuter;
import mrdshinse.sql2xlsx.logic.SqlExecuter;
import mrdshinse.sql2xlsx.logic.SqlServerCsvReader;
import mrdshinse.sql2xlsx.logic.SqlServerSqlExecuter;
import mrdshinse.sql2xlsx.util.JsonUtil;

/**
 *
 * @author mrdShinse
 */
public class Execution implements Facade {

    private SqlExecuter sqlExecuter;
    private CsvReader csvReader;
    private ExcelBuilder excelBuilder;

    /**
     * <pre>
     * Default constractor.
     * Inject logics by property file.
     * </pre>
     */
    public Execution() {
        injectLogics(callProperty());
    }

    @Override
    public void exe() {
        File[] sqlFiles = callQueries();

        if (sqlFiles == null) {
            return;
        }

        for (File sql : sqlFiles) {
            String fileName = getFileName(sql);

            executeSqlexecuter(fileName);

            List<AbstractCsv> dtoList = executeCsvReader(fileName);

            executeExcelBuilder(fileName, dtoList);
        }
    }

    private SqlProperty callProperty() {
        SqlProperty prop = JsonUtil.parse(new File(Consts.PROP_SQL), SqlProperty.class);

        if (prop == null) {
            throw new RuntimeException("Their is no property file. Please execute with \"init\" option");
        }
        return prop;
    }

    private void injectLogics(SqlProperty prop) {
        switch (prop.getDbType()) {
            case "sqlserver":
                this.sqlExecuter = new SqlServerSqlExecuter();
                this.csvReader = new SqlServerCsvReader();
                break;
            case "mysql":
                this.sqlExecuter = new MysqlSqlExecuter();
                this.csvReader = new MysqlCsvReader();
                break;
            default:
                throw new RuntimeException("dbType in sqlProperty.property is wrong. Please input right dbType.");
        }
        excelBuilder = new ExcelBuilder();
    }

    private File[] callQueries() {
        return new File(Consts.DIR_QUERY).listFiles(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.isFile() && f.getName().endsWith("sql");
            }
        });
    }

    private String getFileName(File file) {
        return file.getName().substring(0, file.getName().length() - 4);
    }

    private void executeSqlexecuter(String fileName) {
        System.out.println("executing sql files...");

        sqlExecuter.exe(fileName);

        System.out.println("finished!");
    }

    private List<AbstractCsv> executeCsvReader(String fileName) {
        System.out.println("reading tsv files..." + fileName);

        List<AbstractCsv> dtoList = csvReader.exe(fileName);

        System.out.println("finished!");

        return dtoList;
    }

    private void executeExcelBuilder(String fileName, List<AbstractCsv> dtoList) {
        System.out.println("create excel files..." + fileName);

        try {
            excelBuilder.exe(fileName, dtoList);
        } catch (OutOfMemoryError e) {
            System.out.println("Not enough memory. please add VM option (ex.)-Xms200m -Xmx1000m");
        }

        System.out.println("finished!");
    }
}
