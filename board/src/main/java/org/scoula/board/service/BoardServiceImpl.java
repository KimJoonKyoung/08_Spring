package org.scoula.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.scoula.board.domain.BoardAttachmentVO;
import org.scoula.board.domain.BoardVO;
import org.scoula.board.dto.BoardDTO;
import org.scoula.board.mapper.BoardMapper;
import org.scoula.common.util.UploadFiles;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.scoula.common.util.UploadFiles.upload;

@Log4j
@Service // 서비스 역할하는 빈등록
@RequiredArgsConstructor // final 필드로 생성자 생성자 추가
public class BoardServiceImpl implements BoardService{
    private final static String BASE_DIR = "c:/upload/board";

    //생성자가 하나라면 주입가능
    final private BoardMapper mapper;


    @Override
    public List<BoardDTO> getList() {
       log.info("getList.......");
       return mapper.getList().stream() //BoardVO의 스트림
               .map(BoardDTO::of) //전부 BoardDTO로 변환-> BoardDTO의 스트림
               .toList(); //BoardDTO의 리스트로 변환
    }

    @Override
    public BoardDTO get(Long no) {
        log.info("get......."+no);
        BoardDTO board= BoardDTO.of(mapper.get(no));
        //board 객체가 null이면 NoSuchElementException 예외 발생, null이 아니면 해당 객체 반환
        return Optional.ofNullable(board)
                .orElseThrow(NoSuchElementException::new);

    }

    @Transactional
    @Override
    public void create(BoardDTO board) {
        log.info("create......"+board);

        /*BoardVO vo=board.toVo();
        mapper.create(vo);
        board.setNo(vo.getNo());*/

        BoardVO boardVO = board.toVo();
        mapper.create(boardVO);

        List<MultipartFile> files = board.getFiles();
        if(files != null && !files.isEmpty()) {
            upload(boardVO.getNo(), files);
        }
    }

    private void upload(Long bno, List<MultipartFile> files) {
        for(MultipartFile part : files) {
            if(part.isEmpty()) continue;
            try {
                String uploadPath = UploadFiles.upload(BASE_DIR, part);
                BoardAttachmentVO attach = BoardAttachmentVO.of(part, bno, uploadPath);
                mapper.createAttachment(attach);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public boolean update(BoardDTO board) {

     log.info("update......"+board);
     return mapper.update(board.toVo())==1;

            }

    @Override
    public boolean delete(Long no) {
        log.info("delete......"+no);
        return mapper.delete(no)==1;
    }

    @Override
    public BoardAttachmentVO getAttachment(Long no) {
        return mapper.getAttachment(no);
    }

    @Override
    public boolean deleteAttachment(Long no) {
        return mapper.deleteAttachment(no)==1;
    }
}
