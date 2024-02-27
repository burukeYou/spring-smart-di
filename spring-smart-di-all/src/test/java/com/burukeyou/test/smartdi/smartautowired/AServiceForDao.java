package com.burukeyou.test.smartdi.smartautowired;

import com.burukeyou.smartdi.annotations.BeanAliasName;
import org.springframework.stereotype.Component;

@Component
@BeanAliasName("a-dao")
public class AServiceForDao extends AbstractService {
}
