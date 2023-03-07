package com.yunlan.baselibrary.util;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * EditText输入金额监听
 */
public class MoneyTextWatcher implements TextWatcher {

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        String temp = editable.toString();
        if (".".equals(temp)) editable.delete(0, temp.length());
        else if (temp.contains(".")) {
            int posDot = temp.indexOf(".");
            if (temp.length() - posDot > 2) editable.delete(posDot + 3, temp.length());
        }
    }

}
