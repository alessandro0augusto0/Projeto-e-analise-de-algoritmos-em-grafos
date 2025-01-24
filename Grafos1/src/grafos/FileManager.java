package grafos;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileManager {

    public ArrayList<String> stringReader(String path) {
        BufferedReader buffRead = null;
        try {
            buffRead = new BufferedReader(new FileReader(path));
            ArrayList<String> text = new ArrayList<>();
            String line = buffRead.readLine();
            while (line != null) {
                text.add(line);
                line = buffRead.readLine();
            }
            return text;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (IOException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            try {
                if (buffRead != null) {
                    buffRead.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
