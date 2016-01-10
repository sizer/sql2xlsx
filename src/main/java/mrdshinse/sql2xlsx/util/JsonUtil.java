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
package mrdshinse.sql2xlsx.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author mrdShinse
 */
public class JsonUtil {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * JSON文字列をDTOクラスへ変換する
     *
     * @param <T>
     * @param json
     * @param dto
     * @return DTOobject
     */
    public static <T> T parse(String json, Class<T> dto) {
        try {
            return (T) MAPPER.readValue(json, dto);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * JSON文字列をDTOクラスへ変換する
     *
     * @param <T>
     * @param file
     * @param dto
     * @return
     */
    public static <T> T parse(File file, Class<T> dto) {
        return parse(FileUtil.toString(file), dto);
    }

    /**
     * DTOクラスのインスタンスをJSON文字列に変換する
     *
     * @param dto
     * @return String
     */
    public static String convert(Object dto) {
        try {
            String json = MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(dto);
            return json;
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public static File createFile(Object dto, File file) {
        FileUtil.create(file, convert(dto));
        return file;
    }
}
