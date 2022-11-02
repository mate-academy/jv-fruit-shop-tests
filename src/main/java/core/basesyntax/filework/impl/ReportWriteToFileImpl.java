package core.basesyntax.filework.impl;

import core.basesyntax.filework.ReportWriteToFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public class ReportWriteToFileImpl implements ReportWriteToFile {

    @Override
    public void writeToFile(String report, String path) {
        File file = new File(path);

        try {
            Files.write(file.toPath(), report.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException("Can't write report to file: " + file, e);
        }
    }
}
