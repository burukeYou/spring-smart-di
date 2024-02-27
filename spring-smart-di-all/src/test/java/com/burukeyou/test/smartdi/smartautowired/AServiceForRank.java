package com.burukeyou.test.smartdi.smartautowired;

import org.springframework.stereotype.Component;

@Component
public class AServiceForRank extends AService {

    public void sayHello(){
        System.out.println("sb");
    }
}
