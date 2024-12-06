package zeroone.developers.courseservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableJpaRepositories(repositoryBaseClass = BaseRepositoryImpl::class)
class CourseServiceApplication

fun main(args: Array<String>) {
	runApplication<CourseServiceApplication>(*args)
}
