package kr.co.sist.admin.ask;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class AskService {

    private final AskDAO aDAO;

    public AskService(AskDAO aDAO) {
        this.aDAO = aDAO;
    }

    public List<AskDomain> getAskList(AskDTO dto) {
        try {
            return aDAO.selectAskList(dto);
        } catch (Exception e) {
            throw new RuntimeException("문의 목록 조회 중 오류", e);
        }
    }

    public int getTotalCount(AskDTO dto) {
        try {
            return aDAO.selectTotalCount(dto);
        } catch (Exception e) {
            throw new RuntimeException("문의 총 개수 조회 중 오류", e);
        }
    }

    public AskDomain getAskDetail(int askNum) {
        try {
            return aDAO.selectAskDetail(askNum);
        } catch (Exception e) {
            throw new RuntimeException("문의 상세 조회 중 오류", e);
        }
    }

    public int answerAsk(AskDTO dto) {
        try {
            return aDAO.updateAnswer(dto);
        } catch (Exception e) {
            throw new RuntimeException("문의 답변 처리 중 오류", e);
        }
    }
    public List<String> getAskImages(int askNum) {
        try {
            return aDAO.selectAskImages(askNum);
        } catch (Exception e) {
            throw new RuntimeException("문의 이미지 조회 중 오류", e);
        }
    }
}
