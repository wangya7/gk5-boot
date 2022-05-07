package wang.bannong.gk5.boot.starter.web.util;

import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import wang.bannong.gk5.boot.starter.web.gateway.model.GatewayResponse;
import wang.bannong.gk5.boot.starter.web.security.model.Subject;

/**
 * JSON 操作
 */
@Slf4j
public final class JsonUtils {

    static ObjectMapper mapper = new ObjectMapper();

    /**
     * Java Bean 转 json content
     *
     * @param object Java Bean
     * @return JSON String
     */
    public static String toString(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("catch JsonProcessingException, object:[{}], exception detail:", object, e);
        } catch (Exception e) {
            log.error("catch Exception, object:[{}], exception detail:", object, e);
        }
        return Constant.BLANK;
    }

    /**
     * json content 转 Java Bean
     *
     * @param content json content
     * @param clazz   Java Bean` class
     * @param <T>     Instance
     * @return Java Bean
     */
    public static <T> T toObject(String content, Class<T> clazz) {
        try {
            return mapper.readValue(content, clazz);
        } catch (JsonMappingException e) {
            log.error("catch JsonMappingException, content:[{}], clazz:[{}], " +
                    "exception detail:", content, clazz.getSimpleName(), e);
        } catch (JsonProcessingException e) {
            log.error("catch JsonProcessingException, content:[{}], clazz:[{}], exception detail:",
                    content, clazz.getSimpleName(), e);
        } catch (Exception e) {
            log.error("catch Exception, content:[{}], clazz:[{}], exception detail:",
                    content, clazz.getSimpleName(), e);
        }
        return null;
    }

    /**
     * json content 转 Map
     *
     * @param content json content
     * @return Map
     */
    public static Map<String, Object> toMap(String content) {
        try {
            return mapper.readValue(content, new TypeReference<Map<String, Object>>() {
            });
        } catch (JsonMappingException e) {
            log.error("catch JsonMappingException, content:[{}], exception detail:", content, e);
        } catch (JsonProcessingException e) {
            log.error("catch JsonProcessingException, content:[{}], exception detail:", content, e);
        } catch (Exception e) {
            log.error("catch Exception, content:[{}], exception detail:", content, e);
        }
        return null;
    }

    public static void main(String[] args) throws JsonProcessingException {
        ObjectNode root = mapper.createObjectNode();
        root.put("username", "bn");
        root.put("password", "test@12");
        root.put("subjectId", "455");
        String json = mapper.writeValueAsString(root);
        System.out.println(json);
        System.out.println(mapper.readValue(json, Subject.class));

        System.out.println(GatewayResponse.fail("HJk"));

    }

}