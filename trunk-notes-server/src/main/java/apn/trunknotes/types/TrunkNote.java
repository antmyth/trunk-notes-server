package apn.trunknotes.types;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;
import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

/**
 * User: antonion
 * Date: 14/01/14 13:51
 */
public class TrunkNote {

    private final Title title;
    private Timestamp timestamp;
    private CreatedTimestamp createdTimestamp;
    private LastAccessedTimestamp lastAccessedTimestamp;
    private final TimesAccessed timesAccessed;
    private final TagList tag;
    private final NoteBody body;

    public TrunkNote(Title title,
                     Timestamp timestamp,
                     CreatedTimestamp createdTimestamp,
                     LastAccessedTimestamp lastAccessedTimestamp,
                     TimesAccessed timesAccessed,
                     TagList tag,
                     NoteBody body) {
        this.title = title;
        this.timestamp = timestamp;
        this.createdTimestamp = createdTimestamp;
        this.lastAccessedTimestamp = lastAccessedTimestamp;
        this.timesAccessed = timesAccessed;
        this.tag = tag;
        this.body = body;
    }

    @Override
    public int hashCode() {
        return reflectionHashCode(this);
    }

    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    @Override
    public boolean equals(Object o) {
        return reflectionEquals(this, o);
    }

    @Override
    public String toString() {
        return reflectionToString(this, SHORT_PREFIX_STYLE);
    }
}
