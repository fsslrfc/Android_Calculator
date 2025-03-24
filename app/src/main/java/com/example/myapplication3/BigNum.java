package com.example.myapplication3;

public class BigNum {
    private boolean flag;
    private StringBuilder ZS;
    private StringBuilder XS;

    /*
     * 判断正数/负数：flag=1正，flag=0负
     * 判断是否为0：ZS.length()=0 && XS.length()=0（如果运算结束后一个数为0，需要将它设置为正数）
     * 判断整数/小数：XS.length()=0整数，XS.length()>0小数（每一次运算结束后需要判断整数部分/小数部分是否为0，如果是则制空）
     *
     */

    //静态函数：数据预处理
    public static String prefix(String input) {
        StringBuilder res = new StringBuilder("");

        //空处理
        if (input == null || input.isEmpty()) {
            return "+.";
        }

        //符号处理
        boolean flag = true;
        if (input.charAt(0) == '-') {
            flag = false;
        }
        input = input.substring(1);

        int index = input.indexOf('.');

        //整数部分处理
        if (index == -1) {
            res.append(input);
            for (int i = 0; i < res.length(); i++) {
                if (res.charAt(0) == '0') {
                    res.deleteCharAt(0);
                }
                else {
                    break;
                }
            }
            res.append('.');
            if (flag == true) {
                res.insert(0, '+');
            }
            else {
                res.insert(0, '-');
            }
            return res.toString();
        }
        else {
            res.append(input.substring(0, index));
            for (int i = 0; i < res.length(); i++) {
                if (res.charAt(0) == '0') {
                    res.deleteCharAt(0);
                }
                else {
                    break;
                }
            }
            res.append('.');
            input = input.substring(index + 1);
        }

        StringBuilder temp = new StringBuilder(input);
        for (int i = temp.length() - 1; i >= 0; i--) {
            if (temp.charAt(i) == '0') {
                temp.deleteCharAt(i);
            }
        }

        res.append(temp);
        if (flag == true) {
            res.insert(0, '+');
        }
        else {
            res.insert(0, '-');
        }
        return res.toString();
    }

    //构造函数
    public BigNum() {
        this.flag = true;
        this.ZS = new StringBuilder("");
        this.XS = new StringBuilder("");
    }

    public BigNum(String input) {
        StringBuilder str = new StringBuilder(prefix(input));

        if (str.charAt(0) == '+') {
            this.flag = true;
        }
        else {
            this.flag = false;
        }

        int index = str.indexOf(".");
        this.ZS = new StringBuilder(str.substring(1, index));
        this.XS = new StringBuilder(str.substring(index + 1));
    }

    public BigNum(BigNum b) {
        this.flag = b.flag;
        this.ZS = new StringBuilder(b.ZS);
        this.XS = new StringBuilder(b.XS);
    }

    //公有访问方法
    public boolean get_flag() {
        return this.flag;
    }

    public String get_ZS() {
        return this.ZS.toString();
    }

    public String get_XS() {
        return this.XS.toString();
    }

    public String get() {
        StringBuilder res = new StringBuilder("");
        if (this.flag) {
            res.append("+");
        }
        else {
            res.append("-");
        }

        if (ZS.length() == 0) {
            res.append("0");
        }
        else {
            res.append(ZS);
        }

        if (XS.length() != 0) {
            res.append(".");
            res.append(XS);
        }

        return res.toString();
    }

    //公有修改方法
    public void set_flag(boolean input) {
        this.flag = input;
    }

    public void set_ZS(String input) {
        this.ZS = new StringBuilder(input);
    }

    public void set_XS(String input) {
        this.XS = new StringBuilder(input);
    }

    public void set(String input) {
        StringBuilder str = new StringBuilder(prefix(input));

        if (str.charAt(0) == '+') {
            this.flag = true;
        }
        else {
            this.flag = false;
        }

        int index = str.indexOf(".");
        this.ZS = new StringBuilder(str.substring(1, index));
        this.XS = new StringBuilder(str.substring(index + 1));
    }

    public void copy(BigNum b) {
        this.flag = b.flag;
        this.ZS = new StringBuilder(b.ZS);
        this.XS = new StringBuilder(b.XS);
    }

