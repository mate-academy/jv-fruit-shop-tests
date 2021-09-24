package core.service.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class OsFileServiceImpl implements FileService {
    private static final String PATH_INPUT = "src/main/resources/report_input.csv";

    public OsFileServiceImpl() {
    }

    @Override
    public List<String> readFile(String filePath) {
        filePath = PATH_INPUT;
        List<String> records;
        try {
            records = Files.readAllLines(new File(filePath).toPath());
        } catch (IOException e) {
            throw new RuntimeException("Can't read file " + filePath, e);
        }
        if (records.isEmpty()) {
            throw new RuntimeException("File is empty " + filePath);
        }
        return records;
    }
}

