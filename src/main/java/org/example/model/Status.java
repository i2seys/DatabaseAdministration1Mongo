package org.example.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

@Data
@NoArgsConstructor
public class Status {
    public static final String ID_FIELD = "_id";
    public static final String NAME_FIELD = "name";
    public static final String COLLECTING_BY_CLIENT_NAME_FIELD = "Собирается клиентом";
    @BsonProperty(ID_FIELD)
    private ObjectId id;
    @BsonProperty(NAME_FIELD)
    private String name;
}
