package core.basesyntax.service.impl;

import core.basesyntax.service.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class CsvReaderService implements DataReaderService {
    private static final int HEADER_LINE_INDEX = 0;

    @Override
    public List<String> readData(String path) {
        List<String> dataList;
        try {
            dataList = Files.readAllLines(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException("Can not read file. Path:" + path);
        }
        if (dataList.isEmpty()) {
            throw new RuntimeException("Can not proceed operate with empty file. Path:" + path);
        }
        dataList.remove(HEADER_LINE_INDEX);
        return dataList;
    }
}
