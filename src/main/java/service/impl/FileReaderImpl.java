package service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import service.FileReader;

public class FileReaderImpl implements FileReader {
    private static final int FIRST_LINE = 0;

    @Override
    public List<String> read(String fileName) {
        List<String> listOfLines = new ArrayList<>();
        File file;
        try {
            file = new File(fileName);
        } catch (NullPointerException e) {
            throw new NullPointerException("Can't read null file name");
        }
        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(file))) {
            String value;
            while ((value = reader.readLine()) != null) {
                listOfLines.add(value);
            }
        } catch (IOException e) {
            throw new RuntimeException("Cannot read the file " + file.getPath(), e);
        }
        if (!listOfLines.get(FIRST_LINE).equals("type,fruit,quantity")) {
            throw new RuntimeException(
                    "The file doesn't match the required format,"
                            + " first line must be (type,fruit,quantity), but is "
                            + listOfLines.get(0));
        }
        return listOfLines;
    }
}
