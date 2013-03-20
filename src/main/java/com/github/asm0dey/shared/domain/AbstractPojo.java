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
	Short consistencyVersion;
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

	public void setConsistencyVersion( Short consistencyVersion ) {
		this.consistencyVersion = consistencyVersion;
	}

	@Override
	public boolean equals( Object o ) {
		if ( this == o )
			return true;
		if ( !( o instanceof AbstractPojo ) )
			return false;

		AbstractPojo that = (AbstractPojo) o;
		return !( consistencyVersion != null ? !consistencyVersion.equals( that.consistencyVersion ) : that.consistencyVersion != null )
				&& !( id != null ? !id.equals( that.id ) : that.id != null );

	}

	@Override
	public int hashCode() {
		int result = consistencyVersion != null ? consistencyVersion.hashCode() : 0;
		result = 31 * result + ( id != null ? id.hashCode() : 0 );
		return result;
	}
}
