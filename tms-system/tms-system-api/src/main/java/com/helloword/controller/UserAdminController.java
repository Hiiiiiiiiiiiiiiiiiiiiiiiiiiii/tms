package com.helloword.controller;

import com.helloword.entity.Account;
import com.helloword.entity.Role;
import com.helloword.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/system")
public class UserAdminController {

    @Autowired
    HomeService homeService;

    @GetMapping("/tour/user")
    public String gotoUserAdmin(Model model){
        //获得所有的用户信息
        //1.旅游局管理用户
        List<Account> accountList = homeService.findAllTravelAccount();
        model.addAttribute("accountList",accountList);
        //查询所有角色
        List<Role> roleList = homeService.findAllRole();
        model.addAttribute("roleList",roleList);
        //2.综合管理用户
        //3.结算管理用户
        //4.售票用户
        return "userAdmin/tourUser";
    }
    @PostMapping("/tour/user")
    public String createNewTourUser(RedirectAttributes redirectAttributes,
                                    Account account, String[] roleName){
        //1.添加新旅游局用户
        int accountId = homeService.insertAccount(account);
        //2.用户与角色建立关系
        for(String role : roleName){
            homeService.insertRoleRelationship(role,accountId);
        }
        redirectAttributes.addFlashAttribute("message","添加新用户成功!");
        return "redirect:/system/tour/user";
    }
}
