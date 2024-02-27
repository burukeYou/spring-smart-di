package com.burukeyou.test.smartdi.smartautowired;

import com.burukeyou.smartdi.annotations.BeanAliasName;
import org.springframework.stereotype.Component;

@Component
@BeanAliasName("a-cooking")
public class AserviceForCook extends AServiceForRank {

    @Override
    public void sayHello() {
        System.out.println("fasdfasfd");
    }
}
