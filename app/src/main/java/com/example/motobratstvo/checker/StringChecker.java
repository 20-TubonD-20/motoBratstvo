package com.example.motobratstvo.checker;

public class StringChecker {

    public int checkPassword (String password_) {
        if(password_.length()  < 6 ) return 1; //size must be >= 8
        return 0;
    }

    public int checkEmail (String email_) {
        int count = 0;

        for (int i = 0; i < email_.length(); i++) {
            if(email_.charAt(i) == '@') count++;
        }
        if(count != 1) return 1; //must be 1 @

        return 0;
    }

}
