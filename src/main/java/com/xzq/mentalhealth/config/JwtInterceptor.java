package com.xzq.mentalhealth.config;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.xzq.mentalhealth.common.ErrorCode;
import com.xzq.mentalhealth.exception.BusinessException;
import com.xzq.mentalhealth.model.entity.User;
import com.xzq.mentalhealth.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = request.getHeader("token");
        //执行认证
        if (StrUtil.isBlank(token)){
            token  = request.getParameter("token");
        }
        //执行认证
        if (StrUtil.isBlank(token)){
            throw new BusinessException(ErrorCode.NOT_LOGIN,"无token，请重新登录");
        }
        String userId;
        User user;
        try {
            //通过之前创建token取出userId
            userId = JWT.decode(token).getAudience().get(0);
            log.info("{}",userId);
            //通过userId查询用户数据
            user = userService.getById(Integer.parseInt(userId));

        }catch (Exception e){
            String errMg = "token验证失败，请重新登录";
            log.error(errMg+",token="+token,e);
            throw new BusinessException(ErrorCode.NOT_LOGIN,errMg);
        }
        if (user == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户不存在");
        }
        try{
            //用户名验证token
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getUserPassword())).build();
            jwtVerifier.verify(token);//验证token
        }catch (JWTVerificationException E){
            throw new BusinessException(ErrorCode.NOT_LOGIN,"token验证失败，请重新登录");
        }
        return true;
    }
}
