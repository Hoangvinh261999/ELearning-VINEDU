package vinhit.authservice.security;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import vinhit.authservice.service.JwtService;
import vinhit.authservice.service.ServiceImplement.UserDetailServiceImp;
import java.io.IOException;

@Component
public class CustomJwtFilter extends OncePerRequestFilter {
    @Autowired
    JwtService jwtService;
    @Autowired
    UserDetailServiceImp userDetailServiceImp;
    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String HEADER_STRING = "Authorization";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().equals("/auth/token")) {
            System.out.println(request.getRequestURI());
            filterChain.doFilter(request, response);
            return;
        }
        String header = request.getHeader(HEADER_STRING);
        String username="";


        if(header!=null  && header.startsWith(HEADER_STRING)){
            try {
                String token =header.replace(TOKEN_PREFIX,"");
                username=jwtService.extractUsername(token);
            } catch (SignatureException e) {
                request.setAttribute("JWT_ERROR", "Invalid JWT signature");
            } catch (ExpiredJwtException e) {
                request.setAttribute("JWT_ERROR", "Token has expired");
            } catch (JwtException e) {
                request.setAttribute("JWT_ERROR", "Invalid JWT token");
            }
        }else{
            request.setAttribute("JWT_ERROR", "Token is missing");

        }
        if(StringUtils.hasText(username)){
            UserDetails userDetails= userDetailServiceImp.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authentication= new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request,response);
    }
}
