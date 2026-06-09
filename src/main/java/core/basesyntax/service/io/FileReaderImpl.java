package core.basesyntax.service.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class FileReaderImpl implements FileReader {
    private String inputDataFilePath;

    public FileReaderImpl(String inputDataFilePath) {
        this.inputDataFilePath = inputDataFilePath;
    }

    @Override
    public List<String> read() {
        if (inputDataFilePath == null) {
            throw new RuntimeException("input data can not be null");
        }

        File file = new File(inputDataFilePath);

        try {
            List<String> lines = Files.readAllLines(file.toPath());

            if (lines.isEmpty()) {
                throw new RuntimeException("input file can not be empty");
            }

            return lines;
        } catch (IOException e) {
            throw new RuntimeException("can't read data from file");
        }
    }
}
