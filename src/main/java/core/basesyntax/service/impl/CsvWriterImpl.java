package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriterService;
import core.basesyntax.service.ReportCreatorService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public class CsvWriterImpl implements FileWriterService {
    private final ReportCreatorService reportCreatorService;

    public CsvWriterImpl(ReportCreatorService reportCreatorService) {
        this.reportCreatorService = reportCreatorService;
    }

    @Override
    public void writeToFile(String filePath) {
        File reportFile = new File(filePath);
        try {
            reportFile.delete();
            reportFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException("FAILED TO CREATE: cant create new file: "
                    + System.lineSeparator() + filePath);
        }
        writeStringToFile(reportFile, ("fruit,quantity" + System.lineSeparator()));
        reportCreatorService.createReport()
                .forEach(
                        line -> writeStringToFile(reportFile, (line + System.lineSeparator()))
                );
    }

    public void writeStringToFile(File file, String line) {
        try {
            Files.write(file.toPath(), line.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException("FAILED TO WRITE: cant write line: "
                    + line + ", to file: "
                    + System.lineSeparator() + file.getName());
        }
    }
}
