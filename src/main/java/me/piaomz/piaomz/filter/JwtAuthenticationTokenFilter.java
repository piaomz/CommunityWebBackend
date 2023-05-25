package me.piaomz.piaomz.filter;

import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import me.piaomz.piaomz.pojo.User;
import me.piaomz.piaomz.service.LoginUserDetails;
import me.piaomz.piaomz.utils.JwtUtil;
import me.piaomz.piaomz.utils.RedisCache;
import me.piaomz.piaomz.utils.ResponseResult;
import me.piaomz.piaomz.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //get token
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)) {
            filterChain.doFilter(request, response);
            return;
        }
        String redisKey = null;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            String userid = claims.getSubject();
            redisKey = "login:" + userid;

        } catch (Exception e) {
            ResponseResult responseResult = new ResponseResult(HttpStatus.FORBIDDEN.value(),"token not parseable");
            String json = JSON.toJSONString(responseResult);
            WebUtils.renderString(response,json);
            //throw new RuntimeException("token not parseable");
            return ;
        }
        //redis get all user detail
        LoginUserDetails loginUserDetails = redisCache.getCacheObject(redisKey);
        //System.out.println("111");
        if (Objects.isNull(loginUserDetails)) {
            ResponseResult responseResult = new ResponseResult(HttpStatus.FORBIDDEN.value(),"No cache of this token loginUser");
            String json = JSON.toJSONString(responseResult);
            WebUtils.renderString(response,json);
            //throw new RuntimeException("No cache of this token loginUser");
            return ;
        }

        //store in securityContextHolder
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUserDetails, null, loginUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //fangxing
        filterChain.doFilter(request, response);


        //filterChain.doFilter(request,response);
        return ;
    }
}
