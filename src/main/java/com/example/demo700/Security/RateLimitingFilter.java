package com.example.demo700.Security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo700.Services.SecurityService.BucketService;

import io.github.bucket4j.Bucket;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RateLimitingFilter extends OncePerRequestFilter {

	@Autowired
	private BucketService bucketService;
	
	private final AntPathMatcher pathMatcher = new AntPathMatcher();

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		Bucket globalBucket = bucketService.getGlobalBucket();

		if (globalBucket.tryConsume(1)) {

			String clientIp = extractClientToken(request);

			Bucket bucket = bucketService.resolvePersonalUses(clientIp);

			if (bucket.tryConsume(1)) {

				filterChain.doFilter(request, response);

			} else {

				response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
				response.setContentType("application/json");
				response.getWriter().write("Too many request among the fixed time, please try after some time");

			}

		} else {

			response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
			response.setContentType("application/json");
			response.getWriter().write("Too many request among the fixed time, please try after some time");

		}

	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		
		String path = request.getRequestURI();
		
		return pathMatcher.match("/api/user-active/heartbeat/**", path);
	}

	private String extractClientToken(HttpServletRequest http) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {

			System.out.println("collecting user's personal token from users given bearer token");

			return auth.getName();

		}

		String fetchIpAdress = http.getHeader("X-Forwarded-For");

		if (fetchIpAdress != null && !fetchIpAdress.isEmpty()) {

			System.out.println("collecting user's personal ip :- " + fetchIpAdress);

			return fetchIpAdress;

		}

		System.out.println("Fetching user's uses network ip :- " + http.getRemoteAddr());

		return http.getRemoteAddr();

	}

}
