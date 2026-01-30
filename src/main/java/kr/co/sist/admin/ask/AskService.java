package kr.co.sist.admin.ask;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AskService {

    private final AskDAO dao;

    public AskService(AskDAO dao) {
        this.dao = dao;
    }

    public int getTotalCount(AskDTO dto) {
        return dao.selectTotalCount(dto);
    }

    public List<AskDomain> getAskList(AskDTO dto) {
        return dao.selectAskList(dto);
    }

    public AskDomain getAskDetail(int askNum) {
        return dao.selectAskDetail(askNum);
    }

    public void answerAsk(AskDTO dto) {
        dao.updateAnswer(dto);
    }
}