    //重载比较算符
    public boolean is_bigger(BigNum b) {
        if (this.flag == b.flag) {
            if (this.flag == true) {
                if (this.ZS.length() > b.ZS.length()) {
                    return true;
                }
                else if (this.ZS.length() < b.ZS.length()) {
                    return false;
                }
                else {
                    for (int i = 0; i < this.ZS.length(); i++) {
                        if (this.ZS.charAt(i) > b.ZS.charAt(i)) {
                            return true;
                        }
                        else if (this.ZS.charAt(i) < b.ZS.charAt(i)) {
                            return false;
                        }
                    }

                    //比较小数
                    int len1 = this.XS.length();
                    int len2 = b.XS.length();
                    if (len1 > len2) {
                        for (int i = 0; i < len2; i++) {
                            if (this.XS.charAt(i) > b.XS.charAt(i)) {
                                return true;
                            }
                            else if (this.XS.charAt(i) < b.XS.charAt(i)) {
                                return false;
                            }
                        }
                        return true;
                    }
                    else {
                        for (int i = 0; i < len1; i++) {
                            if (this.XS.charAt(i) > b.XS.charAt(i)) {
                                return true;
                            }
                            else if (this.XS.charAt(i) < b.XS.charAt(i)) {
                                return false;
                            }
                        }
                        return false;
                    }
                }
            }
            else {
                if (this.ZS.length() > b.ZS.length()) {
                    return false;
                }
                else if (this.ZS.length() < b.ZS.length()) {
                    return true;
                }
                else {
                    for (int i = 0; i < this.ZS.length(); i++) {
                        if (this.ZS.charAt(i) > b.ZS.charAt(i)) {
                            return false;
                        }
                        else if (this.ZS.charAt(i) < b.ZS.charAt(i)) {
                            return true;
                        }
                    }

                    //比较小数
                    int len1 = this.XS.length();
                    int len2 = b.XS.length();
                    if (len1 > len2) {
                        for (int i = 0; i < len2; i++) {
                            if (this.XS.charAt(i) > b.XS.charAt(i)) {
                                return false;
                            }
                            else if (this.XS.charAt(i) < b.XS.charAt(i)) {
                                return true;
                            }
                        }
                        return false;
                    }
                    else {
                        for (int i = 0; i < len1; i++) {
                            if (this.XS.charAt(i) > b.XS.charAt(i)) {
                                return false;
                            }
                            else if (this.XS.charAt(i) < b.XS.charAt(i)) {
                                return true;
                            }
                        }
                        return true;
                    }
                }
            }
        }
        else {
            if (this.flag == true) {
                return true;
            }
            else {
                return false;
            }
        }
    }

