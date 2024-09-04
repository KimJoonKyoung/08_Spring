package org.scoula.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.scoula.board.domain.BoardAttachmentVO;
import org.scoula.board.dto.BoardDTO;
import org.scoula.board.service.BoardService;
import org.scoula.common.util.UploadFiles;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.File;

@Controller
@Log4j
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    final private BoardService service;
    //return 값이 void기 때문에 뷰도 경로가 동일(board/list)
    @GetMapping("/list")
    public void list(Model model) {
        log.info("list");
        model.addAttribute("list", service.getList());
    }
    @GetMapping("/create")
    public void create() {
        log.info("create");

    }
    @PostMapping("/create")
    public String create(BoardDTO board,
                         RedirectAttributes ra) {
//        log.info("create"+board);


        service.create(board);
        ra.addFlashAttribute("result", board.getNo());
        return "redirect:/board/list";
    }

    @GetMapping({"/get", "/update"})
    public void get(@RequestParam("no") Long no, Model model) {
        log.info("get or update");
        model.addAttribute("board", service.get(no));
    }
    @PostMapping("/update")
    public String update(BoardDTO board) {
        log.info("update:"+board);
        service.update(board);
        return "redirect:/board/list";
    }
    @PostMapping("/delete")
    public String delete(@RequestParam("no") Long no) {
        log.info("delete:"+no);
        service.delete(no);
        return "redirect:/board/list";
    }

    @GetMapping("/download/{no}")
    @ResponseBody
    public void download(@PathVariable("no") Long no, HttpServletResponse response) throws Exception{

        BoardAttachmentVO attach = service.getAttachment(no);

        File file = new File(attach.getPath());

        UploadFiles.download(response, file, attach.getFilename());
    }
}
