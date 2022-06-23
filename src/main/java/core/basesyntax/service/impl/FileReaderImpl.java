package core.basesyntax.service.impl;

import core.basesyntax.service.FileReaderService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileReaderImpl implements FileReaderService {
    @Override
    public List<String[]> read(String input) {
        List<String> reader = new ArrayList<>();
        List<String[]> fileReaded = new ArrayList<>();
        try {
            reader = Files.readAllLines(Path.of(input));
            for (String line : reader) {
                fileReaded.add(line.split(","));
            }
        } catch (IOException e) {
            throw new RuntimeException("somethin went wrong, not sure what: " + e);
        }
        fileReaded.remove(0);
        return fileReaded;
    }
}
