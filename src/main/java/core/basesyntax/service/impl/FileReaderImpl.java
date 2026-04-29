package core.basesyntax.service.impl;

import core.basesyntax.service.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileReaderImpl implements FileReader {
    @Override
    public List<String> readTransactionsFromFile(String fileName) {
        List<String> list = new ArrayList<>();

        InputStream inputStream = getClass()
                .getClassLoader()
                .getResourceAsStream(fileName);
        if (inputStream == null) {
            throw new RuntimeException("Resource not found: " + fileName
                + ". Make sure the file is placed in src/main/resources and the name is correct.");
        }

        try (BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(inputStream))) {
            String header = bufferedReader.readLine(); //  explicitly skip header
            if (header == null) {
                return Collections.emptyList();
            }
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                list.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file: " + fileName, e);
        }
        return list;
    }
}
