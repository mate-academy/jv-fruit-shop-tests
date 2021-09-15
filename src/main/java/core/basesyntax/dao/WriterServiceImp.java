package core.basesyntax.dao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class WriterServiceImp implements WriterService {
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yy HH_mm_ss");

    @Override
    public String writeToFile(List<String> reportList, String filePath) {
        if (filePath == null) {
            throw new RuntimeException("file path is null!");
        }
        if (reportList == null) {
            throw new RuntimeException("report list is null!");
        }
        String reportDateTime = dtf.format(LocalDateTime.now());
        String fullFilePath = filePath + "report dated" + reportDateTime;
        File file = new File(fullFilePath);
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            for (String line:
                    reportList) {
                bufferedWriter.write(line);
                bufferedWriter.write(System.lineSeparator());
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't create the file!", e);
        }
        return fullFilePath;
    }
}
