package com.study.jpa.chap05.dto;

import lombok.*;

@Setter @Getter @ToString
@NoArgsConstructor @AllArgsConstructor
@Builder
public class GroupAverageAgeDto {

    private String groupName;
    private double averageAge;

}
