package dao;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class DaoWriterImpl implements DaoWriter {
    @Override
    public void write(List<String> stringReport, String fileName) {
        if (fileName == null) {
            throw new RuntimeException("File name is null");
        }
        if (stringReport == null) {
            throw new RuntimeException("Report name is null");
        }
        try {
            Path path = Path.of(fileName);
            Files.write(path, stringReport);
        } catch (Exception e) {
            throw new RuntimeException("Can't write the file: " + fileName);
        }
    }
}
