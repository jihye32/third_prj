package kr.co.sist.admin.board;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import kr.co.sist.dao.MyBatisHandler;

@Repository
public class BoardDAO {

    // 페이징용 전체 개수
    public int selectTotalCount(BoardDTO dto) throws SQLException {
        int totalCnt = 0;

        SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(false);
        totalCnt = ss.selectOne("kr.co.sist.admin.board.selectTotalCount", dto);

        if (ss != null) ss.close();
        return totalCnt;
    }

    // 목록 조회
    public List<BoardDomain> selectBoardList(BoardDTO dto) throws SQLException {
        List<BoardDomain> list = null;

        SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(false);
        list = ss.selectList("kr.co.sist.admin.board.selectBoardList", dto);

        if (ss != null) ss.close();
        return list;
    }

    // 상세 조회
    public BoardDomain selectBoardDetail(int boardNum) throws SQLException {
        BoardDomain detail = null;

        SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(false);
        detail = ss.selectOne("kr.co.sist.admin.board.selectBoardDetail", boardNum);

        if (ss != null) ss.close();
        return detail;
    }

    // 등록
    public void insertBoard(BoardDTO dto) throws PersistenceException {
        SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(true);
        ss.insert("kr.co.sist.admin.board.insertBoard", dto);

        if (ss != null) ss.close();
    }

    // 수정
    public int updateBoard(BoardDTO dto) throws SQLException {
        int cnt = 0;

        SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(true);
        cnt = ss.update("kr.co.sist.admin.board.updateBoard", dto);

        if (ss != null) ss.close();
        return cnt;
    }

    // 삭제(논리삭제)
    public int updateDeleteFlag(int boardNum) throws SQLException {
        int cnt = 0;

        SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(true);
        cnt = ss.update("kr.co.sist.admin.board.updateDeleteFlag", boardNum);

        if (ss != null) ss.close();
        return cnt;
    }
}
