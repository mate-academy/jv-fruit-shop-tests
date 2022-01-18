package core.basesyntax.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyFileReaderList implements MyFileReader {

    @Override
    public List<String> readDataFromFile(File file) {
        List<String> retList = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while (line != null) {
                retList.add(line);
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("FileNotFoundException: " + file.getName(), e);
        } catch (IOException e) {
            throw new RuntimeException("IOException during reading from file: "
                    + file.getName(), e);
        }
        return retList;
    }

}
