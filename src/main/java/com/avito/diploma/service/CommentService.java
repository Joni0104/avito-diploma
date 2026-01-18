package com.avito.diploma.service;

import com.avito.diploma.dto.CommentDTO;
import com.avito.diploma.dto.CommentsDTO;
import com.avito.diploma.dto.CreateOrUpdateCommentDTO;
import com.avito.diploma.entity.Ad;
import com.avito.diploma.entity.Comment;
import com.avito.diploma.entity.User;
import com.avito.diploma.exception.AccessDeniedException;
import com.avito.diploma.exception.AdNotFoundException;
import com.avito.diploma.exception.CommentNotFoundException;
import com.avito.diploma.mapper.CommentMapper;
import com.avito.diploma.repository.AdRepository;
import com.avito.diploma.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final AdRepository adRepository;
    private final CommentMapper commentMapper;
    private final UserService userService;

    public CommentsDTO getComments(Integer adId) {
        if (!adRepository.existsById(adId)) {
            throw new AdNotFoundException("Объявление не найдено: " + adId);
        }

        List<Comment> comments = commentRepository.findByAdId(adId);

        CommentsDTO commentsDTO = new CommentsDTO();
        commentsDTO.setCount(comments.size());
        commentsDTO.setResults(comments.stream()
                .map(commentMapper::toDTO)
                .toList());

        return commentsDTO;
    }

    @Transactional
    public CommentDTO addComment(Integer adId, CreateOrUpdateCommentDTO createOrUpdateCommentDTO) {
        User author = userService.getCurrentUserEntity();
        Ad ad = adRepository.findById(adId)
                .orElseThrow(() -> new AdNotFoundException("Объявление не найдено: " + adId));

        Comment comment = commentMapper.toEntity(createOrUpdateCommentDTO);
        comment.setAuthor(author);
        comment.setAd(ad);

        comment = commentRepository.save(comment);
        return commentMapper.toDTO(comment);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN') or @commentService.isCommentOwner(#commentId, authentication.name)")
    public void deleteComment(Integer adId, Integer commentId) {
        if (!commentRepository.existsById(commentId)) {
            throw new CommentNotFoundException("Комментарий не найден: " + commentId);
        }
        commentRepository.deleteById(commentId);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN') or @commentService.isCommentOwner(#commentId, authentication.name)")
    public CommentDTO updateComment(Integer adId, Integer commentId,
                                    CreateOrUpdateCommentDTO createOrUpdateCommentDTO) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Комментарий не найден: " + commentId));

        comment.setText(createOrUpdateCommentDTO.getText());
        comment = commentRepository.save(comment);

        return commentMapper.toDTO(comment);
    }

    public boolean isCommentOwner(Integer commentId, String username) {
        return commentRepository.findById(commentId)
                .map(comment -> comment.getAuthor().getEmail().equals(username))
                .orElse(false);
    }

    // Явная проверка прав
    public void checkCommentAccess(Integer commentId) {
        User currentUser = userService.getCurrentUserEntity();
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Комментарий не найден"));

        if (!comment.getAuthor().getId().equals(currentUser.getId()) &&
                !currentUser.getRole().equals(User.Role.ADMIN)) {
            throw new AccessDeniedException("Нет прав для редактирования этого комментария");
        }
    }
}