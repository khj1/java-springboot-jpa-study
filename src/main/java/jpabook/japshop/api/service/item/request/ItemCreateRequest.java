package jpabook.japshop.api.service.item.request;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jpabook.japshop.domain.item.Item;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.EXISTING_PROPERTY,
    property = "dType",
    visible = true
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = AlbumCreateRequest.class, name = "A"),
    @JsonSubTypes.Type(value = BookCreateRequest.class, name = "B"),
    @JsonSubTypes.Type(value = MovieCreateRequest.class, name = "M")
})
public sealed interface ItemCreateRequest
    permits AlbumCreateRequest, BookCreateRequest, MovieCreateRequest {

    String dType();

    String name();

    int price();

    int stockQuantity();

    Item toEntity();
}
