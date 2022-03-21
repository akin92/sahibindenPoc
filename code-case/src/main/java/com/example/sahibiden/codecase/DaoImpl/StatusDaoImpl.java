package com.example.sahibiden.codecase.DaoImpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sahibiden.codecase.Dao.StatusDao;
import com.example.sahibiden.codecase.model.Status;
import com.example.sahibiden.codecase.repository.StatusRepository;

@Transactional
@Service
public class StatusDaoImpl implements StatusDao{
	
	@Autowired
	StatusRepository statusRepository;

	@Override
	public List<Status> getStatusAll() {
		return statusRepository.findAll();
	}

	@Override
	public Status getStatusById(Integer id) {
		return statusRepository.findById(id).get();
	}

	@Override
	public List<Status> saveAll(List<Status> statusList) {
		return statusRepository.saveAll(statusList);
	}

	@Override
	public Status getStatusByName(String status) {
		return statusRepository.findByStatus(status);
	}

}
