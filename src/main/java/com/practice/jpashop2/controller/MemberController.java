package com.practice.jpashop2.controller;

import com.practice.jpashop2.domain.Address;
import com.practice.jpashop2.domain.Member;
import com.practice.jpashop2.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model)
    {
        model.addAttribute("memberForm",new MemberForm());
        return "members/createMemberForm";

    }

    @PostMapping("members/new")
    public String create(@Valid MemberForm memberForm, BindingResult result)
    {
        //오류났을 때
        if(result.hasErrors())
        {
            return "members/createMemberForm";
        }
        Address address = new Address(memberForm.getCity(), memberForm.getStreet(), memberForm.getZipcode());

        Member member = new Member();
        member.setName(memberForm.getName());
        member.setAddress(address);

        memberService.join(member);

        log.info("save member");

        return "redirect:/";
    }

    //회원 목록 조회
    @GetMapping("/members")
    public String list(Model model)
    {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members",members);

        return "members/memberList";
    }

}
