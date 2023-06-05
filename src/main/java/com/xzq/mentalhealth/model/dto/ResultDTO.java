package com.xzq.mentalhealth.model.dto;

import com.xzq.mentalhealth.model.entity.Question;
import lombok.Data;

import java.util.List;

@Data
public class ResultDTO {
    private Long paperId;
    private Long userId;
    private List<Question> addQuestion;
}
