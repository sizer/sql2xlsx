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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mrdshinse.sql2xlsx.consts.Consts;
import mrdshinse.sql2xlsx.csv.AbstractCsv;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;

/**
 *
 * @author mrdShinse
 */
public class ExcelBuilder {

    public void exe(String fileName, List<AbstractCsv> list) {
        honmono(fileName, list);
    }

    private void honmono(String fileName, List<AbstractCsv> list) {
        InputStream is = null;
        try {
            is = new FileInputStream(Consts.DIR_TEMPLATE + Consts.DELIMITER + fileName + ".xls");
            OutputStream os = new FileOutputStream(Consts.DIR_RESULT + Consts.DELIMITER + fileName + ".xls");
            Context context = new Context();
            context.putVar(fileName, list);
            JxlsHelper.getInstance().processTemplate(is, os, context);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ExcelBuilder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ExcelBuilder.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                is.close();
            } catch (IOException ex) {
                Logger.getLogger(ExcelBuilder.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
