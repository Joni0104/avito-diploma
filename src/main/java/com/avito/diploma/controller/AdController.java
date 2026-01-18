package com.avito.diploma.controller;

import com.avito.diploma.dto.AdDTO;
import com.avito.diploma.dto.AdsDTO;
import com.avito.diploma.dto.CreateOrUpdateAdDTO;
import com.avito.diploma.dto.ExtendedAdDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;

@RestController
@RequestMapping("/ads")
@CrossOrigin(value = "http://localhost:3000")
@Tag(name = "Объявления")
public class AdController {

    @Operation(
            summary = "Получение всех объявлений",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(schema = @Schema(implementation = AdsDTO.class))
                    )
            }
    )
    @GetMapping
    public ResponseEntity<AdsDTO> getAllAds() {
        // Заглушка - пустой список объявлений
        AdsDTO adsDTO = new AdsDTO();
        adsDTO.setCount(0);
        adsDTO.setResults(Collections.emptyList());
        return ResponseEntity.ok(adsDTO);
    }

    @Operation(
            summary = "Добавление объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Created",
                            content = @Content(schema = @Schema(implementation = AdDTO.class))
                    ),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdDTO> addAd(
            @RequestPart("properties") CreateOrUpdateAdDTO properties,
            @RequestPart("image") MultipartFile image) {
        // Заглушка
        System.out.println("Add ad: " + properties.getTitle() + ", image: " + image.getOriginalFilename());

        AdDTO adDTO = new AdDTO();
        adDTO.setPk(1);
        adDTO.setAuthor(1);
        adDTO.setTitle(properties.getTitle());
        adDTO.setPrice(properties.getPrice());
        adDTO.setImage("/images/ads/1.jpg");

        return ResponseEntity.status(201).body(adDTO);
    }

    @Operation(
            summary = "Получение информации об объявлении",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(schema = @Schema(implementation = ExtendedAdDTO.class))
                    ),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "404", description = "Not found")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ExtendedAdDTO> getAds(
            @Parameter(description = "ID объявления", required = true)
            @PathVariable Integer id) {
        // Заглушка
        ExtendedAdDTO extendedAdDTO = new ExtendedAdDTO();
        extendedAdDTO.setPk(id);
        extendedAdDTO.setAuthorFirstName("Иван");
        extendedAdDTO.setAuthorLastName("Иванов");
        extendedAdDTO.setDescription("Описание объявления");
        extendedAdDTO.setEmail("user@example.com");
        extendedAdDTO.setImage("/images/ads/" + id + ".jpg");
        extendedAdDTO.setPhone("+79991234567");
        extendedAdDTO.setPrice(10000);
        extendedAdDTO.setTitle("Объявление " + id);

        return ResponseEntity.ok(extendedAdDTO);
    }

    @Operation(
            summary = "Удаление объявления",
            responses = {
                    @ApiResponse(responseCode = "204", description = "No Content"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not found")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeAd(
            @Parameter(description = "ID объявления", required = true)
            @PathVariable Integer id) {
        // Заглушка
        System.out.println("Delete ad with id: " + id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Обновление информации об объявлении",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(schema = @Schema(implementation = AdDTO.class))
                    ),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not found")
            }
    )
    @PatchMapping("/{id}")
    public ResponseEntity<AdDTO> updateAds(
            @Parameter(description = "ID объявления", required = true)
            @PathVariable Integer id,
            @RequestBody CreateOrUpdateAdDTO createOrUpdateAdDTO) {
        // Заглушка
        System.out.println("Update ad " + id + ": " + createOrUpdateAdDTO.getTitle());

        AdDTO adDTO = new AdDTO();
        adDTO.setPk(id);
        adDTO.setAuthor(1);
        adDTO.setTitle(createOrUpdateAdDTO.getTitle());
        adDTO.setPrice(createOrUpdateAdDTO.getPrice());
        adDTO.setImage("/images/ads/" + id + ".jpg");

        return ResponseEntity.ok(adDTO);
    }

    @Operation(
            summary = "Получение объявлений авторизованного пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(schema = @Schema(implementation = AdsDTO.class))
                    ),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    @GetMapping("/me")
    public ResponseEntity<AdsDTO> getAdsMe() {
        // Заглушка - пустой список
        AdsDTO adsDTO = new AdsDTO();
        adsDTO.setCount(0);
        adsDTO.setResults(Collections.emptyList());
        return ResponseEntity.ok(adsDTO);
    }

    @Operation(
            summary = "Обновление картинки объявления",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not found")
            }
    )
    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> updateImage(
            @Parameter(description = "ID объявления", required = true)
            @PathVariable Integer id,
            @RequestParam("image") MultipartFile image) {
        // Заглушка
        System.out.println("Update image for ad " + id + ": " + image.getOriginalFilename());
        return ResponseEntity.ok().build();
    }
}