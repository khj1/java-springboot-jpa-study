package jpabook.japshop.api.service.item;

import jpabook.japshop.api.service.item.request.ItemCreateRequest;
import jpabook.japshop.api.service.item.response.ItemCreateResponse;
import jpabook.japshop.domain.item.Item;
import jpabook.japshop.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemCreateResponse saveItem(ItemCreateRequest request) {
        Item item = request.toEntity();
        Item savedItem = itemRepository.save(item);

        return ItemCreateResponse.of(savedItem.getId());
    }
}
