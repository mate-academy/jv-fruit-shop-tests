package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriterService;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileWriterImpl implements FileWriterService {
    @Override
    public void writeToFile(String pathName, List<String> data) {
        if (data.isEmpty()) {
            throw new RuntimeException("Your data is empty,");
        }
        File file = new File(pathName);
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            for (String string : data) {
                bufferedWriter.write(string);
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't write data to file correctly.", e);
        }
    }
}
