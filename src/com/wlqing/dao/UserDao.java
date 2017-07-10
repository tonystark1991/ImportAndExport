package com.wlqing.dao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class  UserDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void exportDB() {
		String sql = "select * from userinfo";
		List userList = new ArrayList();
		userList = jdbcTemplate.queryForList(sql);
		// ArrayList实现了序列化接口，可将其对象导出，即userList可以导出。
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream("d:/users.txt"));
			oos.writeObject(userList);
			oos.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (oos != null)
				try {
					oos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

	public void importDB() {
		List<Map> userList = new ArrayList<Map>();
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream("d:/users.txt"));
			userList = (List<Map>) ois.readObject();
			// userList做为ArrayList的对象，本身已经实现了序列化接口，所以可以接收文件内容
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ois != null)
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		for (int i = 0; i < userList.size(); i++) {
			Map map = userList.get(i);
			Set set = map.keySet();
			String str = "";
			String str1 = "";
			Object[] objects = new Object[map.size()];

			for (Object obj : set) {
				str = str + obj + ",";
				str1 = str1 + "?,";
				int j = str1.length() / 2 - 1;
				objects[j] = map.get(obj);
			}
			str=str.substring(0, str.length()-1);
			str1=str1.substring(0, str1.length()-1);
			String sql = "insert into usertest("+str+") value("+str1+")";
			System.out.println(sql);
			jdbcTemplate.update(sql, objects);
		}
	}

}
