package jpabook.japshop.api.service.item;

import jpabook.japshop.api.service.item.request.AlbumCreateRequest;
import jpabook.japshop.api.service.item.request.ItemCreateRequest;
import jpabook.japshop.api.service.item.response.ItemCreateResponse;
import jpabook.japshop.domain.item.Item;
import jpabook.japshop.domain.item.ItemRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
class ItemServiceTest {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemService itemService;

    @DisplayName("상품을 등록한다.")
    @Test
    void saveItem() {
        //given
        ItemCreateRequest albumRequest = new AlbumCreateRequest(
            "A", "앨범", 10_000, 10, "아티스트", "기타사항"
        );

        //when
        ItemCreateResponse response = itemService.saveItem(albumRequest);
        Item savedItem = itemRepository.findById(response.itemId())
            .orElseThrow(() -> new AssertionError("상품이 저장되지 않았습니다."));

        //then
        assertThat(savedItem)
            .extracting("name", "price", "stockQuantity", "artist", "etc")
            .contains("앨범", 10_000, 10, "아티스트", "기타사항");
    }

}