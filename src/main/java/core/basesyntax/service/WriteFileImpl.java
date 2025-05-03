package core.basesyntax.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class WriteFileImpl implements WriteFile {
    @Override
    public boolean writeFileReport(List<String> report, String filePath) {

        File file;
        try {
            file = new File(filePath);
        } catch (NullPointerException e) {
            throw new RuntimeException(" File path is null ", e);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String line: report) {
                writer.write(line);
                writer.write(System.getProperty("line.separator"));
            }
        } catch (IOException e) {
            throw new RuntimeException(" Can`t create file" + filePath, e);
        }
        return true;
    }
}
