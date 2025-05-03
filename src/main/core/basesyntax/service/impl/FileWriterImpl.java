package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileWriterImpl implements FileWriter {
    @Override
    public void writeToFile(String text, String path) {
        try {
            Files.writeString(Paths.get(path), text,
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Can't write to file " + path);
        } catch (NullPointerException e) {
            if (text == null) {
                throw new RuntimeException("Wrong text", e);
            }
            if (path == null) {
                throw new RuntimeException("Wrong path", e);
            }
        }
    }
}
