package kr.co.sist.admin.board;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class BoardService {

    private final BoardDAO bDAO;

    public BoardService(BoardDAO bDAO) {
        this.bDAO = bDAO;
    }

    public List<BoardDomain> getBoardList(BoardDTO dto) {
        try {
            return bDAO.selectBoardList(dto);
        } catch (SQLException e) {
            throw new RuntimeException("게시글 목록 조회 중 오류", e);
        }
    }

    public int getTotalCount(BoardDTO dto) {
        try {
            return bDAO.selectTotalCount(dto);
        } catch (SQLException e) {
            throw new RuntimeException("게시글 총 개수 조회 중 오류", e);
        }
    }

    public BoardDomain getBoardDetail(int boardNum) {
        try {
            return bDAO.selectBoardDetail(boardNum);
        } catch (SQLException e) {
            throw new RuntimeException("게시글 상세 조회 중 오류", e);
        }
    }

    public void addBoard(BoardDTO dto) {
        try {
            bDAO.insertBoard(dto);
        } catch (Exception e) {
            throw new RuntimeException("게시글 등록 중 오류", e);
        }
    }

    public int modifyBoard(BoardDTO dto) {
        try {
            return bDAO.updateBoard(dto);
        } catch (SQLException e) {
            throw new RuntimeException("게시글 수정 중 오류", e);
        }
    }

    public int removeBoard(int boardNum) {
        try {
            return bDAO.updateDeleteFlag(boardNum);
        } catch (SQLException e) {
            throw new RuntimeException("게시글 삭제 중 오류", e);
        }
    }
}
