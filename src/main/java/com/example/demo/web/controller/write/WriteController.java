package com.example.demo.web.controller.write;

import com.example.demo.member.entity.Member;
import com.example.demo.write.entity.Write;
import com.example.demo.write.service.WriteJdbcServiceV2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/writes")
@RequiredArgsConstructor
public class WriteController {

    private final WriteJdbcServiceV2 writeJdbcServiceV2;

    @GetMapping
    public String writes(Model model){
        List<Write> writes = writeJdbcServiceV2.findAllWrite();
        model.addAttribute("writes",writes);
        return "writes/writes";
    }

    @GetMapping("/{writeId}")
    public String write(@PathVariable long writeId, Model model){
        Optional<Write> write = writeJdbcServiceV2.findOneWrite(writeId);
        model.addAttribute("write",write);
        return "writes/write";
    }

    @GetMapping("/add")
    public String addForm(Model model){
        model.addAttribute("write",new Write());
        return "writes/addForm";
    }

    @PostMapping("/add")
    public String addWrite(@Validated @ModelAttribute("write") WriteForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "writes/addForm";
        }

        Write write = new Write(null,form.getTitle(),form.getContent(), form.getMemberId());
        Write createWrite = writeJdbcServiceV2.create(write);
        redirectAttributes.addAttribute("writeId",createWrite.getWriteId());
        redirectAttributes.addAttribute("status",true);
        return "redirect:/writes/{writeId}";
    }

    @GetMapping("/{writeId}/edit")
    public String editForm(@PathVariable Long writeId, Model model){
        Optional<Write> write = writeJdbcServiceV2.findOneWrite(writeId);
        model.addAttribute("write",write);
        return "writes/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @Validated @ModelAttribute("item")  WriteForm writeForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "writes/editForm";
        }

        Write write = new Write(null,writeForm.getTitle(),writeForm.getContent(), writeForm.getMemberId());
        writeJdbcServiceV2.update(itemId, write);

        return "redirect:/writes/{writeId}";
    }

}
