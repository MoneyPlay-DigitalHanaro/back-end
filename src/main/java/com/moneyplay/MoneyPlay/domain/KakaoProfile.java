package com.moneyplay.MoneyPlay.domain;

import lombok.Data;

@Data
public class KakaoProfile {

    public Long id;
    public String connected_at;
    public Properties properties;
    public KakaoAccount kakao_account;

    @Data
    public class Properties {
        public String nickname;
        public String profile_image; // 이미지 경로 필드1
        public String thumbnail_image;
    }

    @Data
    public class KakaoAccount {
        public Boolean profile_nickname_needs_agreement;
        public Boolean profile_image_needs_agreement;
        public Profile profile;
        public Boolean has_email;
        public Boolean email_needs_agreement;
        public Boolean is_email_valid;
        public Boolean is_email_verified;
        public String email;
        public String has_age_range;
        public Boolean age_range_needs_agreement;
        public String age_range;
        public Boolean has_birthday;
        public Boolean birthday_needs_agreement;
        public String birthday;
        public String birthday_type;
        public Boolean has_gender;
        public Boolean gender_needs_agreement;
        public String gender;


        @Data
        public class Profile {
            public String nickname;
            public String thumbnail_image_url;
            public String profile_image_url; // 이미지 경로 필드2
            public Boolean is_default_image;
        }
    }
}
