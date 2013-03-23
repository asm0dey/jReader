package com.github.asm0dey.server.services;

import org.dozer.CustomFieldMapper;
import org.dozer.classmap.ClassMap;
import org.dozer.fieldmap.FieldMap;
import org.hibernate.Hibernate;
import org.hibernate.collection.internal.AbstractPersistentCollection;
import org.springframework.stereotype.Component;

@Component( value = "hibernateCustomMapper" )
public class HibernateInitializedFieldMapper implements CustomFieldMapper {
	public boolean mapField( Object source, Object destination, Object sourceFieldValue, ClassMap classMap, FieldMap fieldMapping ) {
		// if field is initialized, Dozer will continue mapping

		// Check if field is derived from Persistent Collection
		return !Hibernate.isInitialized(sourceFieldValue);

	}
}
