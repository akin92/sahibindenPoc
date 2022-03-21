package com.example.sahibiden.codecase.controller;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sahibiden.codecase.Dao.CategoryDao;
import com.example.sahibiden.codecase.DaoImpl.CategoryDaoImpl;
import com.example.sahibiden.codecase.DaoImpl.NoticeDaoImpl;
import com.example.sahibiden.codecase.DaoImpl.StatusDaoImpl;
import com.example.sahibiden.codecase.enums.CategoryEnums;
import com.example.sahibiden.codecase.enums.StatusEnum;
import com.example.sahibiden.codecase.model.Category;
import com.example.sahibiden.codecase.model.Notice;
import com.example.sahibiden.codecase.model.Status;
import com.example.sahibiden.codecase.repository.CategoryRepository;
import com.example.sahibiden.codecase.repository.NoticeRepository;
import com.example.sahibiden.codecase.repository.StatusRepository;

@RestController
@RequestMapping("/api")
public class RestApiController {
	
	 private static final Logger logger = LogManager.getLogger(RestApiController.class);
	
	@Autowired
	CategoryDaoImpl categoryDaoImpl;
	
	@Autowired
	StatusDaoImpl  statusDaoImpl;
	
	@Autowired
	NoticeDaoImpl  noticeDaoImpl ;
	
	
	List<Category> categoryList;
	
	List<Status> statusList;
	
	@PostConstruct
	public void fillTables() {
		 LocalDate today = LocalDate.now();
		Category  category1 =new Category(1, "EMLAK", 4);
		Category  category2 =new Category(2, "VASITA", 3);
		Category  category3 =new Category(3, "ALISVERIS", 8);
		Category  category4 =new Category(4, "DIGER", 8);
	    categoryList = new ArrayList<Category>();
		categoryList.add(category1);
		categoryList.add(category2);
		categoryList.add(category3);
		categoryList.add(category4);
		categoryDaoImpl.saveAll(categoryList);
		
		statusList = new ArrayList<Status>();
		Status status1 = new Status(1,"PENDING");
		Status status2 = new Status(2,"ACTIVE");
		Status status3 = new Status(3,"DEACTIVE");
		statusList.add(status1);
		statusList.add(status2);
		statusList.add(status3);
		statusDaoImpl.saveAll(statusList);
		
		Status pending = statusDaoImpl.getStatusById(5);
		Category categoryEmlak = categoryDaoImpl.getCategoryById(1);
		Notice notice1 = new Notice(1,"Sahibinden Temiz","Al gotur Çok kaçar Az yakar Pişman olmazsın..",false,categoryEmlak,pending,today,today);
		Notice notice2 = new Notice(2,"Sahibinden Temiz2","Al gotur Çok kaçar Az yakar Pişman olmazsın2..",false,categoryEmlak,pending,today,today);
		noticeDaoImpl.saveNotice(notice1);
		noticeDaoImpl.saveNotice(notice2);
		
		
	}
	
	@GetMapping("/categories")
	public ResponseEntity<List<Category>> getAllCategories(){
		try {
			List<Category> categories = new ArrayList<Category>();
			categoryDaoImpl.getCategoryAll().forEach(categories::add);
			
			if (categories.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
			return new ResponseEntity<>(categories, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping("/notices")
	public ResponseEntity<List<Notice>> getAllNotices(){
		try {
			List<Notice> notices = new ArrayList<Notice>();
			noticeDaoImpl.getNoticeAll().forEach(notices::add);
			
			if (notices.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
			return new ResponseEntity<>(notices, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping("/status")
	public ResponseEntity<List<Status>> getAllStatus(){
		try {
			List<Status> statusList = new ArrayList<Status>();
			statusDaoImpl.getStatusAll().forEach(statusList::add);
			
			if (statusList.isEmpty()) {
				logger.info("Not Found Status Recods on Db");	
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
			return new ResponseEntity<>(statusList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PostMapping("/notices")
	public ResponseEntity<Notice> createTutorial(@RequestBody Notice notice) {
		try {
			Status newstatus = new Status();
		    LocalDate today = LocalDate.now();
		    LocalDate expire = null;
		    Category  tempCategory= null;
			
			if(notice.getCategory()!=null ) {
				Integer categoryId = notice.getCategory().getId();
			    tempCategory = categoryDaoImpl.getCategoryById(categoryId);
			    if(tempCategory.getName().equalsIgnoreCase(CategoryEnums.ALISVERIS.toString())) {
			    	newstatus =  statusDaoImpl.getStatusByName(StatusEnum.ACTIVE.toString());
			    }else {
			    	newstatus = statusDaoImpl.getStatusByName(StatusEnum.PENDING.toString());
			    }
			    
			    expire = today.plus(tempCategory.getExpire() , ChronoUnit.WEEKS);
			
			}
			
			boolean repeating = validateRepeating(notice,tempCategory);
		
			Notice tempNotice = new Notice(notice.getTitle(),notice.getDetail());
			tempNotice.setStatus(newstatus);
			tempNotice.setStartDate(today);
			tempNotice.setFinihDate(expire);
			tempNotice.setCategory(tempCategory);
			tempNotice.setRepeating(repeating);
			
			Notice newNotice = noticeDaoImpl.saveNoticeAndFlush(tempNotice);
			logger.info("New Notice was Added:"+ newNotice.toString());	
			
			return new ResponseEntity<>(newNotice, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	private boolean validateRepeating(Notice notice, Category tempCategory) {
		List<Notice> listNotice = noticeDaoImpl.getNoticeByTitleAndDetail(notice.getTitle(), notice.getDetail());
		
		if(!listNotice.isEmpty()) {
			for(Notice noticeDb : listNotice) {
				if(noticeDb.getCategory().getName().equalsIgnoreCase(tempCategory.getName())) {
					return true;
				}
			}
		}
		return false;
	}

	@PutMapping("/notices/{noticeId}/{statusId}")
	public ResponseEntity<Notice> updateTutorial(@PathVariable("noticeId") Integer noticeId, @PathVariable("statusId") Integer statusId) {
		Optional<Notice> noticeData = Optional.of(noticeDaoImpl.getNoticeById(noticeId));
		Optional<Status> status = Optional.of(statusDaoImpl.getStatusById(statusId));
		
		if(!validateStatus(noticeData.get())){
			logger.info("Only Notice which is status is not deactive and not Repeating will be update.. ");	
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		if (noticeData.isPresent() && status.isPresent()) {
			Notice newNotice = noticeData.get();
			newNotice.setStatus(status.get());
			
			return new ResponseEntity<>(noticeDaoImpl.saveNotice(newNotice), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	

	@DeleteMapping("/notices/{noticeId}")
	public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("noticeId") Integer noticeId) {
		try {
			noticeDaoImpl.deleteNoticeById(noticeId);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	private boolean validateStatus(Notice notice) {
		if(notice.getStatus().getStatus().equalsIgnoreCase(StatusEnum.DEACTIVE.toString()) || notice.getRepeating()) {
			return false;
		}
		return true;
	}
	
}
