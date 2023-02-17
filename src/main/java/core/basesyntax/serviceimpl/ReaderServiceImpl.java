package core.basesyntax.serviceimpl;

import core.basesyntax.service.ReaderService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ReaderServiceImpl implements ReaderService {

    @Override
    public List<String> read(String fromFilePath) {
        List<String> data;
        Path inputFilePath = Path.of(fromFilePath);
        try {
            data = Files.readAllLines(inputFilePath);
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from file" + fromFilePath, e);
        }
        return data;
    }
}
