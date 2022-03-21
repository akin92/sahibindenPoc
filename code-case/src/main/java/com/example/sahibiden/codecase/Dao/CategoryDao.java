package com.example.sahibiden.codecase.Dao;

import java.util.List;

import javax.transaction.Transactional;

import com.example.sahibiden.codecase.model.Category;

@Transactional
public interface CategoryDao {

	List<Category> getCategoryAll();
	
	Category getCategoryById(Integer id);
	
	List<Category> saveAll(List<Category> categoryList);
	
	
}
