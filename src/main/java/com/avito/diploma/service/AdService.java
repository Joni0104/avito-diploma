package com.avito.diploma.service;

import com.avito.diploma.dto.*;
import com.avito.diploma.entity.Ad;
import com.avito.diploma.entity.Image;
import com.avito.diploma.entity.User;
import com.avito.diploma.exception.AccessDeniedException;
import com.avito.diploma.exception.AdNotFoundException;
import com.avito.diploma.mapper.AdMapper;
import com.avito.diploma.repository.AdRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdService {

    private final AdRepository adRepository;
    private final AdMapper adMapper;
    private final UserService userService;
    private final ImageService imageService;

    public AdsDTO getAllAds(Pageable pageable) {
        Page<Ad> page = adRepository.findAll(pageable);

        AdsDTO adsDTO = new AdsDTO();
        adsDTO.setCount((int) page.getTotalElements());
        adsDTO.setResults(page.getContent().stream()
                .map(adMapper::toDTO)
                .toList());

        return adsDTO;
    }

    @Transactional
    public AdDTO createAd(CreateOrUpdateAdDTO createOrUpdateAdDTO) {
        User author = userService.getCurrentUserEntity();

        Ad ad = adMapper.toEntity(createOrUpdateAdDTO);
        ad.setAuthor(author);
        // image будет добавляться позже через отдельный метод

        ad = adRepository.save(ad);
        return adMapper.toDTO(ad);
    }

    @Transactional
    public AdDTO createAdWithImage(CreateOrUpdateAdDTO createOrUpdateAdDTO, MultipartFile image) throws IOException {
        User author = userService.getCurrentUserEntity();

        Ad ad = adMapper.toEntity(createOrUpdateAdDTO);
        ad.setAuthor(author);

        // Сохраняем изображение
        if (image != null && !image.isEmpty()) {
            Image adImage = imageService.uploadImage(image);
            ad.setImage(adImage);
        }

        ad = adRepository.save(ad);
        return adMapper.toDTO(ad);
    }

    public ExtendedAdDTO getAd(Integer id) {
        Ad ad = adRepository.findById(id)
                .orElseThrow(() -> new AdNotFoundException("Объявление не найдено: " + id));

        return adMapper.toExtendedDTO(ad);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN') or @adService.isAdOwner(#id, authentication.name)")
    public AdDTO updateAd(Integer id, CreateOrUpdateAdDTO createOrUpdateAdDTO) {
        Ad ad = adRepository.findById(id)
                .orElseThrow(() -> new AdNotFoundException("Объявление не найдено: " + id));

        if (createOrUpdateAdDTO.getTitle() != null) {
            ad.setTitle(createOrUpdateAdDTO.getTitle());
        }
        if (createOrUpdateAdDTO.getPrice() != null) {
            ad.setPrice(createOrUpdateAdDTO.getPrice());
        }
        if (createOrUpdateAdDTO.getDescription() != null) {
            ad.setDescription(createOrUpdateAdDTO.getDescription());
        }

        ad = adRepository.save(ad);
        return adMapper.toDTO(ad);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN') or @adService.isAdOwner(#id, authentication.name)")
    public void deleteAd(Integer id) {
        if (!adRepository.existsById(id)) {
            throw new AdNotFoundException("Объявление не найдено: " + id);
        }
        adRepository.deleteById(id);
    }

    @Transactional
    public byte[] updateAdImage(Integer adId, MultipartFile image) throws IOException {
        Ad ad = adRepository.findById(adId)
                .orElseThrow(() -> new AdNotFoundException("Объявление не найдено"));

        // Проверка прав
        checkAdAccess(adId);

        // Удаляем старое изображение если есть
        if (ad.getImage() != null) {
            imageService.deleteImage(ad.getImage().getId());
        }

        // Сохраняем новое изображение
        Image newImage = imageService.uploadImage(image);
        ad.setImage(newImage);
        adRepository.save(ad);

        return newImage.getData();
    }

    public boolean isAdOwner(Integer adId, String username) {
        return adRepository.findById(adId)
                .map(ad -> ad.getAuthor().getEmail().equals(username))
                .orElse(false);
    }

    public AdsDTO getUserAds() {
        User user = userService.getCurrentUserEntity();
        List<Ad> userAds = adRepository.findByAuthor(user);

        AdsDTO adsDTO = new AdsDTO();
        adsDTO.setCount(userAds.size());
        adsDTO.setResults(userAds.stream()
                .map(adMapper::toDTO)
                .toList());

        return adsDTO;
    }

    // Явная проверка прав (альтернатива @PreAuthorize)
    public void checkAdAccess(Integer adId) {
        User currentUser = userService.getCurrentUserEntity();
        Ad ad = adRepository.findById(adId)
                .orElseThrow(() -> new AdNotFoundException("Объявление не найдено"));

        if (!ad.getAuthor().getId().equals(currentUser.getId()) &&
                !currentUser.getRole().equals(User.Role.ADMIN)) {
            throw new AccessDeniedException("Нет прав для редактирования этого объявления");
        }
    }
}