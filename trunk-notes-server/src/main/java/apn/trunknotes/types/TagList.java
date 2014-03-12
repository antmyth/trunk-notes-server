package apn.trunknotes.types;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public class TagList {
    private final ImmutableList<Tag> tagList;

    public TagList(Tag... tags) {
        tagList = ImmutableList.copyOf(tags);
    }

    public TagList(List<Tag> tagsAsList) {
        tagList = ImmutableList.copyOf(tagsAsList);
    }

    public List<Tag> tagList() {
        return tagList;
    }

    public String asString() {
        StringBuilder sb = new StringBuilder();
        for (Tag tag : tagList) {
            sb.append(tag.asString()).append(", ");
        }
        if(sb.toString().endsWith(", ")){
            sb.delete(sb.length()-", ".length(),sb.length());
        }
        return sb.toString();
    }

    public boolean isNull() {
        return false;
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
