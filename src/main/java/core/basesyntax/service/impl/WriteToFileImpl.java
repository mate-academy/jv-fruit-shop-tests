package core.basesyntax.service.impl;

import core.basesyntax.service.WriteToFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class WriteToFileImpl implements WriteToFile {
    private static final int INDEX_OF_HEAD = 0;
    private static final String HEAD = "fruit,quantity" + System.lineSeparator();

    @Override
    public boolean writeToFile(String filePath, List<String> report) {
        if (filePath == null) {
            throw new RuntimeException("FilePath must be matched");
        }
        if (report == null) {
            throw new RuntimeException("Report must be matched");
        }
        if (report.isEmpty()) {
            return false;
        }
        report.add(INDEX_OF_HEAD, HEAD);
        Path path = Paths.get(filePath);
        report.forEach(s -> {
            try {
                Files.writeString(path, s, StandardOpenOption.APPEND);
            } catch (IOException e) {
                throw new RuntimeException("Can't find such file" + filePath, e);
            }
        });
        return true;
    }
}
