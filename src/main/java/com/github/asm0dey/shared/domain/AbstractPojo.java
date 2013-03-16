package com.github.asm0dey.shared.domain;

import com.google.gwt.user.client.rpc.IsSerializable;

import javax.persistence.*;

/**
 * Human: finkel
 * <p/>
 * Date: 15.03.13
 * <p/>
 * Time: 11:12
 */
@MappedSuperclass
public abstract class AbstractPojo implements IsSerializable {
	@Version
	int consistencyVersion;

	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId( Long id ) {
		this.id = id;
	}

	public int getConsistencyVersion() {
		return consistencyVersion;
	}

	public void setConsistencyVersion( int consistencyVersion ) {
		this.consistencyVersion = consistencyVersion;
	}

	@Override
	public boolean equals( Object o ) {
		if ( this == o )
			return true;
		if ( !( o instanceof AbstractPojo ) )
			return false;

		AbstractPojo that = (AbstractPojo) o;

		return consistencyVersion == that.consistencyVersion && id == that.id;

	}

	@Override
	public int hashCode() {
		int result = consistencyVersion;
		result = 31 * result + (int) ( id ^ ( id >>> 32 ) );
		return result;
	}

    @Override
    public String toString() {
        return "AbstractPojo{" +
                "consistencyVersion=" + consistencyVersion +
                ", id=" + id +
                '}';
    }
}
