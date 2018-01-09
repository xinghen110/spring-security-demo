package com.hexin.demo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringSecurityDemoApplicationTests {

	@Autowired
	private SecurityService service;

	@Before
	public void init() {
		this.authentication = new UsernamePasswordAuthenticationToken("user", "password",
				AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER1"));
	}
	@After
	public void close() {
		SecurityContextHolder.clearContext();
	}

	@Test(expected = AuthenticationException.class)
	public void secure() {
		assertThat("Hello Security").isEqualTo(this.service.secure());
	}

	@Test
	public void authenticated() {
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("user", "password",
				AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER")));
		assertThat("Hello Security").isEqualTo(this.service.secure());
	}

	@Test
	public void preauth() {
		SecurityContextHolder.getContext().setAuthentication(this.authentication);
		assertThat("Hello World").isEqualTo(this.service.authorized());
	}

	@Test(expected = AccessDeniedException.class)
	public void denied() {
		SecurityContextHolder.getContext().setAuthentication(this.authentication);
		assertThat("Goodbye World").isEqualTo(this.service.denied());
	}

	private Authentication authentication;

}
