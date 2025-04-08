package com.example.demo.core_service.repository;

import com.example.demo.core_service.entity.Sneaker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
class SneakerRepositoryTest {
    @Autowired
    private SneakerRepository sneakerRepository;

    @Test
    @DisplayName("save should save sneaker when valid object is provided")
    void save_ShouldSaveSneaker_WhenValidObjectIsProvided() {
        // given
        Sneaker sneaker = Sneaker.builder()
                .model("Nike Air Max")
                .releaseDate(LocalDate.of(2023, 1, 1))
                .brand("Nike")
                .price(new BigDecimal("199.99"))
                .size(42)
                .build();

        // when
        Sneaker savedSneaker = sneakerRepository.save(sneaker);

        // then
        assertThat(savedSneaker.getId()).isNotNull();
        assertThat(savedSneaker.getModel()).isEqualTo("Nike Air Max");
        assertThat(savedSneaker.getReleaseDate()).isEqualTo(LocalDate.of(2023, 1, 1));
        assertThat(savedSneaker.getBrand()).isEqualTo("Nike");
        assertThat(savedSneaker.getPrice()).isEqualByComparingTo(new BigDecimal("199.99"));
        assertThat(savedSneaker.getSize()).isEqualTo(42);
    }

    @Test
    @DisplayName("findById should return sneaker when it exists")
    void findById_ShouldReturnSneaker_WhenItExists() {
        // given
        Sneaker sneaker = Sneaker.builder()
                .model("Nike Air Max")
                .releaseDate(LocalDate.of(2023, 1, 1))
                .brand("Nike")
                .price(new BigDecimal("199.99"))
                .size(42)
                .build();
        Sneaker savedSneaker = sneakerRepository.save(sneaker);

        // when
        Optional<Sneaker> foundSneaker = sneakerRepository.findById(savedSneaker.getId());

        // then
        assertThat(foundSneaker).isPresent();
        assertThat(foundSneaker.get().getModel()).isEqualTo("Nike Air Max");
    }

    @Test
    @DisplayName("findById should return empty when sneaker does not exist")
    void findById_ShouldReturnEmpty_WhenSneakerDoesNotExist() {
        // when
        Optional<Sneaker> foundSneaker = sneakerRepository.findById(999L);

        // then
        assertThat(foundSneaker).isEmpty();
    }
}
