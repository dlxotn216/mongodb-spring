package io.taesu.springdatamongodb.app.config.jackson

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.deser.std.StringDeserializer
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import org.bson.types.ObjectId
import java.io.IOException

/**
 * Created by itaesu on 2024/03/01.
 *
 * @author Lee Tae Su
 * @version spring-data-mongodb
 * @since spring-data-mongodb
 */
class ObjectIdToStringModule: SimpleModule() {
    init {
        this.addDeserializer(ObjectId::class.java, ObjectIdToStringDeserializer())
        this.addSerializer(ObjectId::class.java, ObjectIdToStringSerializer())
    }
}

class ObjectIdToStringDeserializer: StdDeserializer<ObjectId>(ObjectId::class.java) {
    @Throws(IOException::class)
    override fun deserialize(parser: JsonParser, context: DeserializationContext): ObjectId? {
        val value = StringDeserializer.instance.deserialize(parser, context)
        return ObjectId(value)
    }

    fun nullIfBlank(value: String?) = value?.trim()?.ifBlank { null }
}

class ObjectIdToStringSerializer: StdSerializer<ObjectId>(ObjectId::class.java) {
    @Throws(IOException::class)
    override fun serialize(value: ObjectId, gen: JsonGenerator, provider: SerializerProvider) {
        gen.writeString(value.toString())
    }
}
