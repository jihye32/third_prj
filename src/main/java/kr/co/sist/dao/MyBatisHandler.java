package kr.co.sist.dao;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatisHandler {
	//Singleton
	private static MyBatisHandler mbh;
	private MyBatisHandler() {
		org.apache.ibatis.logging.LogFactory.useLog4J2Logging();
	}
	public static MyBatisHandler getInstance() {
		if(mbh==null) {
			mbh = new MyBatisHandler();
		}
		return mbh;
	}
	
	private static SqlSessionFactory ssf;
	
	private static SqlSessionFactory getSessFactory() throws IOException {
		if(ssf == null) {
			//1. 설정용 XML과 연결
			Reader reader = Resources.getResourceAsReader("kr/co/sist/dao/mybatis-config.xml");
			//2. SqlSessionFactoryBuild 생성
			SqlSessionFactoryBuilder ssfb = new SqlSessionFactoryBuilder();
			//3. SqlSessionFactory를 building 함
			ssf = ssfb.build(reader);
			
			if(reader != null) reader.close();
		}
		return ssf;
	}
	
	/**
	 * MyBatis Handler 얻기
	 * @param autoCommitFlag true-autocommit, false-none autocommit
	 * @return
	 */
	public SqlSession getMyBatisHandler(boolean autoCommitFlag) {
		SqlSession ss = null;
		
		try {
			ss = getSessFactory().openSession(autoCommitFlag);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return ss;
	}
}
