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


    //OSIV - 트랜잭션 범위를 벗어난 controller 등에서도 하이버네이트 세션이 살아었고 Entity를 사용하여 코드 생산성을 높일 수 있으나 이슈가 있음.
    //      jpa 의 open-in-view 속성 설정하기
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
