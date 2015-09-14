package org.akvelontesttask.domain;

import java.io.Serializable;
/** 
 * Represents entity with id or persistent entity
 * @author baddev
 *
 * @param <ID> represent id type
 */
public interface IEntity<ID extends Serializable> {
	ID getId();
	void setId(ID id);
}
