package com.helloword.controller;

import com.google.gson.Gson;
import com.helloword.entity.Account;
import com.helloword.entity.Role;
import com.helloword.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/system")
public class UserAdminController {
    //2.综合管理用户
    //3.结算管理用户
    //4.售票用户
    @Autowired
    HomeService homeService;

    /**
     * 跳转到旅游局用户管理页面
     * @param model
     * @return
     */
    @GetMapping("/tour/user")
    public String gotoUserAdmin(Model model){
        //获得所有的旅游局用户信息
        List<Account> accountList = homeService.findAllTravelAccount();
        model.addAttribute("accountList",accountList);
        //查询所有角色
        List<Role> roleList = homeService.findAllRole();
        model.addAttribute("roleList",roleList);
        return "userAdmin/tourUser";
    }

    /**
     * 添加新的旅游局用户
     * @param redirectAttributes
     * @param account
     * @param roleName
     * @return
     */
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

    /**
     * 异步 根据旅游局id获取旅游局对象
     * @param accountId
     * @return
     */
    @PostMapping(value="/tour/get/account",produces={"text/html;charset=UTF-8"})
    @ResponseBody
    public String getAccount(int accountId){
        Account account = homeService.findTravelAccountByAccountId(accountId);
        Gson gson = new Gson();
        String accountJson = gson.toJson(account);
        return accountJson;
    }

    @PostMapping("/tour/change/account")
    public String changeAccount(int id,String accountState,
                                String [] roleName,
                                RedirectAttributes redirectAttributes){
        //修改旅游局用户值
        homeService.tourUpdate(id,accountState,roleName);
        redirectAttributes.addFlashAttribute("message","修改用户信息成功!!");
        return "redirect:/system/tour/user";
    }
    @PostMapping("/tour/user/delete")
    public String deleteAccount(int accountId
            ,RedirectAttributes redirectAttributes){

        //删除该用户的所有信息
        homeService.deleteAccountByAccountId(accountId);
        redirectAttributes.addFlashAttribute("message","删除用户成功!");
        return "redirect:/system/tour/user";
    }
}
