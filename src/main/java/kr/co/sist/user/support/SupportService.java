package kr.co.sist.user.support;

import java.io.File;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SupportService {

    private final SupportDAO dao;

    // 1. WebConfig에서 사용하는 팀 공통 업로드 경로 주입
    @Value("${user.upload-dir}")
    private String uploadDir;

    public SupportService(SupportDAO dao) {
        this.dao = dao;
    }

    // ===== 공지/FAQ =====
    public List<SupportDomain> getList(String boardType, String keyword) throws SQLException {
        SupportDTO dto = new SupportDTO();
        dto.setBoardType(boardType);
        dto.setKeyword(keyword);
        return dao.selectSupportList(dto);
    }

    public SupportDomain getDetail(int boardNum, String boardType) throws SQLException {
        SupportDTO dto = new SupportDTO();
        dto.setBoardNum(boardNum);
        dto.setBoardType(boardType);
        return dao.selectSupportDetail(dto);
    }

    // ===== Ask(1:1문의) =====
 // SupportService.java
    public List<UserAskDomain> getMyAskList(String userId, String keyword) throws SQLException {
        // DAO에 userId와 keyword를 함께 전달
        return dao.selectMyAskList(userId, keyword);
    }

    public UserAskDomain getMyAskDetail(String userId, int askNum) throws SQLException {
        UserAskDomain detail = dao.selectMyAskDetail(userId, askNum);
        if (detail != null) {
            detail.setImgList(dao.selectAskImgList(askNum));
        }
        return detail;
    }

    public void writeAsk(String userId, UserAskDTO dto) throws Exception {
        dto.setUserId(userId);

        // 1) 문의 내역 기본 정보 저장 (askNum 생성)
        dao.insertAsk(dto);
        int askNum = dto.getAskNum();

        // 2) 파일 저장 처리
        if (dto.getFiles() == null) return;

        // 2-1. 저장 경로를 팀 공통 경로 하위의 'ask' 폴더로 설정
        // WebConfig의 /upload/** 매핑을 타기 위해 uploadDir 내부로 경로를 잡습니다.
        File dir = new File(uploadDir, "ask"); 
        if (!dir.exists()) {
            dir.mkdirs();
        }

        for (MultipartFile mf : dto.getFiles()) {
            if (mf == null || mf.isEmpty()) continue;

            String original = mf.getOriginalFilename();
            String ext = "";
            if (original != null && original.lastIndexOf(".") > -1) {
                ext = original.substring(original.lastIndexOf(".")).toLowerCase();
            }

            // 파일명 충돌 방지 및 보안을 위해 UUID 사용
            String saveName = "ASK_" + askNum + "_" + UUID.randomUUID().toString().replace("-", "") + ext;
            File saveFile = new File(dir, saveName);

            // 실제 HDD에 파일 물리적 저장
            mf.transferTo(saveFile);

            // 3) DB에는 파일명만 저장
            // 결과적으로 HTML에서 /upload/ask/파일명 으로 접근하게 됩니다.
            dao.insertAskImg(askNum, saveName); 
        }
    }
 // 기존 코드 하단에 추가
    public void modifyAsk(String userId, UserAskDTO dto) throws Exception {
        // 1. 답변 상태 확인 (이미 답변이 있으면 수정 불가)
        UserAskDomain detail = dao.selectMyAskDetail(userId, dto.getAskNum());
        if (detail == null || detail.getAnswerText() != null) {
            throw new IllegalStateException("답변이 완료된 문의는 수정할 수 없습니다.");
        }

        // 2. 제목 및 내용 수정
        dto.setUserId(userId);
        dao.updateAsk(dto); // 상단에 작성한 update 쿼리 호출

        // 3. 새로운 파일이 업로드된 경우에만 이미지 교체
        if (dto.getFiles() != null && !dto.getFiles().get(0).isEmpty()) {
            
            // 3-1. DB에서 기존 이미지 정보 삭제 (실제 파일 삭제 로직은 팀 정책에 따라 추가)
            dao.deleteAskImg(dto.getAskNum());

            // 3-2. 새 파일 저장 (기존 writeAsk 로직 활용)
            File dir = new File(uploadDir, "ask");
            for (MultipartFile mf : dto.getFiles()) {
                if (mf == null || mf.isEmpty()) continue;

                String saveName = "ASK_" + dto.getAskNum() + "_" + UUID.randomUUID().toString().replace("-", "") + getExt(mf.getOriginalFilename());
                mf.transferTo(new File(dir, saveName));

                // 3-3. DB에 새 이미지 정보 삽입
                dao.insertAskImg(dto.getAskNum(), saveName);
            }
        }
    }

    // 확장자 추출 헬퍼 메서드
    private String getExt(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
    }

    /**
     * 경로 정규화 (필요 시 사용)
     */
    private String normalizeDir(String path) {
        if (path == null) return "";
        String p = path.trim().replace("\\", "/");
        if (!p.endsWith("/")) p += "/";
        return p.replace("/", File.separator);
    }
}