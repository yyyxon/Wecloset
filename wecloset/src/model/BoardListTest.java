package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import config.MybatisService;

public class BoardListTest {

	SqlSession session=MybatisService.getFactory().openSession();
	
	public List<Object> memberList(){
		List<Object> items=new ArrayList<Object>();
		Map<String,Object> map=
				new HashMap<String,Object>();		
		items = session.selectList("Test.getList",map);
		return items;
	}
	
}




