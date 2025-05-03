package core.basesyntax.service.impl;

import core.basesyntax.service.DataReaderService;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class DataReaderServiceImpl implements DataReaderService {
    @Override
    public List<String> readDataFromFile(String filepath) {
        try {
            List<String> resultList = Files.readAllLines(Paths.get(filepath));
            if (resultList.isEmpty()) {
                throw new RuntimeException("Input file is empty: " + filepath);
            }
            return resultList;
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Can`t find a file: " + filepath);
        } catch (IOException e) {
            throw new RuntimeException("Can`t read data from file: " + filepath);
        }
    }
}
