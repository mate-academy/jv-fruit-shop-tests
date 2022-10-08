package core.basesyntax.fileservice.impl;

import core.basesyntax.fileservice.CsvFileWriterService;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class CsvFileWriterServiceImpl implements CsvFileWriterService {
    @Override
    public void writeToFile(String info, String path) {
        try {
            FileWriter fileWriter = new FileWriter(path);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(info);
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException("Can't write info to file");
        }
    }
}
