package com.bookmyshow.main.Security;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.bookmyshow.main.security.CustomUserDetailsService;
import com.bookmyshow.main.security.JwtService;
import com.bookmyshow.main.security.SecurityConfig;
import com.bookmyshow.main.service.TokenService;

@ExtendWith(MockitoExtension.class)
class SecurityConfigTest {

    @Mock
    private CustomUserDetailsService userDetailsService;

    @Mock
    private JwtService jwtService;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private SecurityConfig securityConfig;

    @Test
    void testPasswordEncoderIsBCrypt() {
        PasswordEncoder encoder = securityConfig.passwordEncoder();
        assertThat(encoder).isInstanceOf(BCryptPasswordEncoder.class);
    }

    @Test
    void testCorsConfigurationSource() {
        CorsConfigurationSource source = securityConfig.corsConfigurationSource();
        CorsConfiguration corsConfig = ((org.springframework.web.cors.UrlBasedCorsConfigurationSource) source)
                .getCorsConfigurations()
                .get("/**");

        assertThat(corsConfig.getAllowedOriginPatterns()).contains("*");
        assertThat(corsConfig.getAllowedMethods()).containsExactlyInAnyOrder("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS");
        assertThat(corsConfig.getAllowedHeaders()).containsExactlyInAnyOrder("Authorization", "Content-Type", "Accept");
        assertThat(corsConfig.getAllowCredentials()).isTrue();
    }

    @Test
    void testAuthenticationManagerReturnedFromConfig() throws Exception {
        AuthenticationConfiguration authConfig = org.mockito.Mockito.mock(AuthenticationConfiguration.class);
        AuthenticationManager expectedManager = org.mockito.Mockito.mock(AuthenticationManager.class);

        org.mockito.Mockito.when(authConfig.getAuthenticationManager()).thenReturn(expectedManager);

        AuthenticationManager actualManager = securityConfig.authenticationManager(authConfig);
        assertThat(actualManager).isSameAs(expectedManager);
    }

    @Test
    void testJwtAuthenticationFilterBeanNotNull() {
        assertThat(securityConfig.jwtAuthenticationFilter()).isNotNull();
    }


}
