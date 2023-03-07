package com.yunlan.baselibrary.util;

/**
 * Created by lhy on 2022/5/8.
 */

public class AccountBaseUtil {

    static AccountBaseUtil mAccountBaseUtil;
    String accountId;

    public static AccountBaseUtil  getInstance(){
        if (mAccountBaseUtil == null) {
            mAccountBaseUtil = new AccountBaseUtil();
        }
        return mAccountBaseUtil;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public boolean isLogin(){
        return StringUtils.isNotEmpty(accountId);
    }

}
