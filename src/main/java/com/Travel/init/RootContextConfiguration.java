package com.Travel.init;

import org.springframework.context.annotation.*;

@Import({ContextDatabase.class, ContextMail.class})
@ComponentScan(basePackages = {"com.Travel.biz.User.*"})
public class RootContextConfiguration {

}
