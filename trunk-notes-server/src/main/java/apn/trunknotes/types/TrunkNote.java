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

    public TrunkNote(Title title, Timestamp timestamp, CreatedTimestamp createdTimestamp, LastAccessedTimestamp lastAccessedTimestamp) {
        this.title = title;
        this.timestamp = timestamp;
        this.createdTimestamp = createdTimestamp;
        this.lastAccessedTimestamp = lastAccessedTimestamp;
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
