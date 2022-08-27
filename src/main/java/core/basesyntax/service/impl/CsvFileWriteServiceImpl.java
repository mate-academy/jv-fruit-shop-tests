package core.basesyntax.service.impl;

import core.basesyntax.exceptions.FileIoException;
import core.basesyntax.exceptions.WrongDataException;
import core.basesyntax.exceptions.WrongFileNameException;
import core.basesyntax.service.CsvFileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class CsvFileWriteServiceImpl implements CsvFileWriterService {
    @Override
    public void writeReportToFile(String report, String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            throw new WrongFileNameException("Null or empty fileName");
        }
        if (report == null || report.isEmpty()) {
            throw new WrongDataException("Null or empty reportName");
        }
        try {
            Files.write(Path.of(fileName), report.getBytes());
        } catch (IOException e) {
            throw new FileIoException("Can't write report to file " + fileName);
        }
    }
}
