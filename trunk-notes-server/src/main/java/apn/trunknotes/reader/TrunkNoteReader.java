package apn.trunknotes.reader;

import apn.trunknotes.types.Title;
import apn.trunknotes.types.TrunkNote;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class TrunkNoteReader {
    public TrunkNote load(File noteFile) throws Exception {
        Path path = noteFile.toPath();
        List<String> allLines = Files.readAllLines(path, StandardCharsets.UTF_8);
        String titleString = allLines.get(0);
        Title title = new Title(titleString.substring(7));
        return new TrunkNote(title);
    }
}
