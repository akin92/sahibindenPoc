package com.example.sahibiden.codecase.Dao;

import java.util.List;

import javax.transaction.Transactional;

import com.example.sahibiden.codecase.model.Notice;

@Transactional
public interface NoticeDao {

	List<Notice> getNoticeAll();
	
	Notice getNoticeById(Integer id);
	
	Notice saveNoticeAndFlush(Notice notice);
	
	Notice saveNotice(Notice notice);
	
	void deleteNoticeById(Integer id);
	
	void deleteNotice(Notice notice);
	
	List<Notice> getNoticeByTitleAndDetail(String title,String Detail);
	
	
}
