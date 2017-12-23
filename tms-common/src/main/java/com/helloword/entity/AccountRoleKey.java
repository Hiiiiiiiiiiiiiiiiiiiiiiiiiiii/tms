package com.helloword.entity;

import java.io.Serializable;

/**
 * @author 
 */
public class AccountRoleKey implements Serializable {
    private Integer roleId;

    private Integer accountId;

    private static final long serialVersionUID = 1L;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }
}