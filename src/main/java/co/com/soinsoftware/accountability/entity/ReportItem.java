package co.com.soinsoftware.accountability.entity;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Carlos Rodriguez
 * @since 18/08/2016
 * @version 1.0
 */
public class ReportItem implements Comparable<ReportItem> {

	private final long code;

	private final String name;

	private final int level;

	private final Uap uap;

	private Long value;

	private final Set<ReportItem> reportItemSet;

	public ReportItem(final Uap uap, final Long value) {
		super();
		this.code = uap.getCode();
		this.name = (uap.getLevel() < 3) ? uap.getName().toUpperCase() : uap
				.getName();
		this.level = uap.getLevel();
		this.uap = uap;
		this.value = value;
		this.reportItemSet = new HashSet<>();
	}

	public long getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public int getLevel() {
		return level;
	}

	public Long getValue() {
		return value;
	}

	public void setValue(final long value) {
		this.value = value;
	}

	public Uap getUap() {
		return uap;
	}

	public void addToValue(final long value) {
		this.value += value;
	}

	public Set<ReportItem> getReportItemSet() {
		return reportItemSet;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (code ^ (code >>> 32));
		result = prime * result + level;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReportItem other = (ReportItem) obj;
		if (code != other.code)
			return false;
		if (level != other.level)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public int compareTo(final ReportItem other) {
		final Integer firstLevel = this.level;
		final Integer secondLevel = other.level;
		if (firstLevel.compareTo(secondLevel) == 0) {
			final Long firstCode = this.code;
			final Long secondCode = other.code;
			if (firstCode.compareTo(secondCode) == 0) {
				return this.name.compareTo(other.name);
			} else {
				return firstCode.compareTo(secondCode);
			}
		} else {
			return firstLevel.compareTo(secondLevel);
		}
	}
}