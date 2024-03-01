package io.taesu.springdatamongodb.mock

import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import io.taesu.springdatamongodb.post.domain.Post

/**
 * Created by itaesu on 2024/02/29.
 *
 * @author Lee Tae Su
 * @version spring-data-mongodb
 * @since spring-data-mongodb
 */
val fixtureMonkey: FixtureMonkey = FixtureMonkey.builder()
    .plugin(KotlinPlugin())
    .objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
    .build()

inline fun <reified T> giveMeOne(): T {
    while (true) {
        try {
            return fixtureMonkey.giveMeOne(T::class.java)
        } catch (e: Exception) {
            // ignore
            // 와이라노..?
        }
    }
}
