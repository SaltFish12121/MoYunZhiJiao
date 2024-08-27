package com.moyunzhijiao.system_app.service.collection;

import com.moyunzhijiao.system_app.controller.dto.CollectDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CollectService {
    @Autowired
    VideoCollectionService videoCollectionService;
    @Autowired
    KnowledgeCollectionService knowledgeCollectionService;
    @Autowired
    ExerciseCollectionService exerciseCollectionService;

    public boolean updateCollect(CollectDTO collectDTO) {
        switch (collectDTO.getCollectType()) {
            case "Video":
                return videoCollectionService.handleCollect(collectDTO);
            case "VideoCollection":
                return videoCollectionService.handleCollectionCollect(collectDTO);
            case "Knowledge":
                return knowledgeCollectionService.handleKnowledgeCollect(collectDTO);
            case "SelfExercise":
            case "SchoolExercise":
            case "ExcellentHomework":
            case "ExcellentCompetition":
//            case "Competition":
                return exerciseCollectionService.handleExerciseCollect(collectDTO);
            default:
                return false;
        }
    }
}
