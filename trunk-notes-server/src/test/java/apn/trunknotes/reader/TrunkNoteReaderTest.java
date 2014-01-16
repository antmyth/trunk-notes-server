package apn.trunknotes.reader;

import apn.trunknotes.types.*;
import org.junit.Test;

import java.io.BufferedWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import static basecamp.datafixtures.PrimitiveDataFixtures.someString;
import static java.nio.file.Files.newBufferedWriter;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TrunkNoteReaderTest {

    @Test
    public void readsTheFileIntoATrunkNote() throws Exception {
        Path tempFile = Files.createTempFile(someString(), "markdown");
        Title title = new Title(someString());
        Timestamp timestamp = new Timestamp("2014-01-08 21:37:53 +0000");
        CreatedTimestamp createdTimestamp = new CreatedTimestamp("2013-11-26 19:51:16 +0000");
        LastAccessedTimestamp lastAccessedTimestamp = new LastAccessedTimestamp("2013-11-26 19:51:16 +0000");

        assertThat(true, is(tempFile.toFile().exists()));

        try (BufferedWriter writer =
                     newBufferedWriter(tempFile, StandardCharsets.UTF_8,
                             StandardOpenOption.WRITE)) {
            writer.write(String.format("Title: %s\n", title.asString()));
            writer.write(String.format("Timestamp: %s\n",timestamp.asString()));
            writer.write(String.format("Created: %s\n",createdTimestamp.asString()));
            writer.write(String.format("Last Accessed: %s\n",lastAccessedTimestamp.asString()));
        }

        TrunkNoteReader reader = new TrunkNoteReader();
        TrunkNote expected = new TrunkNote(title,timestamp,createdTimestamp,lastAccessedTimestamp);

        assertThat(expected, is(reader.load(tempFile.toFile())));
    }
}
