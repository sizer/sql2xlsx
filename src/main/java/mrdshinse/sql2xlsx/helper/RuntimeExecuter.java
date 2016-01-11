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
package mrdshinse.sql2xlsx.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 *
 * @author mrdShinse
 */
public class RuntimeExecuter {

    private InputStream in = null;

    private InputStream ein = null;

    private OutputStream out = null;

    private BufferedReader br = null;

    private BufferedReader ebr = null;

    private Process process = null;

    private String line = null;

    private String errLine = null;

    private Thread stdRun = null;

    private Thread errRun = null;

    public RuntimeExecuter() {
    }

    public void execCmd(String[] args) throws IOException, InterruptedException {

        process = Runtime.getRuntime().exec(args);

        /* 1 サブプロセスの入力ストリームを取得 */
        in = process.getInputStream();

        /* 2 サブプロセスのエラーストリームを取得 */
        ein = process.getErrorStream();

        /* 3 サブプロセスの出力ストリームを取得 ここでは使用しません。*/
        out = process.getOutputStream();

        /* 上記の3つのストリームは finally でクローズします。 */
        try {
            /*上記 1 のストリームを別スレッドで出力します。*/
            Runnable inputStreamThread = new Runnable() {
                @Override
                public void run() {
                    try {
                        br = new BufferedReader(new InputStreamReader(in));
                        while ((line = br.readLine()) != null) {
                            System.out.println(line);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };

            /*上記 2 のストリームを別スレッドで出力*/
            Runnable errStreamThread = new Runnable() {
                @Override
                public void run() {
                    try {
                        ebr = new BufferedReader(new InputStreamReader(ein));
                        while ((errLine = ebr.readLine()) != null) {
                            System.err.println(errLine);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };

            stdRun = new Thread(inputStreamThread);
            errRun = new Thread(errStreamThread);

            /* スレッドを開始します。 */
            stdRun.start();
            errRun.start();

            /*プロセスが終了していなければ終了するまで待機*/
            process.waitFor();

            /* サブスレッドが終了するのを待機 */
            stdRun.join();
            errRun.join();

        } catch (Exception e) {
            System.out.println("RuntimeExecuterの処理に失敗しました。");
        } finally {
            if (br != null) {
                br.close();
            }
            if (ebr != null) {
                ebr.close();
            }

            /* 子プロセス */
            if (in != null) {
                in.close();
            }
            if (ein != null) {
                ein.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }

}
