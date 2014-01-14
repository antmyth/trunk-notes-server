package apn.trunknotes.reader;

import apn.trunknotes.types.Title;
import apn.trunknotes.types.TrunkNote;

import java.io.*;

public class TrunkNoteReader {
    public TrunkNote load(File noteFile) throws Exception {
        FileInputStream stream = new FileInputStream(noteFile);
        DataInputStream inputStream = new DataInputStream(stream);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String strLine;
        Title title = null;
        while ((strLine = bufferedReader.readLine())!=null){
            title = new Title(strLine.substring(7,strLine.length()));
        }
        inputStream.close();
        return new TrunkNote(title);
    }
}
