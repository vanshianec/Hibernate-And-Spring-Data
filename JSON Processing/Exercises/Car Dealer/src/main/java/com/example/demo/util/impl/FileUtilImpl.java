package com.example.demo.util.impl;

import com.example.demo.util.FileUtil;

import java.io.*;

public class FileUtilImpl implements FileUtil {
    @Override
    public String fileContent(String path) throws IOException {
        File file = new File(path);
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line).append(System.lineSeparator());
        }
        return stringBuilder.toString().trim();
    }
}
