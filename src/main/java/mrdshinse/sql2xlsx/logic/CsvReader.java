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
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mrdshinse.sql2xlsx.consts.Consts;
import mrdshinse.sql2xlsx.csv.AbstractCsv;

/**
 *
 * @author mrdShinse
 */
public class CsvReader {

    public List<AbstractCsv> exe(String fileName) {
        List<AbstractCsv> retList = new ArrayList<>();

        try {
            retList = getBean(new File(Consts.DIR_TSV + Consts.DELIMITER + fileName + ".tsv"), Class.forName("mrdshinse.sql2xlsx.csv." + fileName));
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return retList;
    }

    private List<AbstractCsv> getBean(File file, Class clazz) {
        InputStreamReader isr = null;
        List list = new ArrayList<>();

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
            Logger.getLogger(MysqlCsvReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

}
