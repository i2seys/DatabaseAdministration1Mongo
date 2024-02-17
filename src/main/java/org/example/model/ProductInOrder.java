package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductInOrder {
    public static final String ID_FIELD = "product";
    public static final String AMOUNT_FIELD = "amount";
    @BsonProperty(ID_FIELD)
    private ObjectId id;
    @BsonProperty(AMOUNT_FIELD)
    private int amount;
}
