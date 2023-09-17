package com.example.foodies.Login;
public class ValidEmail {

    //email = ayush123@gmail.com
    static boolean isValidEmail(String email){
        if(email==null || email.isEmpty()){
            return false;
        }

        if(!email.contains("@") || !email.contains(".")){
            return false;
        }

        String[] parts = email.split("@");
        //["ayush123", "gmail.com"]. ====> length=2

        //Email should have exactly one @ symbol
        if(parts.length != 2){
            return false;
        }

        if(parts[0].isEmpty()){
            return false;
        }

        String[] domainParts = parts[1].split("\\.");
        //["gmail", "com"].

        //at least one dot after @
        if(domainParts.length < 2){
            return false;
        }

        //no two consecutive dot
        for(String s:domainParts){
            if(s.isEmpty()){
                return false;
            }
        }

        return true;
    }
}
