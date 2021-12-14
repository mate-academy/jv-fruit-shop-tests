package core.basesyntax.shop.service;

import java.io.IOException;

public interface FileReader {
    String[] read(String fileName) throws IOException;
}
