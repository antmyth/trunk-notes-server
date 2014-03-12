package apn.trunknotes.reader;

import apn.trunknotes.types.Action;
import apn.trunknotes.types.ActionDate;
import apn.trunknotes.types.ActionName;
import apn.trunknotes.types.ActionText;
import apn.trunknotes.types.CreatedTimestamp;
import apn.trunknotes.types.LastAccessedTimestamp;
import apn.trunknotes.types.NoteBody;
import apn.trunknotes.types.TagList;
import apn.trunknotes.types.TimesAccessed;
import apn.trunknotes.types.Timestamp;
import apn.trunknotes.types.Title;
import apn.trunknotes.types.TrunkNote;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

import static apn.trunknotes.fixtures.TrunkNotesDataFixtures.someTag;
import static basecamp.datafixtures.PrimitiveDataFixtures.someNumberOfLength;
import static basecamp.datafixtures.PrimitiveDataFixtures.someString;
import static basecamp.datafixtures.PrimitiveDataFixtures.someStringOfLength;
import static java.lang.String.format;
import static java.nio.file.Files.newBufferedWriter;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;

public class TrunkNoteReaderTest {

	private TrunkNoteReader trunkNoteReader;
	private Title title;
	private Timestamp timestamp;
	private CreatedTimestamp createdTimestamp;
	private LastAccessedTimestamp lastAccessedTimestamp;
	private TimesAccessed timesAccessed;
	private TagList tags;
	private NoteBody body;

	@Before
	public void setUp() throws Exception {
		trunkNoteReader = new TrunkNoteReader();
		title = new Title(someString());
		timestamp = new Timestamp("2014-01-08 21:37:53 +0000");
		createdTimestamp = new CreatedTimestamp("2013-11-26 19:51:16 +0000");
		lastAccessedTimestamp = new LastAccessedTimestamp("2013-11-26 19:51:16 +0000");
		timesAccessed = new TimesAccessed(someNumberOfLength(2));
		tags = new TagList(someTag(), someTag());
	}

	@Test
    public void readsTheFileIntoATrunkNote() throws Exception {
        Path tempFile = Files.createTempFile(someString(), "markdown");
		body = new NoteBody("# APN wiki home\n" +
                "# To Do's\n" +
                "\n" +
                "\n" +
                "# toRead \n" +
                "{{action @toread}}\n" +
                "# Reading List\n" +
                "{{action @reading}}\n" +
                "\n" +
                "# \n"
		);

		writeTrunkNoteTo(tempFile);

		TrunkNote expected = new TrunkNote(tempFile.toFile().getName(), title, timestamp, createdTimestamp, lastAccessedTimestamp, timesAccessed, tags, body, new ArrayList<Action>());

        assertThat(expected, is(trunkNoteReader.load(tempFile.toFile())));
    }

	@Test
	public void parsesInternalPageLinksOnTheNoteBody() throws Exception{
		Path tempFile = Files.createTempFile(someString(), "markdown");
		String linkedPage = someString();
		body = new NoteBody(format("[[%s]]\n", linkedPage));

		writeTrunkNoteTo(tempFile);

		TrunkNote trunkNote = trunkNoteReader.load(tempFile.toFile());
		assertThat(trunkNote.body().asString(), containsString(format("[%1$s](%1$s)",linkedPage)));
	}

	@Test
	public void parsesActionTagsOnTheNoteBody() throws Exception{
		Path tempFile = Files.createTempFile(someString(), "markdown");
		String actionTagName = someString();
		String actionTagText = someStringOfLength(15) + " " + someString();
		body = new NoteBody(format("@%s %s\n", actionTagName, actionTagText));

		writeTrunkNoteTo(tempFile);

		TrunkNote trunkNote = trunkNoteReader.load(tempFile.toFile());
		assertThat(trunkNote.actions().size(), is(1));
		assertThat(trunkNote.actions().get(0),is(new Action(new ActionName(actionTagName), new ActionDate(null), new ActionText(actionTagText))));
	}

	@Test
	public void parsesActionTagsWithDateOnTheNoteBody() throws Exception{
		Path tempFile = Files.createTempFile(someString(), "markdown");
		String actionTagName = someString();
		String actionTagText = someStringOfLength(15) + " " + someString();
		String actionTagDate = "18/12/2013";
		body = new NoteBody(format("@%s %s %s\n", actionTagName,actionTagDate, actionTagText));

		writeTrunkNoteTo(tempFile);

		TrunkNote trunkNote = trunkNoteReader.load(tempFile.toFile());
		assertThat(trunkNote.actions().size(), is(1));
		assertThat(trunkNote.actions().get(0),is(new Action(new ActionName(actionTagName), new ActionDate(actionTagDate),new ActionText(actionTagText))));
	}

	private void writeTrunkNoteTo(Path tempFile) throws IOException {
		assertThat(true, is(tempFile.toFile().exists()));

		try (BufferedWriter writer =
					 newBufferedWriter(tempFile, StandardCharsets.UTF_8,
							 StandardOpenOption.WRITE)) {
			writer.write(format("Title: %s\n", title.asString()));
			writer.write(format("Timestamp: %s\n", timestamp.asString()));
			writer.write(format("Created: %s\n", createdTimestamp.asString()));
			writer.write(format("Last Accessed: %s\n", lastAccessedTimestamp.asString()));
			writer.write(format("Times Accessed: %s\n", timesAccessed.asString()));
			writer.write(format("Tags: %s\n", tags.asString()));
			writer.write(format("Metadata: %s\n", ""));
			writer.write(body.asString());
		}
	}
}
