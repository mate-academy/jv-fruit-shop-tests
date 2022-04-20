package service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class ReaderImpl implements Reader {
    @Override
    public List<String> read(String fileName) {
        List<String> lines;
        try {
            lines = Files.readAllLines(Path.of(fileName));
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from file." + fileName);
        }
        return lines.stream().skip(1).collect(Collectors.toList());
    }
}
