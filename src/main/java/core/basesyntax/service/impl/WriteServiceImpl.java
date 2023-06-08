package core.basesyntax.service.impl;

import core.basesyntax.service.WriteService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class WriteServiceImpl implements WriteService {
    @Override
    public void writeFile(String fileName, List<String> content) {
        try {
            Files.write(Path.of(fileName), content);
        } catch (IOException e) {
            throw new RuntimeException("Can't write data to file " + fileName, e);
        }
    }
}
