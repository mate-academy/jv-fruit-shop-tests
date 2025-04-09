package core.basesyntax.dao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileReaderCsvImpl implements FruitFileReader {

    @Override
    public List<String> read(String fileName) {
        if (!fileName.toLowerCase().endsWith(".csv")) {
            throw new IllegalArgumentException("Invalid file type. Expected a .csv file: " + fileName);
        }
        List<String> inputArray;
        try {
            inputArray = Files.readAllLines(Path.of(fileName));
        } catch (IOException e) {
            throw new RuntimeException("Can't read the file by path: " + fileName, e);
        }
        return inputArray;
    }
}
