package org.iclass.story.dto;

import org.iclass.story.domain.UserAccount;

import java.time.LocalDateTime;

public record UserrAccountDto(
        Long id,
        String userId,
        String password,
        String email,
        String nickname,
        String memo,
        LocalDateTime createAt,
        String createBy,
        LocalDateTime modifiedAt,
        String modifiedBy

) {

    public static UserrAccountDto of(Long id,String userId,String password,String email,String nickname, String memo,
                                     LocalDateTime createAt, String createdBy,LocalDateTime modifiedAt, String modifiedBy){
        return new UserrAccountDto(id,userId,password,email,nickname,memo,createAt,createdBy,modifiedAt,modifiedBy);
    }

    public static  UserrAccountDto from (UserAccount entity){
        return new UserrAccountDto(entity.getId(),
                entity.getUserId(),
                entity.getUserPassword(),
                entity.getEmail(),
                entity.getNickname(),
                entity.getMemo(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy());
    }
    public UserAccount toEntity() {
        return UserAccount.of(
                userId,
                password,
                email,
                nickname,
                memo);
    }
}
