/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itstep.learning.services.kdf;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import itstep.learning.services.hash.HashService;

@Singleton
public class PbKdf1Service implements KdfService{

    private final int iterationCount=3;
    private final int dkLength=20;//length of key
    private final HashService hashservice;
    
    @Inject
    public PbKdf1Service (HashService hashservice){
    this.hashservice=hashservice;
    }
    
    @Override
    public String dk(String password, String salt) {
        String res=password + salt;
        for (int i=0; i<iterationCount; ++i){
        res = hashservice.digest(res);
        }
        return res.substring(0, dkLength);
    }
    
}
