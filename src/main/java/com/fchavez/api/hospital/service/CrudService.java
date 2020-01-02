package com.fchavez.api.hospital.service;

import java.util.List;
import java.util.Optional;

public interface CrudService <T>{
	
	T insertOrUpdate(T entity);
	Optional<T> getOne(Integer id);

	List<T> getAll();

	void delete(Integer id);

}
