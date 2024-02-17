package org.example.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;
import com.mongodb.BasicDBObject;
import org.bson.BasicBSONObject;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.example.model.Order;

import java.lang.reflect.Type;
import java.time.*;
import java.util.Map;
import java.util.Optional;

public class ObjectToBsonConverter {
//    private static final Gson gson = createGson();
//    private static Gson createGson() {
//        var localDateTimeAdapter = new JsonSerializer<LocalDateTime>() {
//            @Override
//            public JsonElement serialize(
//                    LocalDateTime localDateTime,
//                    Type type,
//                    JsonSerializationContext jsonSerializationContext
//            ) {
//                var dateTime = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());
//                var dateTimeStr = dateTime.toString();
//
//                //¯\_(ツ)_/¯
//                //turn 2024-02-17T20:48:18.285745800+00:00[Europe/Moscow]
//                //into 2024-02-17T20:48:18.285+00:00
//                //¯\_(ツ)_/¯
//
//                var dotInd = dateTimeStr.indexOf(".");
//                var plusInd = dateTimeStr.indexOf("+");
//                var firstPart = dateTimeStr.substring(0, dotInd+4);
//                var secondPart = dateTimeStr.substring(plusInd, plusInd + 6);
//
//                return new JsonPrimitive(firstPart + secondPart);
//            }
//        };
//
//        var objectIdSerializer = new JsonSerializer<ObjectId>() {
//
//            @Override
//            public JsonElement serialize(
//                    ObjectId objectId,
//                    Type type,
//                    JsonSerializationContext jsonSerializationContext
//            ) {
//                return new JsonPrimitive(objectId.toString());
//            }
//        };
//
//        return new GsonBuilder()
//                .registerTypeAdapter(LocalDateTime.class, localDateTimeAdapter)
//                .registerTypeAdapter(ObjectId.class, objectIdSerializer)
//                .create();
//        return new Gson();
//    }

    //private static final ObjectMapper mapper = new ObjectMapper();
    //        var json = gson.toJson(obj);
    //
    //        try {
    //            Map<String,?> map = (Map<String, ?>) mapper.readValue(json, Map.class);
    //            return new Document(map);
    //        } catch (JsonProcessingException e) {
    //            throw new RuntimeException(e);
    //        }

    public static Bson fromOrder(Order order) {
        var bsonOrder = new BasicDBObject();
        Optional.ofNullable(order.getId())
                .ifPresent(v -> bsonOrder.put(Order.ID_FIELD, v));
        Optional.ofNullable(order.getClientId())
                .ifPresent(v -> bsonOrder.put(Order.CLIENT_ID_FIELD, v));
        Optional.ofNullable(order.getProductsInOrder())
                .ifPresent(v -> bsonOrder.put(Order.PRODUCTS_FIELD, v));
        Optional.ofNullable(order.getStatusId())
                .ifPresent(v -> bsonOrder.put(Order.STATUS_ID_FIELD, v));
        Optional.ofNullable(order.getStatusChangeTime())
                .ifPresent(v -> bsonOrder.put(Order.STATUS_CHANGED_TIME_FIELD, v));
        return bsonOrder;
    }


}
