package com.example.sahibiden.codecase.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sahibiden.codecase.model.Notice;
import com.example.sahibiden.codecase.model.Status;

public interface NoticeRepository extends JpaRepository<Notice, Integer> {
	
	List<Notice> findByTitleAndDetail(String title,String detail);

}
