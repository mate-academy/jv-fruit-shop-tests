package core.basesyntax.service.csv.impl;

import core.basesyntax.service.csv.WriteService;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CsvWriteServiceImpl implements WriteService {
    @Override
    public void save(String contentToWrite, String path) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path))) {
            bufferedWriter.write(contentToWrite);
        } catch (IOException e) {
            throw new RuntimeException("Can't write to file by path: " + path, e);
        }
    }
}
