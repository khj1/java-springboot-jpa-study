package jpabook.japshop.api.service.item;

import jpabook.japshop.api.service.item.request.AlbumCreateRequest;
import jpabook.japshop.api.service.item.request.ItemCreateRequest;
import jpabook.japshop.api.service.item.response.AlbumResponse;
import jpabook.japshop.api.service.item.response.ItemCreateResponse;
import jpabook.japshop.api.service.item.response.ItemResponse;
import jpabook.japshop.domain.item.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@ActiveProfiles("test")
@Transactional
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

    @DisplayName("상품을 모두 조회한다.")
    @Test
    void findAll() {
        //given
        Album album = Album.builder()
            .name("앨범")
            .price(10_000)
            .stockQuantity(10)
            .build();

        Book book = Book.builder()
            .name("책")
            .price(20_000)
            .stockQuantity(5)
            .build();

        Movie movie = Movie.builder()
            .name("영화")
            .price(25_000)
            .stockQuantity(1)
            .build();

        itemRepository.saveAll(List.of(album, book, movie));

        //when
        List<ItemResponse> responses = itemService.findAll();

        //then
        assertThat(responses).hasSize(3)
            .extracting("name", "price", "stockQuantity")
            .containsExactlyInAnyOrder(
                tuple("앨범", 10_000, 10),
                tuple("책", 20_000, 5),
                tuple("영화", 25_000, 1)
            );
    }

    @DisplayName("상품을 한 건 조회한다.")
    @Test
    void findOne() {
        //given
        Album album = Album.builder()
            .name("앨범")
            .price(10_000)
            .stockQuantity(10)
            .build();

        Album savedItem = itemRepository.save(album);

        //when
        ItemResponse response = itemService.findOne(savedItem.getId());

        //then
        assertThat(response)
            .isInstanceOf(AlbumResponse.class)
            .extracting("name", "price", "stockQuantity")
            .contains("앨범", 10_000, 10);
    }

}