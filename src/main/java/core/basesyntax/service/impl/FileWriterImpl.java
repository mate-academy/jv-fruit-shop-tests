package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileWriterImpl implements FileWriter {
    @Override
    public void write(String content, String file) {
        if (content == null || content.equals("")) {
            throw new RuntimeException("Empty content was given");
        }
        Path path = Path.of(file);
        try {
            Files.writeString(path, content);
        } catch (IOException e) {
            System.out.println("Can't write file! " + e);
        }
    }
}
