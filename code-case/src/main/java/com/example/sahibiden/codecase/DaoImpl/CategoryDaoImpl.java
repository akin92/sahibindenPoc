package com.example.sahibiden.codecase.DaoImpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sahibiden.codecase.Dao.CategoryDao;
import com.example.sahibiden.codecase.model.Category;
import com.example.sahibiden.codecase.repository.CategoryRepository;

@Transactional
@Service
public class CategoryDaoImpl implements CategoryDao{
	
	@Autowired
	CategoryRepository categoryRepository;

	@Override
	public List<Category> getCategoryAll() {
		return categoryRepository.findAll();
	}

	@Override
	public Category getCategoryById(Integer id) {
		return categoryRepository.findById(id).get();
	}

	@Override
	public List<Category> saveAll(List<Category> categoryList) {
		return categoryRepository.saveAll(categoryList);
	}

}
