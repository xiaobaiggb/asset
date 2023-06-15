//package com.javaee.lqsx.asset.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        //【注意事项】放行资源要放在前面，认证的放在后面
//        http.authorizeRequests()
////                .mvcMatchers("/index").permitAll() //代表放行index的所有请求
//                .mvcMatchers("/login").permitAll() //放行loginHtml请求
//                .anyRequest().authenticated()//代表其他请求需要认证
//                .and()
//                .formLogin()//表示其他需要认证的请求通过表单认证
//                //loginPage 一旦你自定义了这个登录页面，那你必须要明确告诉SpringSecurity日后哪个url处理你的登录请求
//                .loginPage("/login")//用来指定自定义登录界面，不使用SpringSecurity默认登录界面  注意：一旦自定义登录页面，必须指定登录url
//                //loginProcessingUrl  这个doLogin请求本身是没有的，因为我们只需要明确告诉SpringSecurity，日后只要前端发起的是一个doLogin这样的请求，
//                //那SpringSecurity应该把你username和password给捕获到
//                .loginProcessingUrl("/login")//指定处理登录的请求url
//                .usernameParameter("no") //指定登录界面用户名文本框的name值，如果没有指定，默认属性名必须为username
//                .passwordParameter("password")//指定登录界面密码密码框的name值，如果没有指定，默认属性名必须为password
//                .and()
//                .csrf().disable(); //禁止csrf 跨站请求保护
//
//    }
//}
