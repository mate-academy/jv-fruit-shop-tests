package core.basesyntax.filework.impl;

import core.basesyntax.filework.ReportWriteToFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class ReportWriteToFileImpl implements ReportWriteToFile {

    @Override
    public void writeToFile(String report, String path) {

        try {
            Files.write(Path.of(path), report.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException("Can't write report to file: " + path, e);
        }
    }
}
