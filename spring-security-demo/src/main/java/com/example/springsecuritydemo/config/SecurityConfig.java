//package com.example.springsecuritydemo.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import java.io.PrintWriter;
//
///**
// * 安全配置
// *
// * @author 周林
// * @version 1.0
// * @date 2021/5/7 16:01
// */
//@Configuration
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
////    @Autowired
////    VerifyCodeFilter verifyCodeFilter;
//
//
//    /**
//     * 忽略拦截
//     * @param web
//     * @throws Exception
//     */
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/vercode");
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.addFilterBefore(null, UsernamePasswordAuthenticationFilter.class);
//        http.authorizeRequests()                //开启登录配置
//            .antMatchers("/hello").hasRole("admin") //表示访问 /hello 这个接口，需要具备 admin 这个角色
//            .anyRequest().authenticated()   //表示剩余的其他接口，登录之后就能访问
//            .and()
//            .formLogin()
//            .loginPage("/login_p")          //定义登录页面，未登录时，访问一个需要登录之后才能访问的接口，会自动跳转到该页面
//            //登录处理接口
//            .loginProcessingUrl("/doLogin")
//            .usernameParameter("admin")     //定义登录时，用户名的 key，默认为 username
//            .passwordParameter("123456")    //定义登录时，用户密码的 key，默认为 password
//            .successHandler((req, resp, authentication) -> {    //登录成功的处理器
//                resp.setContentType("application/json;charset=utf-8");
//                PrintWriter out = resp.getWriter();
//                out.write("success");
//                out.flush();
//            })
//            .failureHandler((req, resp, exception) -> {     //登录失败的处理器
//                resp.setContentType("application/json;charset=utf-8");
//                PrintWriter out = resp.getWriter();
//                out.write("fail");
//                out.flush();
//            })
//            .permitAll() //和表单登录相关的接口统统都直接通过
//            .and()
//            .logout()
//            .logoutUrl("/logout")
//            .logoutSuccessHandler((req, resp, authentication) -> {
//                resp.setContentType("application/json;charset=utf-8");
//                PrintWriter out = resp.getWriter();
//                out.write("logout success");
//                out.flush();
//            })
//            .permitAll()
//            .and()
//            .httpBasic()
//            .and()
//            .csrf().disable();
//    }
//}
