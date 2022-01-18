package core.basesyntax.dao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class MyFileWriterList implements MyFileWriter {

    @Override
    public void writeDataToFile(List<String> dataList, String fileFullPath) {
        File file = new File(fileFullPath);
        file.getParentFile().mkdirs(); //to create directories for file
        try (BufferedWriter bufferedWriter =
                     new BufferedWriter(new FileWriter(file, false))) {
            for (String lineData: dataList) {
                bufferedWriter.write(lineData + System.lineSeparator());
                bufferedWriter.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException("IOException during writing to file " + file.getName(), e);
        }
    }

}
