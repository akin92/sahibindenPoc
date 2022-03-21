package com.example.sahibiden.codecase.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sahibiden.codecase.model.Status;

public interface StatusRepository extends JpaRepository<Status, Integer> {

	Status findByStatus(String status);
}
