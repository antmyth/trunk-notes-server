package apn.trunknotes.reader;

import apn.trunknotes.types.*;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeParser;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Joiner.on;
import static com.google.common.base.Splitter.*;
import static com.google.common.collect.Lists.newArrayList;

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
		List<Action> actionList = parseActionTags(allLines);
		NoteBody body = parseNoteBody(allLines);
		return new TrunkNote(noteFileName, title, timestamp, createdTimestamp, lastAccessedTimestamp, timesAccessed, tags, body, actionList);
	}

	private ArrayList<Action> parseActionTags(List<String> allLines) {
		ArrayList<Action> actions = newArrayList();
		for (int i = 7; i < allLines.size(); i++) {
			String currentLine = allLines.get(i);
			if (currentLine.startsWith("@")) {
				ArrayList<String> stringArrayList = newArrayList(Splitter.on(" ").split(currentLine.substring(1)));
				int index = 0;

				ActionName name = new ActionName(stringArrayList.get(index++));
				ActionDate date = new ActionDate(null);
				ActionText text;

				if (stringArrayList.size() > 1) {
					DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
					try {
						formatter.parseDateTime(stringArrayList.get(index));
						date = new ActionDate(stringArrayList.get(index));
						index++;
					}
					catch (Exception e) {
					}
				}
				text = new ActionText(Joiner.on(" ").join(stringArrayList.subList(index, stringArrayList.size())));
				actions.add(new Action(name, date, text));
			}
		}
		return actions;
	}

	private NoteBody parseNoteBody(List<String> allLines) {
		StringBuilder sb = new StringBuilder();
		for (int i = 7; i < allLines.size(); i++) {
			String currentLine = allLines.get(i);
			if (currentLine.contains("[[")) {
				currentLine = currentLine.replaceAll("\\[\\[(.+)\\]\\]", "[$1]($1)");
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
