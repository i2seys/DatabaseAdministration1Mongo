package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

@Data
@NoArgsConstructor
public class Position {
    public static final String ID_FIELD = "_id";
    public static final String NAME_FIELD = "name";
    @BsonProperty(ID_FIELD)
    private ObjectId id;
    @BsonProperty(NAME_FIELD)
    private String name;
}
