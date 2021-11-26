package shop.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import shop.service.Writer;

public class CsvWriterImpl implements Writer {

    public CsvWriterImpl() {
    }

    @Override
    public boolean write(List<String> dataToWrite, String outputFile) {
        try {
            Files.write(Paths.get(outputFile), dataToWrite);
        } catch (IOException e) {
            throw new RuntimeException("Can`t write in file");
        }
        return true;
    }
}
