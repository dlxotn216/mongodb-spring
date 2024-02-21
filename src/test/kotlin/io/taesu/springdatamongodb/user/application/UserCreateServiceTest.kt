package io.taesu.springdatamongodb.user.application

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

/**
 * Created by itaesu on 2024/02/21.
 *
 * @author Lee Tae Su
 * @version spring-data-mongodb
 * @since spring-data-mongodb
 */
@SpringBootTest
class UserCreateServiceTest {
    @Autowired
    private lateinit var userCreateService: UserCreateService

    @Test
    fun prepare() {
        userCreateService.prepareLarge()
    }
}
