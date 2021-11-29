package core.basesyntax.service.impl;

import core.basesyntax.service.WriterService;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriterServiceImpl implements WriterService {
    @Override
    public boolean writeToFile(String pathToFile, String data) {
        if (data == null) {
            throw new RuntimeException("Incorrect data input");
        }
        File file = new File(pathToFile);
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            bufferedWriter.write(data);
            return true;
        } catch (IOException e) {
            throw new RuntimeException("Can't write to file" + e);
        }
    }
}
