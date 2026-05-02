package core.basesyntax.basesyntax.service.impl;

import core.basesyntax.basesyntax.service.FileWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileWriterImpl implements FileWriter {
    @Override
    public void writeFile(List<String> lines, String path) {
        if (path == null || path.isBlank()) {
            throw new RuntimeException("Invalid path: " + path);
        }
        try {
            File file = new File(path);
            File parentDir = file.getParentFile();

            if (parentDir != null && !parentDir.exists()) {
                boolean created = parentDir.mkdirs();
                if (!created) {
                    throw new RuntimeException("Could not create directory: "
                            + file.getAbsolutePath());
                }
            }

            try (BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(file))) {
                for (String line : lines) {
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error writing file", e);
        }
    }
}
