package com.example.demo.api_gateway.service;

import com.example.demo.api_gateway.dto.CreateSneakerRequest;
import com.example.demo.api_gateway.dto.SneakerResponse;
import com.example.demo.api_gateway.dto.UpdateSneakerRequest;
import com.example.demo.api_gateway.exception.CoreServiceException;
import com.example.demo.api_gateway.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SneakerService {
    private final RestTemplate restTemplate;
    @Value("${core-service.url}/sneakers")
    private String coreServiceSneakersApiUrl;

    public List<SneakerResponse> getSneakers() {
        try {
            ResponseEntity<SneakerResponse[]> response = restTemplate.getForEntity(coreServiceSneakersApiUrl, SneakerResponse[].class);
            return List.of(Objects.requireNonNull(response.getBody()));
        } catch (RestClientException e) {
            throw new CoreServiceException("Error when getting the list of sneakers", e);
        }
    }

    public SneakerResponse getSneaker(Long id) {
        try {
            return restTemplate.getForObject(coreServiceSneakersApiUrl + "/" + id, SneakerResponse.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new NotFoundException("Sneaker with ID " + id + " not found");
        } catch (RestClientException e) {
            throw new CoreServiceException("Error when receiving a sneaker", e);
        }
    }

    public SneakerResponse addSneaker(CreateSneakerRequest request) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<CreateSneakerRequest> entity = new HttpEntity<>(request, headers);
            return restTemplate.postForObject(coreServiceSneakersApiUrl, entity, SneakerResponse.class);
        } catch (RestClientException e) {
            throw new CoreServiceException("Error when adding a sneaker", e);
        }
    }

    public SneakerResponse updateSneaker(UpdateSneakerRequest request) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<UpdateSneakerRequest> entity = new HttpEntity<>(request, headers);
            ResponseEntity<SneakerResponse> response = restTemplate.exchange(coreServiceSneakersApiUrl, HttpMethod.PUT, entity, SneakerResponse.class);
            return response.getBody();
        } catch (HttpClientErrorException.NotFound e) {
            throw new NotFoundException("Sneaker with ID " + request.id() + " not found");
        } catch (RestClientException e) {
            throw new CoreServiceException("Error when updating a sneaker", e);
        }
    }

    public void deleteSneaker(Long id) {
        try {
            restTemplate.delete(coreServiceSneakersApiUrl + "/" + id);
        } catch (HttpClientErrorException.NotFound e) {
            throw new NotFoundException("Sneaker with ID " + id + " not found");
        } catch (RestClientException e) {
            throw new CoreServiceException("Error when deleting a sneaker", e);
        }
    }
}
