package jpabook.japshop.api.service.item;

import jpabook.japshop.api.service.item.mapper.ItemMapper;
import jpabook.japshop.api.service.item.request.ItemCreateRequest;
import jpabook.japshop.api.service.item.response.ItemCreateResponse;
import jpabook.japshop.api.service.item.response.ItemResponse;
import jpabook.japshop.domain.item.Item;
import jpabook.japshop.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    @Transactional
    public ItemCreateResponse saveItem(ItemCreateRequest request) {
        Item item = request.toEntity();
        Item savedItem = itemRepository.save(item);

        return ItemCreateResponse.of(savedItem.getId());
    }

    public List<ItemResponse> findAll() {
        List<Item> foundItems = itemRepository.findAll();
        return foundItems.stream()
            .map(itemMapper::toResponse)
            .toList();
    }

    public ItemResponse findOne(Long id) {
        Item foundItem = itemRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));

        return itemMapper.toResponse(foundItem);
    }
}
