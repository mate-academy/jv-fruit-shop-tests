package core.basesyntax.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileWriter implements Writer {
    private static final String PATTERN = "(\\w+,\\w+\\r*\\n*)(\\w+,\\d+\\r*\\n*)*";

    @Override
    public boolean writeToFile(String report, String filePath) {

        if (report == null || !report.matches(PATTERN)) {
            throw new RuntimeException("Report isn't valid");
        }
        try {
            Files.write(Path.of(filePath), report.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException("There is a problem with saving" + filePath, e);
        }
        return true;
    }
}

