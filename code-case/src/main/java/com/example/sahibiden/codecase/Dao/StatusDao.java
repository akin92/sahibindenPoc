package com.example.sahibiden.codecase.Dao;

import java.util.List;

import javax.transaction.Transactional;

import com.example.sahibiden.codecase.model.Status;

@Transactional
public interface StatusDao {

	List<Status> getStatusAll();
	
	Status getStatusById(Integer id);
	
	List<Status> saveAll(List<Status> statusList);
	
	Status getStatusByName(String status);
}
