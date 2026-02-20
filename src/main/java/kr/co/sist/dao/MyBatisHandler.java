package kr.co.sist.dao;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatisHandler {
	private static MyBatisHandler mbh;
	
	private static SqlSessionFactory ssf;
		
	private MyBatisHandler() {
//		org.apache.ibatis.logging.LogFactory.useLog4J2Logging();
	}// MyBatisHandler

	public static MyBatisHandler getInstance() {
		if (mbh == null) {
			mbh = new MyBatisHandler(); 
		} // end if

		return mbh;

	}// getInstance
	
	private static SqlSessionFactory getSessionFactory() throws IOException {
		if(ssf == null) {
			// 1.설정용 XML과 연결
			// Resources.getResourceAsReader는 classpath 위치에서 읽어들인다. 
			// ( fat-jar : resources에서 파일을 읽어 들여야한다.)
//			Reader reader = Resources.getResourceAsReader("kr/co/sist/dao/mybatis-config.xml");
		
			InputStream is = Thread.currentThread()// 현재 실행중인(java -jar) 스레드를 얻어와서
					.getContextClassLoader()// class를 로딩하고
					.getResourceAsStream("mybatis-config.xml");// ResourceAsStream으로 resources에 접근
			
			// 2.SqlSessionFactoryBuild 생성
			SqlSessionFactoryBuilder ssfb = new SqlSessionFactoryBuilder();
			
			// 3.SqlSessionFactory를 building 한다.
//			ssf = ssfb.build(reader);
//			if(reader != null) {
//				reader.close();
//			}// end if
			ssf = ssfb.build(is);
			
		}// end if
		
		return ssf;
		
	}// getSessionFactory
	
	/**
	 * MyBatisHandler 얻기
	 * @param autoCommitFlag true-autocommit, false-non autocommit
	 * @return
	 */
	public SqlSession getMyBatisHandler(boolean autoCommitFlag) {
		SqlSession ss = null;
		
		try {
			ss = getSessionFactory().openSession(autoCommitFlag);
		} catch (IOException e) {
			e.printStackTrace();
		}// end catch
		
		return ss;
	}// getMyBatisHandler
	
	
}// class
