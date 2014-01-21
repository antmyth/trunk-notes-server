package apn.trunknotes.types;

import basecamp.types.StringValue;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;
import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

public class TimesAccessed implements StringValue{
    private final Long timesAccessed;

    public TimesAccessed(Long timesAccessed) {
        this.timesAccessed = timesAccessed;
    }

    public TimesAccessed(String asString) {
        timesAccessed = Long.parseLong(asString);
    }

    public Long value(){
        return timesAccessed;
    }

    @Override
    public boolean isNull() {
        return timesAccessed == null;
    }

    @Override
    public String asString() {
        return timesAccessed.toString();
    }

    @Override
    public int hashCode() {
        return reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object o) {
        return reflectionEquals(this, o);
    }

    @Override
    public String toString() {
        return reflectionToString(this, SHORT_PREFIX_STYLE);
    }
}
