package com.avito.diploma.controller;

import com.avito.diploma.dto.*;
import com.avito.diploma.service.AdService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/ads")
@CrossOrigin(value = "http://localhost:3000")
@Tag(name = "Объявления")
@RequiredArgsConstructor
public class AdController {

    private final AdService adService;

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
    public ResponseEntity<AdsDTO> getAllAds(Pageable pageable) {
        AdsDTO adsDTO = adService.getAllAds(pageable);
        return ResponseEntity.ok(adsDTO);
    }

    @Operation(
            summary = "Добавление объявления (без изображения)",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Created",
                            content = @Content(schema = @Schema(implementation = AdDTO.class))
                    ),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    @PostMapping
    public ResponseEntity<AdDTO> addAd(@RequestBody CreateOrUpdateAdDTO createOrUpdateAdDTO) {
        AdDTO adDTO = adService.createAd(createOrUpdateAdDTO);
        return ResponseEntity.status(201).body(adDTO);
    }

    @Operation(
            summary = "Добавление объявления с изображением",
            description = "Используйте multipart/form-data для отправки данных",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Created",
                            content = @Content(schema = @Schema(implementation = AdDTO.class))
                    ),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "400", description = "Bad Request - ошибка при загрузке файла")
            }
    )
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdDTO> addAdWithImage(
            @RequestPart("properties") CreateOrUpdateAdDTO properties,
            @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {

        AdDTO adDTO;
        if (image != null && !image.isEmpty()) {
            adDTO = adService.createAdWithImage(properties, image);
        } else {
            adDTO = adService.createAd(properties);
        }

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
    public ResponseEntity<ExtendedAdDTO> getAd(
            @Parameter(description = "ID объявления", required = true)
            @PathVariable Integer id) {
        ExtendedAdDTO extendedAdDTO = adService.getAd(id);
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
        adService.deleteAd(id);
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
    public ResponseEntity<AdDTO> updateAd(
            @Parameter(description = "ID объявления", required = true)
            @PathVariable Integer id,
            @RequestBody CreateOrUpdateAdDTO createOrUpdateAdDTO) {
        AdDTO adDTO = adService.updateAd(id, createOrUpdateAdDTO);
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
        AdsDTO adsDTO = adService.getUserAds();
        return ResponseEntity.ok(adsDTO);
    }

    @Operation(
            summary = "Обновление изображения объявления",
            description = "Загружает новое изображение для существующего объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(mediaType = "image/*")
                    ),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not found"),
                    @ApiResponse(responseCode = "400", description = "Bad Request - ошибка при загрузке файла")
            }
    )
    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> updateImage(
            @PathVariable Integer id,
            @RequestParam("image") MultipartFile image) throws IOException {

        byte[] imageData = adService.updateAdImage(id, image);
        String contentType = image.getContentType();

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType != null ? contentType : MediaType.IMAGE_JPEG_VALUE))
                .body(imageData);
    }
}