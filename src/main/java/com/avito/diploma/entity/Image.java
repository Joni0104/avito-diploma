package com.avito.diploma.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "images")
@Data
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "file_path", nullable = false)
    private String filePath;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "media_type", length = 50)
    private String mediaType;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "data", columnDefinition = "BYTEA")
    private byte[] data;

    @OneToOne(mappedBy = "image", fetch = FetchType.LAZY)
    private Ad ad;

    @OneToOne(mappedBy = "image", fetch = FetchType.LAZY)
    private User user;
}