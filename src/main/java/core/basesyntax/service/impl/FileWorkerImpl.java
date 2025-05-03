package core.basesyntax.service.impl;

import core.basesyntax.service.FileWorker;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class FileWorkerImpl implements FileWorker {
    @Override
    public List<String> readFromFile(String fileName) {
        try {
            return Files.readAllLines(Paths.get(fileName));
        } catch (IOException e) {
            throw new RuntimeException("Something went wrong "
                    + "when reading data from " + fileName, e);
        }
    }

    @Override
    public void writeToFile(String fileName, String header,
                            String columnSeparator, Map<String, Integer> data) {
        try (BufferedWriter writer =
                     Files.newBufferedWriter(Paths.get(fileName))) {
            writer.write(header);
            writer.newLine();
            for (Map.Entry<String, Integer> entry : data.entrySet()) {
                writer.write(entry.getKey() + columnSeparator + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Something went wrong "
                    + "when writing report to "
                    + fileName, e);
        }
    }
}
