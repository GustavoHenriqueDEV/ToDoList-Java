package br.com.gustavodev.TodoList.filter;
import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.gustavodev.TodoList.user.InterfaceUserRepo;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {
/**/

    @Autowired
    private InterfaceUserRepo userRepo;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

            var serveletPath = request.getServletPath();

            if (serveletPath.startsWith("/tasks/")){
            var authorization = request.getHeader("Authorization");
            var authEncoded = authorization.substring("Basic".length()).trim();


            byte[] authEncode = Base64.getDecoder().decode(authEncoded);

            var authString  = new String(authEncode);

            String[] credentials = authString.split(":");
            String username = credentials[0];
            String passworld = credentials[1];


            var user = this.userRepo.findByUserName(username);
            if (user == null){
                response.sendError(401, "Usuario sem autorização");
            }else{
                var passwordVerify = BCrypt.verifyer().verify(passworld.toCharArray(), user.getPassword());
                if (passwordVerify.verified){
                    request.setAttribute("idUser", user.getId());
                    filterChain.doFilter(request, response);
                }else{
                    response.sendError(401, "Usuario sem autorização");
                }
            }
            System.out.println("AUTO");
            System.out.println(username);
            System.out.println(passworld);
        }else{
                filterChain.doFilter(request, response);

            }
    }
}

