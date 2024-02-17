package org.example.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    public static final String ID_FIELD = "_id";
    public static final String CLIENT_ID_FIELD = "client_id";
    public static final String PRODUCTS_FIELD = "products";
    public static final String STATUS_CHANGED_TIME_FIELD = "purchase_date";
    public static final String STATUS_ID_FIELD = "status";

    @BsonProperty(ID_FIELD)
    @SerializedName(ID_FIELD)
    private ObjectId id;
    @BsonProperty(CLIENT_ID_FIELD)
    @SerializedName(CLIENT_ID_FIELD)
    private ObjectId clientId;
    @BsonProperty(PRODUCTS_FIELD)
    @SerializedName(PRODUCTS_FIELD)
    private List<ProductInOrder> productsInOrder;
    @BsonProperty(STATUS_CHANGED_TIME_FIELD)
    @SerializedName(STATUS_CHANGED_TIME_FIELD)
    private LocalDateTime statusChangeTime;
    @BsonProperty(STATUS_ID_FIELD)
    @SerializedName(STATUS_ID_FIELD)
    private ObjectId statusId;
}