    public boolean is_equal(BigNum b) {
        if (this.flag == b.flag && this.ZS == b.ZS && this.XS == b.XS) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean is_smaller(BigNum b) {
        if (this.is_bigger(b) || this.is_equal(b)) {
            return false;
        }
        else {
            return true;
        }
    }

    public boolean is_abs_bigger(BigNum b) {
        BigNum temp1 = new BigNum(this);
        BigNum temp2 = new BigNum(b);
        temp1.flag = true;
        temp2.flag = true;

        if (temp1.is_bigger(temp2)) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean is_abs_equal(BigNum b) {
        BigNum temp1 = new BigNum(this);
        BigNum temp2 = new BigNum(b);
        temp1.flag = true;
        temp2.flag = true;

        if (temp1.is_equal(temp2)) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean is_abs_smaller(BigNum b) {
        BigNum temp1 = new BigNum(this);
        BigNum temp2 = new BigNum(b);
        temp1.flag = true;
        temp2.flag = true;

        if (temp1.is_smaller(temp2)) {
            return true;
        }
        else {
            return false;
        }
    }

    //重载运算符
    public BigNum add(BigNum b) {
        BigNum res;

        if (this.flag == b.flag) {
            res = new BigNum();
            int len1 = this.XS.length();
            int len2 = b.XS.length();

            if (len1 >= len2) {
                //小数处理
                res.XS.append(this.get_XS());

                int flag_temp = 0;
                for (int i = len2 - 1; i >= 0; i--) {
                    int temp = (res.XS.charAt(i) - '0') + (b.XS.charAt(i) - '0') + flag_temp;
                    flag_temp = temp / 10;
                    temp = temp - flag_temp * 10;
                    res.XS.setCharAt(i, (char) (temp + '0'));
                }

                //整数处理
                len1 = this.ZS.length();
                len2 = b.ZS.length();
                if (len1 >= len2) {
                    res.ZS.append(this.get_ZS());
                    for (int i = 0; i < len2; i++) {
                        int temp = (res.ZS.charAt(len1 - i - 1) - '0') + (b.ZS.charAt(len2 - i - 1) - '0') + flag_temp;
                        flag_temp = 0;
                        while (temp >= 10) {
                            flag_temp += 1;
                            temp -= 10;
                        }
                        res.ZS.setCharAt(len1 - i - 1, (char) (temp + '0'));
                    }
                    for (int i = len2; i < len1; i++) {
                        int temp = (res.ZS.charAt(len1 - i - 1) - '0') + flag_temp;
                        flag_temp = 0;
                        while (temp >= 10) {
                            flag_temp += 1;
                            temp -= 10;
                        }
                        res.ZS.setCharAt(len1 - i - 1, (char) (temp + '0'));
                    }
                    if (flag_temp != 0) {
                        res.ZS.insert(0, (char) (flag_temp + '0'));
                    }
                }
                else {
                    res.ZS.append(b.get_ZS());
                    for (int i = 0; i < len1; i++) {
                        int temp = (res.ZS.charAt(len2 - i - 1) - '0') + (this.ZS.charAt(len1 - i - 1) - '0') + flag_temp;
                        flag_temp = 0;
                        while (temp >= 10) {
                            flag_temp += 1;
                            temp -= 10;
                        }
                        res.ZS.setCharAt(len2 - i - 1, (char) (temp + '0'));
                    }
                    for (int i = len1; i < len2; i++) {
                        int temp = (res.ZS.charAt(len2 - i - 1) - '0') + flag_temp;
                        flag_temp = 0;
                        while (temp >= 10) {
                            flag_temp += 1;
                            temp -= 10;
                        }
                        res.ZS.setCharAt(len2 - i - 1, (char) (temp + '0'));
                    }
                }
            }
            else {
                //小数处理
                res.XS.append(b.get_XS());

                int flag_temp = 0;
                for (int i = len1 - 1; i >= 0; i--) {
                    int temp = (this.XS.charAt(i) - '0') + (res.XS.charAt(i) - '0') + flag_temp;
                    flag_temp = temp / 10;
                    temp = temp - flag_temp * 10;
                    res.XS.setCharAt(i, (char) (temp + '0'));
                }

                //整数处理
                len1 = this.ZS.length();
                len2 = b.ZS.length();
                if (len1 >= len2) {
                    res.ZS.append(this.get_ZS());
                    for (int i = 0; i < len2; i++) {
                        int temp = (res.ZS.charAt(len1 - i - 1) - '0') + (b.ZS.charAt(len2 - i - 1) - '0') + flag_temp;
                        flag_temp = 0;
                        while (temp >= 10) {
                            flag_temp += 1;
                            temp -= 10;
                        }
                        res.ZS.setCharAt(len1 - i - 1, (char) (temp + '0'));
                    }
                    for (int i = len2; i < len1; i++) {
                        int temp = (res.ZS.charAt(len1 - i - 1) - '0') + flag_temp;
                        flag_temp = 0;
                        while (temp >= 10) {
                            flag_temp += 1;
                            temp -= 10;
                        }
                        res.ZS.setCharAt(len1 - i - 1, (char) (temp + '0'));
                    }
                    if (flag_temp != 0) {
                        res.ZS.insert(0, (char) (flag_temp + '0'));
                    }
                }
                else {
                    res.ZS.append(b.get_ZS());
                    for (int i = 0; i < len1; i++) {
                        int temp = (res.ZS.charAt(len2 - i - 1) - '0') + (this.ZS.charAt(len1 - i - 1) - '0') + flag_temp;
                        flag_temp = 0;
                        while (temp >= 10) {
                            flag_temp += 1;
                            temp -= 10;
                        }
                        res.ZS.setCharAt(len2 - i - 1, (char) (temp + '0'));
                    }
                    for (int i = len1; i < len2; i++) {
                        int temp = (res.ZS.charAt(len2 - i - 1) - '0') + flag_temp;
                        flag_temp = 0;
                        while (temp >= 10) {
                            flag_temp += 1;
                            temp -= 10;
                        }
                        res.ZS.setCharAt(len2 - i - 1, (char) (temp + '0'));
                    }
                }
            }
        }
        else {
            if (this.is_abs_equal(b)) {
                res = new BigNum();
            }
            else if (this.is_abs_bigger(b)) {
                res = new BigNum();
                res.flag = this.flag;

                int len1 = this.XS.length();
                int len2 = b.XS.length();

                if (len1 >= len2) {
                    //小数处理
                    res.XS.append(this.get_XS());

                    int flag_temp = 0;
                    for (int i = len2 - 1; i >= 0; i--) {
                        int temp = (res.XS.charAt(i) - '0') - (b.XS.charAt(i) - '0') + flag_temp;
                        flag_temp = 0;
                        while (temp < 0) {
                            flag_temp -= 1;
                            temp += 10;
                        }
                        res.XS.setCharAt(i, (char) (temp + '0'));
                    }

                    //整数处理
                    len1 = this.ZS.length();
                    len2 = b.ZS.length();
                    res.ZS.append(this.get_ZS());
                    for (int i = 0; i < len2; i++) {
                        int temp = (res.ZS.charAt(len1 - i - 1) - '0') - (b.ZS.charAt(len2 - i - 1) - '0') + flag_temp;
                        flag_temp = 0;
                        while (temp < 0) {
                            flag_temp -= 1;
                            temp += 10;
                        }
                        res.ZS.setCharAt(len1 - i - 1, (char) (temp + '0'));
                    }
                    for (int i = len2; i < len1; i++) {
                        int temp = (res.ZS.charAt(len1 - i - 1) - '0') + flag_temp;
                        flag_temp = 0;
                        while (temp < 0) {
                            flag_temp -= 1;
                            temp += 10;
                        }
                        res.ZS.setCharAt(len1 - i - 1, (char) (temp + '0'));
                    }
                }
                else {
                    //小数处理
                    res.XS.append(this.get_XS());
                    for (int i = len1; i < len2; i++) {
                        res.XS.append('0');
                    }

                    int flag_temp = 0;
                    for (int i = len2 - 1; i >= 0; i--) {
                        int temp = (res.XS.charAt(i) - '0') - (b.XS.charAt(i) - '0') + flag_temp;
                        res.XS.setCharAt(i, (char) (temp + '0'));
                    }

                    //整数处理
                    len1 = this.ZS.length();
                    len2 = b.ZS.length();
                    res.ZS.append(this.get_ZS());
                    for (int i = 0; i < len2; i++) {
                        int temp = (res.ZS.charAt(len1 - i - 1) - '0') - (b.ZS.charAt(len2 - i - 1) - '0') + flag_temp;
                        flag_temp = 0;
                        while (temp < 0) {
                            flag_temp -= 1;
                            temp += 10;
                        }
                        res.ZS.setCharAt(len1 - i - 1, (char) (temp + '0'));
                    }
                    for (int i = len2; i < len1; i++) {
                        int temp = (res.ZS.charAt(len1 - i - 1) - '0') + flag_temp;
                        flag_temp = 0;
                        while (temp < 0) {
                            flag_temp -= 1;
                            temp += 10;
                        }
                        res.ZS.setCharAt(len1 - i - 1, (char) (temp + '0'));
                    }
                }
            }
            else {
                res = new BigNum();
                res.flag = b.flag;

                int len1 = this.XS.length();
                int len2 = b.XS.length();

                if (len1 >= len2) {
                    //小数处理
                    res.XS.append(b.get_XS());
                    for (int i = len2; i < len1; i++) {
                        res.XS.append('0');
                    }

                    int flag_temp = 0;
                    for (int i = len1 - 1; i >= 0; i--) {
                        int temp = (res.XS.charAt(i) - '0') - (this.XS.charAt(i) - '0') + flag_temp;
                        flag_temp = 0;
                        while (temp < 0) {
                            flag_temp -= 1;
                            temp += 10;
                        }
                        res.XS.setCharAt(i, (char) (temp + '0'));
                    }

                    //整数处理
                    len1 = b.ZS.length();
                    len2 = this.ZS.length();
                    res.ZS.append(b.get_ZS());
                    for (int i = 0; i < len2; i++) {
                        int temp = (res.ZS.charAt(len1 - i - 1) - '0') - (this.ZS.charAt(len2 - i - 1) - '0') + flag_temp;
                        flag_temp = 0;
                        while (temp < 0) {
                            flag_temp -= 1;
                            temp += 10;
                        }
                        res.ZS.setCharAt(len1 - i - 1, (char) (temp + '0'));
                    }
                    for (int i = len2; i < len1; i++) {
                        int temp = (res.ZS.charAt(len1 - i - 1) - '0') + flag_temp;
                        flag_temp = 0;
                        while (temp < 0) {
                            flag_temp -= 1;
                            temp += 10;
                        }
                        res.ZS.setCharAt(len1 - i - 1, (char) (temp + '0'));
                    }
                }
                else {
                    //小数处理
                    res.XS.append(b.get_XS());

                    int flag_temp = 0;
                    for (int i = len1 - 1; i >= 0; i--) {
                        int temp = (res.XS.charAt(i) - '0') - (this.XS.charAt(i) - '0') + flag_temp;
                        flag_temp = 0;
                        while (temp < 0) {
                            flag_temp -= 1;
                            temp += 10;
                        }
                        res.XS.setCharAt(i, (char) (temp + '0'));
                    }

                    //整数处理
                    len1 = b.ZS.length();
                    len2 = this.ZS.length();
                    res.ZS.append(b.get_ZS());
                    for (int i = 0; i < len2; i++) {
                        int temp = (res.ZS.charAt(len1 - i - 1) - '0') - (this.ZS.charAt(len2 - i - 1) - '0') + flag_temp;
                        flag_temp = 0;
                        while (temp < 0) {
                            flag_temp -= 1;
                            temp += 10;
                        }
                        res.ZS.setCharAt(len1 - i - 1, (char) (temp + '0'));
                    }
                    for (int i = len2; i < len1; i++) {
                        int temp = (res.ZS.charAt(len1 - i - 1) - '0') + flag_temp;
                        flag_temp = 0;
                        while (temp < 0) {
                            flag_temp -= 1;
                            temp += 10;
                        }
                        res.ZS.setCharAt(len1 - i - 1, (char) (temp + '0'));
                    }
                }
            }
        }

        //后期处理
        while (res.ZS.length() > 0 && res.ZS.charAt(0) == '0') {
            res.ZS.deleteCharAt(0);
        }
        while (res.XS.length() > 0 && res.XS.charAt(res.XS.length() - 1) == '0') {
            res.XS.deleteCharAt(res.XS.length() - 1);
        }

        return res;
    }

    public BigNum sub(BigNum b) {
        BigNum temp1 = new BigNum(this);
        BigNum temp2 = new BigNum(b);
        temp2.flag = !temp2.flag;
        return temp1.add(temp2);
    }

    public BigNum mul(BigNum b) {
        BigNum res;

        int index = this.XS.length() + b.XS.length();
        StringBuilder num0 = new StringBuilder();
        StringBuilder num1 = new StringBuilder(this.get_ZS() + this.get_XS());
        StringBuilder num2 = new StringBuilder(b.get_ZS() + b.get_XS());

        for (int i = 0; i < this.ZS.length() + b.ZS.length() + index; i++) {
            num0.append('0');
        }

        int len0 = num0.length();
        int len1 = num1.length();
        int len2 = num2.length();

        for (int i = 0; i < len1; i++) {
            for (int j = 0; j < len2; j++) {
                int temp = (num1.charAt(len1 - i - 1) - '0') * (num2.charAt(len2 - j - 1) - '0') + (num0.charAt(len0 - i - j - 1) - '0');
                num0.setCharAt(len0 - i - j - 1, (char) (temp + '0'));
            }
        }

        int flag_temp = 0;
        for (int i = num0.length() - 1; i >= 0; i--) {
            int temp = num0.charAt(i) - '0' + flag_temp;
            flag_temp = 0;
            while (temp >= 10) {
                flag_temp += 1;
                temp -= 10;
            }
            num0.setCharAt(i, (char) (temp + '0'));
        }

        if (flag_temp != 0) {
            num0.insert(0, (char) (flag_temp + '0'));
        }

        num0.insert(num0.length() - index, '.');

        if (this.flag == b.flag) {
            num0.insert(0, "+");
        }
        else {
            num0.insert(0, "-");
        }
        res = new BigNum(num0.toString());

        //后期处理
        while (res.ZS.length() > 0 && res.ZS.charAt(0) == '0') {
            res.ZS.deleteCharAt(0);
        }
        while (res.XS.length() > 0 && res.XS.charAt(res.XS.length() - 1) == '0') {
            res.XS.deleteCharAt(res.XS.length() - 1);
        }

        return res;
    }

    public BigNum div(BigNum b){
        return this;
    }


}


