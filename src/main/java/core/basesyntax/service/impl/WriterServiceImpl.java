package core.basesyntax.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import core.basesyntax.service.WriterService;

public class WriterServiceImpl implements WriterService {
    @Override
    public void writeDataToFile(String data, String fileName) {
        if (data.length() == 0) {
            throw new RuntimeException("Can't write zero string");
        }
        File report = new File(fileName);
        try {
            report.createNewFile();
            Files.write(report.toPath(), data.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Can't create new file or write to" + fileName, e);
        }
    }
}
