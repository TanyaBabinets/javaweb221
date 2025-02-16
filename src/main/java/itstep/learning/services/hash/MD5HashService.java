/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itstep.learning.services.hash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author User
 */
public class MD5HashService implements HashService {

    @Override
    public String digest(String input) {
   try{
        char[] chars=new char[32];
        int i=0;
        for( byte b:
        MessageDigest
.getInstance("MD5")
                .digest(input.getBytes())){
        int bi=b&0xFF;
            String str=Integer.toHexString(bi);
            if (bi <16){
             chars[i]='0';
         chars[i+1]=str.charAt(0);
            }
            else{
               chars[i]=str.charAt(0);
         chars[i+1]=str.charAt(1);
            }
       
         i+=2;
        }
        return new String(chars);     
        }
        catch(NoSuchAlgorithmException ex){
        throw new RuntimeException ( ex );
        }
    }
}
