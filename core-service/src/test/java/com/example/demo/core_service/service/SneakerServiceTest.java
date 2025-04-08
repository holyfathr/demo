package com.example.demo.core_service.service;

import com.example.demo.core_service.dto.CreateSneakerRequest;
import com.example.demo.core_service.dto.UpdateSneakerRequest;
import com.example.demo.core_service.entity.Sneaker;
import com.example.demo.core_service.exception.NotFoundException;
import com.example.demo.core_service.mapper.SneakerMapper;
import com.example.demo.core_service.repository.SneakerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SneakerServiceTest {
    @Mock
    private SneakerRepository sneakerRepository;

    @Mock
    private SneakerMapper sneakerMapper;

    private SneakerService sneakerService;

    @Test
    @DisplayName("getSneakers_ReturnsListOfSneakers_WhenDataExists")
    void getSneakers_ReturnsListOfSneakers_WhenDataExists() {
        // given
        List<Sneaker> sneakers = List.of(new Sneaker(), new Sneaker(), new Sneaker());
        when(sneakerRepository.findAll()).thenReturn(sneakers);

        // when
        List<Sneaker> result = sneakerService.getSneakers();

        // then
        assertThat(result).hasSize(3).containsExactlyElementsOf(sneakers);
        verify(sneakerRepository).findAll();
    }

    @Test
    @DisplayName("getSneaker_ReturnsSneaker_WhenExistsInDatabase")
    void getSneaker_ReturnsSneaker_WhenExistsInDatabase() {
        // given
        Sneaker sneaker = new Sneaker();
        when(sneakerRepository.findById(1L)).thenReturn(Optional.of(sneaker));

        // when
        Sneaker result = sneakerService.getSneaker(1L);

        // then
        assertThat(result).isEqualTo(sneaker);
        verify(sneakerRepository).findById(1L);
    }

    @Test
    @DisplayName("getSneaker_ThrowsNotFoundException_WhenSneakerNotFound")
    void getSneaker_ThrowsNotFoundException_WhenSneakerNotFound() {
        // given
        when(sneakerRepository.findById(1L)).thenReturn(Optional.empty());

        // when/then
        assertThatThrownBy(() -> sneakerService.getSneaker(1L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Sneaker with ID 1 not found");
        verify(sneakerRepository).findById(1L);
    }

    @Test
    @DisplayName("addSneaker_ReturnsSavedSneaker_WhenDataIsValid")
    void addSneaker_ReturnsSavedSneaker_WhenDataIsValid() {
        // given
        CreateSneakerRequest request = new CreateSneakerRequest("Nike Air Max", "2023-01-01", "Nike", 199.99, 42);
        Sneaker sneaker = new Sneaker();
        when(sneakerMapper.toEntity(request)).thenReturn(sneaker);
        when(sneakerRepository.save(sneaker)).thenReturn(sneaker);

        // when
        Sneaker result = sneakerService.addSneaker(request);

        // then
        assertThat(result).isEqualTo(sneaker);
        verify(sneakerMapper).toEntity(request);
        verify(sneakerRepository).save(sneaker);
    }

    @Test
    @DisplayName("updateSneaker_ReturnsSneaker_WhenSneakerExists")
    void updateSneaker_ReturnsSneaker_WhenSneakerExists() {
        // given
        UpdateSneakerRequest request = new UpdateSneakerRequest(1L, "Updated Nike Air Max", "2023-01-01", "Nike", 199.99, 42);
        Sneaker existingSneaker = new Sneaker();
        Sneaker updatedSneaker = new Sneaker();
        when(sneakerRepository.findById(1L)).thenReturn(Optional.of(existingSneaker));
        when(sneakerMapper.updateEntity(existingSneaker, request)).thenReturn(updatedSneaker);
        when(sneakerRepository.save(updatedSneaker)).thenReturn(updatedSneaker);

        // when
        Sneaker result = sneakerService.updateSneaker(request);

        // then
        assertThat(result).isEqualTo(updatedSneaker);
        verify(sneakerRepository).findById(1L);
        verify(sneakerMapper).updateEntity(existingSneaker, request);
        verify(sneakerRepository).save(updatedSneaker);
    }

    @Test
    @DisplayName("updateSneaker_ThrowsNotFoundException_WhenSneakerNotFound")
    void updateSneaker_ThrowsNotFoundException_WhenSneakerNotFound() {
        // given
        UpdateSneakerRequest request = new UpdateSneakerRequest(1L, "Updated Nike Air Max", "2023-01-01", "Nike", 199.99, 42);
        when(sneakerRepository.findById(1L)).thenReturn(Optional.empty());

        // when/then
        assertThatThrownBy(() -> sneakerService.updateSneaker(request))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Sneaker with ID 1 not found");
        verify(sneakerRepository).findById(1L);
    }

    @Test
    @DisplayName("deleteSneaker_DeletesSneaker_WhenSneakerExists")
    void deleteSneaker_DeletesSneaker_WhenSneakerExists() {
        // given
        Sneaker sneaker = new Sneaker();
        when(sneakerRepository.findById(1L)).thenReturn(Optional.of(sneaker));

        // when
        sneakerService.deleteSneaker(1L);

        // then
        verify(sneakerRepository).findById(1L);
        verify(sneakerRepository).delete(sneaker);
    }

    @Test
    @DisplayName("deleteSneaker_ThrowsNotFoundException_WhenSneakerNotFound")
    void deleteSneaker_ThrowsNotFoundException_WhenSneakerNotFound() {
        // given
        when(sneakerRepository.findById(1L)).thenReturn(Optional.empty());

        // when/then
        assertThatThrownBy(() -> sneakerService.deleteSneaker(1L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Sneaker with ID 1 not found");
        verify(sneakerRepository).findById(1L);
    }

    @BeforeEach
    void setUp() {
        sneakerService = new SneakerService(sneakerRepository, sneakerMapper);
    }
}
