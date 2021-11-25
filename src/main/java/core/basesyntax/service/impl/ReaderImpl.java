package core.basesyntax.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class ReaderImpl implements core.basesyntax.service.Reader {
    @Override
    public List<String> read(String source) {
        File fileToRead = new File(source);
        try {
            List<String> strings = Files.readAllLines(fileToRead.toPath());
            if (strings.isEmpty()) {
                throw new RuntimeException("File " + source + " is empty");
            }
            return strings;
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file " + source, e);
        }
    }
}
