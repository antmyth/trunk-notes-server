package apn.trunknotes.types;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import static java.lang.String.format;

/**
 * User: antonion
 * Date: 28/02/14 16:28
 */
public class Action {
	private final ActionName name;
	private final ActionDate date;
	private final ActionText text;

	public Action(ActionName name, ActionDate date, ActionText text) {
		this.name = name;
		this.date = date;
		this.text = text;
	}

	public String prettyPrint(){
		return format("@%s %s %s", name.asString(), date.isNull()?"":date.asString(), text.asString());
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
