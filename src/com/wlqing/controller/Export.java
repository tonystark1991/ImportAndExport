package com.wlqing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wlqing.dao.UserDao;

@Controller

public class Export {
	@Autowired
	private UserDao userDao;
	@RequestMapping("/export")
	public String ExportTest(){
		userDao.exportDB();
		return "success";
	}
	
}
