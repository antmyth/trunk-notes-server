package apn.trunknotes.reader;

import apn.trunknotes.types.*;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TrunkNoteReader {
    public TrunkNote load(File noteFile) throws Exception {
        Path path = noteFile.toPath();
        String noteFileName = noteFile.getName();
        List<String> allLines = Files.readAllLines(path, StandardCharsets.UTF_8);
        Title title = new Title(allLines.get(0).substring(7));
        Timestamp timestamp = new Timestamp(allLines.get(1).substring(11));
        CreatedTimestamp createdTimestamp = new CreatedTimestamp(allLines.get(2).substring(9));
        LastAccessedTimestamp lastAccessedTimestamp = new LastAccessedTimestamp(allLines.get(3).substring(15));
        TimesAccessed timesAccessed = new TimesAccessed(allLines.get(4).substring("Times Accessed: ".length()));
        TagList tags = parseTagList(allLines);
        NoteBody body = parseNoteBody(allLines);
        return new TrunkNote(noteFileName, title, timestamp, createdTimestamp, lastAccessedTimestamp, timesAccessed, tags, body);
    }

    private NoteBody parseNoteBody(List<String> allLines) {
        StringBuilder sb = new StringBuilder();
        for (int i = 7; i < allLines.size(); i++) {
			String currentLine = allLines.get(i);
			if(currentLine.contains("[[")){
				currentLine = currentLine.replaceAll("\\[\\[(.+)\\]\\]","[$1]($1)");
			}
			sb.append(currentLine).append("\n");
        }
        return new NoteBody(sb.toString());
    }

    private TagList parseTagList(List<String> allLines) {
        String tagListAsString = allLines.get(5).substring("Tags: ".length());
        Iterable<String> tagsAsStrings = Splitter.on(", ").split(tagListAsString);
        ArrayList<Tag> auxTagList = Lists.<Tag>newArrayList();
        for (String tagAsString : tagsAsStrings) {
            auxTagList.add(new Tag(tagAsString));
        }
        return new TagList(auxTagList);
    }
}
