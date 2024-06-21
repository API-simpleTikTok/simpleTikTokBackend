package com.simpletiktok.simpletiktok.config.filter;

import com.simpletiktok.simpletiktok.config.SecurityUserDetails;
import com.simpletiktok.simpletiktok.data.service.SecurityUserDetailsService;
import com.simpletiktok.simpletiktok.utils.JwtUtils;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class MyAuthenticationFilter extends OncePerRequestFilter {

    @Resource
    private SecurityUserDetailsService securityUserDetailsService;

    // 示例白名单路径
    private static final String[] WHITELIST = {
            "/session",
            "/user/sign",
            "/video/recommended",
            "/public"
    };

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();

        // 检查请求路径是否在白名单中
        for (String allowedPath : WHITELIST) {
            if (path.startsWith(allowedPath)) {
                filterChain.doFilter(request, response);
                return;
            }
        }

        String requestToken = request.getHeader(JwtUtils.getCurrentConfig().getHeader());

        // 检查token是否存在
        if (StringUtils.isBlank(requestToken)) {
            // 如果没有Token，设置HTTP状态码为401 Unauthorized并返回错误信息
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Token is empty\"}");
            response.getWriter().flush();
            return; // 直接返回，不调用filterChain.doFilter，阻止请求继续处理
        }

        // 如果token存在，检查其有效性
        boolean verifyToken = JwtUtils.isValidToken(requestToken);
        if (!verifyToken) {
            // 如果Token无效，也返回相应的错误信息
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Token is invalid\"}");
            response.getWriter().flush();
            return; // 直接返回，阻止请求继续处理
        }

        // 解析token中的用户信息
        String subject = JwtUtils.getSubject(requestToken);
        if (StringUtils.isNotBlank(subject) && SecurityContextHolder.getContext().getAuthentication() == null) {
            SecurityUserDetails userDetails = (SecurityUserDetails) securityUserDetailsService.loadUserByUsername(subject);
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities());

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // 继续请求的其他处理
        filterChain.doFilter(request, response);
    }
}
