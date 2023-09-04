package core.basesyntax.service.impl;

import core.basesyntax.service.ReaderService;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ReaderServiceImpl implements ReaderService {
    @Override
    public List<String> readFromFile(String path) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            return reader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Can't find the file by path " + path, e);
        }
    }
}
