package softuni.workshop.util;

import java.io.*;

public class FileUtilImpl implements FileUtil {
    @Override
    public String readFile(String path) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        File file = new File(path);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line).append(System.lineSeparator());
        }

        return stringBuilder.toString().trim();
    }
}
