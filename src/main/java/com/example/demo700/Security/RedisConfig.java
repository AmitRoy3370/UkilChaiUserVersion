package com.example.demo700.Security;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.bson.BsonDocument;
import org.bson.BsonObjectId;
import org.bson.BsonString;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.io.IOException;
import java.util.Date;

@Configuration
public class RedisConfig {

    @Bean
    public RedisSerializer<Object> springSessionDefaultRedisSerializer() {
        ObjectMapper objectMapper = new ObjectMapper();

        // Register custom module for GridFSFile
        SimpleModule module = new SimpleModule();
        module.addSerializer(GridFSFile.class, new GridFSFileSerializer());
        module.addDeserializer(GridFSFile.class, new GridFSFileDeserializer());
        objectMapper.registerModule(module);

        return new GenericJackson2JsonRedisSerializer(objectMapper);
    }

    // Custom Serializer
    public static class GridFSFileSerializer extends JsonSerializer<GridFSFile> {
        @Override
        public void serialize(GridFSFile value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeStartObject();
            gen.writeStringField("id", value.getObjectId().toHexString());
            gen.writeStringField("filename", value.getFilename());
            gen.writeNumberField("length", value.getLength());
            gen.writeNumberField("chunkSize", value.getChunkSize());
            gen.writeNumberField("uploadDate", value.getUploadDate().getTime());

            if (value.getMetadata() != null) {
                gen.writeObjectField("metadata", value.getMetadata());
            }
            gen.writeEndObject();
        }
    }

    public static class GridFSFileDeserializer extends JsonDeserializer<GridFSFile> {
        @Override
        public GridFSFile deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            JsonNode node = p.getCodec().readTree(p);

            String idStr = node.get("id").asText();
            String filename = node.get("filename").asText();
            long length = node.get("length").asLong();
            int chunkSize = node.get("chunkSize").asInt();
            long uploadDateMs = node.get("uploadDate").asLong();

            // 1. Read metadata as org.bson.Document
            org.bson.Document metadata = null;
            if (node.has("metadata") && !node.get("metadata").isNull()) {
                metadata = ctxt.readTreeAsValue(node.get("metadata"), org.bson.Document.class);
            }

            // 2. Pass 6 parameters directly (No md5)
            return new GridFSFile(
                    new BsonObjectId(new ObjectId(idStr)),
                    filename,
                    length,
                    chunkSize,
                    new Date(uploadDateMs),
                    metadata
            );
        }
    }
}
