/*

    a static class to store the applicationContext passed from the main method ,
     once the application starts so that we can be able to get beans from anywhere in our code

 */

package com.bridgingcode.springbootactivemqdemo;

import org.springframework.context.ApplicationContext;

public class Context {
    public static ApplicationContext applicationContext;

    Context(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public static void setApplicationContext(ApplicationContext applicationContext) {
        Context.applicationContext = applicationContext;
    }

    public ApplicationContext getApplicationContext(){
        return applicationContext;
    }
}
