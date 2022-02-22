package core.basesyntax.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteDataToFile {

    public void writeToFile(String report, String toFileName) {
        File reportFile = new File(toFileName);
        if (report.isEmpty() || toFileName.isEmpty()) {
            throw new RuntimeException("Empty file or file path!!");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(reportFile, true))) {
            writer.write(report);
        } catch (IOException e) {
            throw new RuntimeException("Can`t write file!", e);
        }
    }
}
