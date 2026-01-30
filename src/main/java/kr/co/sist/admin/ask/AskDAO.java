package kr.co.sist.admin.ask;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import kr.co.sist.dao.MyBatisHandler;

@Repository
public class AskDAO {

    public int selectTotalCount(AskDTO dto) {
        try (SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(false)) {
            return ss.selectOne("kr.co.sist.admin.ask.selectTotalCount", dto);
        }
    }

    public List<AskDomain> selectAskList(AskDTO dto) {
        try (SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(false)) {
            return ss.selectList("kr.co.sist.admin.ask.selectAskList", dto);
        }
    }

    public AskDomain selectAskDetail(int askNum) {
        try (SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(false)) {
            return ss.selectOne("kr.co.sist.admin.ask.selectAskDetail", askNum);
        }
    }

    public void updateAnswer(AskDTO dto) {
        try (SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(true)) {
            ss.update("kr.co.sist.admin.ask.updateAnswer", dto);
        }
    }
}
