package org.akvelontesttask.service;

import java.util.List;

import org.akvelontesttask.domain.PersonInfo;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service which represents business logic to be performed on {@link PersonInfo} entities
 * @author baddev
 */
public interface PersonInfoService {
	
	/**
	 * @return List of {@link PersonInfo} available objects
	 */
	@Transactional(readOnly = true)
	List<PersonInfo> loadAll();

	/**
	 * @param personInfo new or existed entity of {@link PersonInfo} class
	 * @return {@link PersonInfo} persisted entity
	 */
	@Transactional
	PersonInfo addOrSave(PersonInfo personInfo);

	/**
	 * @param persInfIds is a list of identifiers of {@link PersonInfo} objects which need to be deleted
	 * @return list of deleted {@link PersonInfo} objects
	 */
	@Transactional
	List<PersonInfo> delete(List<Long> persInfIds);

	/**
	 * @param example is a {@link PersonInfo} entity that we need to load
	 * @return first match of similar {@link PersonInfo} entity
	 * @see {@link PersonInfoDao#loadByExample()}
	 */
	@Transactional(readOnly = true)
	PersonInfo load(PersonInfo example);

	/**
	 * @param id is an identifier of entity that we need to load
	 * @return {@link PersonInfo} entity loaded by specified id
	 */
	@Transactional(readOnly = true)
	PersonInfo load(Long id);
}
