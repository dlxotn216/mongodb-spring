package io.taesu.springdatamongodb.student.application

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
class StudentCreateServiceTest {
    @Autowired
    private lateinit var studentCreateService: StudentCreateService

    @Test
    fun prepare() {
        studentCreateService.prepareLarge()
    }
}
