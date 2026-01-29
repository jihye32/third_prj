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
        SqlSession ss = null;
        try {
            ss = MyBatisHandler.getInstance().getMyBatisHandler(false);
            return ss.selectOne(
                "kr.co.sist.admin.board.selectTotalCount", dto
            );
        } finally {
            if (ss != null) ss.close();
        }
    }

    // 목록 조회
    public List<BoardDomain> selectBoardList(BoardDTO dto) throws SQLException {
        SqlSession ss = null;
        try {
            ss = MyBatisHandler.getInstance().getMyBatisHandler(false);
            return ss.selectList(
                "kr.co.sist.admin.board.selectBoardList", dto
            );
        } finally {
            if (ss != null) ss.close();
        }
    }

    // 상세 조회
    public BoardDomain selectBoardDetail(int boardNum) throws SQLException {
        SqlSession ss = null;
        try {
            ss = MyBatisHandler.getInstance().getMyBatisHandler(false);
            return ss.selectOne(
                "kr.co.sist.admin.board.selectBoardDetail", boardNum
            );
        } finally {
            if (ss != null) ss.close();
        }
    }

    // 등록
    public void insertBoard(BoardDTO dto) throws PersistenceException {
        SqlSession ss = null;
        try {
            ss = MyBatisHandler.getInstance().getMyBatisHandler(true);
            ss.insert(
                "kr.co.sist.admin.board.insertBoard", dto
            );
        } finally {
            if (ss != null) ss.close();
        }
    }

    // 수정
    public int updateBoard(BoardDTO dto) throws SQLException {
        SqlSession ss = null;
        try {
            ss = MyBatisHandler.getInstance().getMyBatisHandler(true);
            return ss.update(
                "kr.co.sist.admin.board.updateBoard", dto
            );
        } finally {
            if (ss != null) ss.close();
        }
    }

    // 삭제(논리삭제)
    public int updateDeleteFlag(int boardNum) throws SQLException {
        SqlSession ss = null;
        try {
            ss = MyBatisHandler.getInstance().getMyBatisHandler(true);
            return ss.update(
                "kr.co.sist.admin.board.updateDeleteFlag", boardNum
            );
        } finally {
            if (ss != null) ss.close();
        }
    }
}