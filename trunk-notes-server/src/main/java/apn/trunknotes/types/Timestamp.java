package apn.trunknotes.types;

import basecamp.types.StringValue;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;
import static org.apache.commons.lang3.builder.ToStringStyle.*;

public class Timestamp implements StringValue {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss Z");
    private final DateTime dateTime;

    public Timestamp(String dateAsString) {
        if (dateAsString == null) {
            dateTime = null;
        } else {
            dateTime = DATE_TIME_FORMATTER.parseDateTime(dateAsString);
        }
    }

    @Override
    public boolean isNull() {
        return dateTime == null;
    }

    public String asString() {
        return isNull() ? null : DATE_TIME_FORMATTER.print(dateTime);
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
