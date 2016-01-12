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

import com.orangesignal.csv.CsvConfig;
import com.orangesignal.csv.manager.CsvEntityManager;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mrdShinse
 */
public class SqlServerCsvReader {

    public List<Object> exe(File tsv) {
        List<Object> retList = new ArrayList<Object>();

        try {
            modifyCsv(tsv);
            getBean(tsv, Class.forName("mrdshinse.sql2xlsx.csv." + tsv.getName().replaceAll(".tsv", "")));
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SqlServerCsvReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retList;
    }

    private void modifyCsv(File file) {
        File result = new File(file.getAbsolutePath().replace(".tsv", "_.tsv"));
        int fileLength = 0;
        try {
            fileLength = Files.readAllLines(Paths.get(file.getAbsolutePath()), Charset.forName("Shift_JIS")).size();
        } catch (IOException ex) {
            Logger.getLogger(SqlServerCsvReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file)); PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(result)))) {

            String str;
            int counter = 0;

            while ((str = br.readLine()) != null) {
                counter++;

                if (counter != 2 && counter != fileLength - 1 && counter != fileLength) {
                    pw.println(str);
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SqlServerCsvReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SqlServerCsvReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        file.delete();
        result.renameTo(file);
    }

    private List<Object> getBean(File file, Class clazz) {
        InputStreamReader isr = null;
        List list = null;

        try {
            isr = new InputStreamReader(new FileInputStream(file));
            CsvConfig config = new CsvConfig('\t');
            config.setIgnoreLeadingWhitespaces(true);
            config.setIgnoreTrailingWhitespaces(true);
            config.setIgnoreEmptyLines(true);
            list = new CsvEntityManager(config)
                    .load(clazz)
                    .from(isr);
        } catch (IOException ex) {
            Logger.getLogger(SqlServerCsvReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
}
