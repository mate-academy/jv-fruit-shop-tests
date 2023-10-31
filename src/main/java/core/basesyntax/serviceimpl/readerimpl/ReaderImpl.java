package core.basesyntax.serviceimpl.readerimpl;

import core.basesyntax.strategy.serviceintrface.reader.Reader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ReaderImpl implements Reader {

    public static final int OFFSET = 1;

    @Override
    public List<String> readDataFromFile(String path) {
        try {
            return Files.lines(Path.of(path)).skip(OFFSET).toList();
        } catch (IOException e) {
            throw new RuntimeException("Error read file by path: " + path, e);
        }
    }
}
