package com.example.gateway;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.header.XFrameOptionsServerHttpHeadersWriter.Mode;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@EnableWebFluxSecurity
public class SecurityConfig {
	@Bean
	public SecurityWebFilterChain securitygWebFilterChain(ServerHttpSecurity http) {
	    return http
	    		.authorizeExchange()
	    		.anyExchange().authenticated()
	    		.and().formLogin()
	    		.and().csrf().disable() // TODO: nem szabad kikapcsolni!
	    		.headers().frameOptions().mode(Mode.SAMEORIGIN).and()
	    		.build();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}

@Component
class MyReactiveUserDetailsService implements ReactiveUserDetailsService {

	@Autowired
	DummyUserRepository userrepo;

	@Override
	public Mono<UserDetails> findByUsername(String username) {
		return userrepo.findByUsername(username).cast(UserDetails.class);
	}
}

@Component
class DummyUserRepository {

	@Autowired
	BCryptPasswordEncoder encoder;
	Map<String, UserAccount> accounts = new HashMap<>();

	@PostConstruct
	private void init() {
		accounts.put("admin", new UserAccount("admin", encoder.encode("admin"), new SimpleGrantedAuthority("ADMIN")));
		accounts.put("user", new UserAccount("user", encoder.encode("user"), new SimpleGrantedAuthority("USER")));
		accounts.put("harry", new UserAccount("harry", encoder.encode("12345"), new SimpleGrantedAuthority("ADMIN")));
	}

	public Mono<UserAccount> findByUsername(String username) {
		return Mono.justOrEmpty(accounts.get(username));
	}
}

class UserAccount implements UserDetails {

	private String username;
    private String password;
    private boolean active = true;
    private List<SimpleGrantedAuthority> roles = new ArrayList<>();

	public UserAccount(String username, String password, SimpleGrantedAuthority role) {
		super();
		this.username = username;
		this.password = password;
		this.roles.add(role);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles;
	}
	@Override
	public String getPassword() {
		return password;
	}
	@Override
	public String getUsername() {
		return username;
	}
	@Override
	public boolean isAccountNonExpired() {
		return active;
	}
	@Override
	public boolean isAccountNonLocked() {
		return active;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return active;
	}
	@Override
	public boolean isEnabled() {
		return active;
	}
}