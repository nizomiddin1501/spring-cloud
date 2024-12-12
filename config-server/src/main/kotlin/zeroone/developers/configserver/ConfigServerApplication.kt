package zeroone.developers.configserver
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.config.server.EnableConfigServer
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint

@SpringBootApplication
@EnableConfigServer
class ConfigServerApplication
fun main(args: Array<String>) {
	runApplication<ConfigServerApplication>(*args)
}

@Bean
fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
	http
		.authorizeHttpRequests { authz ->
			authz
				.requestMatchers("/internal/**").permitAll()
				.anyRequest().authenticated()
		}
		.httpBasic { basic ->
			basic.authenticationEntryPoint(BasicAuthenticationEntryPoint())
		}
	return http.build()
}
