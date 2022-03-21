package com.example.sahibiden.codecase.DaoImpl;

import java.util.List;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sahibiden.codecase.Dao.NoticeDao;
import com.example.sahibiden.codecase.model.Notice;
import com.example.sahibiden.codecase.repository.NoticeRepository;

@Transactional
@Service
public class NoticeDaoImpl implements NoticeDao{

	@Autowired
	NoticeRepository noticeRepository;
	
	@Override
	public List<Notice> getNoticeAll() {
		return noticeRepository.findAll();
	}

	@Override
	public Notice getNoticeById(Integer id) {
		return noticeRepository.findById(id).get();
	}

	@Transactional(value = TxType.REQUIRES_NEW)
	@Override
	public Notice saveNoticeAndFlush(Notice notice) {
		return noticeRepository.saveAndFlush(notice);
	}

	@Override
	public void deleteNoticeById(Integer id) {
		noticeRepository.deleteById(id);
	}

	@Override
	public void deleteNotice(Notice notice) {
		noticeRepository.delete(notice);		
	}

	@Override
	public Notice saveNotice(Notice notice) {
		return noticeRepository.save(notice);
	}

	@Override
	public List<Notice> getNoticeByTitleAndDetail(String title, String Detail) {
		return noticeRepository.findByTitleAndDetail(title, Detail);
	}

}
