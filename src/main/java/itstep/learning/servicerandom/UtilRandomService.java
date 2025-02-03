/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itstep.learning.servicerandom;

import com.google.inject.Singleton;
import java.util.Random;

/**
 *
 * @author User
 */

@Singleton
public class UtilRandomService implements RandomService {
private Random random = new Random();
    @Override
    public int randomInt() {
        return random.nextInt();
    }
    
}
