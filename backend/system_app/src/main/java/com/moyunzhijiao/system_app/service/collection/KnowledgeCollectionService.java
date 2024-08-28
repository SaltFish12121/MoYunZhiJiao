package com.moyunzhijiao.system_app.service.collection;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_app.controller.dto.CollectDTO;
import com.moyunzhijiao.system_app.controller.dto.fonted.KnowledgeInfo;
import com.moyunzhijiao.system_app.entity.knowledge.Knowledge;
import com.moyunzhijiao.system_app.entity.collection.KnowledgeCollection;
import com.moyunzhijiao.system_app.entity.knowledge.KnowledgeContents;
import com.moyunzhijiao.system_app.mapper.knowledge.KnowledgeContentsMapper;
import com.moyunzhijiao.system_app.mapper.knowledge.KnowledgeMapper;
import com.moyunzhijiao.system_app.mapper.collection.KnowledgeCollectionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KnowledgeCollectionService extends ServiceImpl<KnowledgeCollectionMapper, KnowledgeCollection> {
    @Autowired
    KnowledgeCollectionMapper knowledgeCollectionMapper;

    @Autowired
    KnowledgeMapper knowledgeMapper;
    @Autowired
    KnowledgeContentsMapper knowledgeContentsMapper;


    // 获取用户知识收藏
    public List<KnowledgeInfo> getKnowledgeCollect(Integer userId, Integer pageNumber, Integer pageSize) {
        // 通过用户ID找到收藏表中的 knowledgeId 和 type
        QueryWrapper<KnowledgeCollection> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_id", userId);
        List<KnowledgeCollection> collections = knowledgeCollectionMapper.selectList(queryWrapper);

        System.out.println("获取的数据" + collections);

        List<KnowledgeInfo> results = new ArrayList<>();
        for (KnowledgeCollection collection : collections) {
            System.out.println("获取的KnowledgeId" + collection.getResourcesId());
            KnowledgeInfo result = getKnowledgeByID(collection.getResourcesId(), userId);
            if (result != null) {
                results.add(result);
            }
        }

        // 分页处理
        int start = (pageNumber - 1) * pageSize;
        int end = Math.min(start + pageSize, results.size());

        // 检查索引有效性
        if (start >= results.size()) {
            // 如果结果为空或起始索引超出范围，返回空列表
            return new ArrayList<>();
        }

        List<KnowledgeInfo> pagedResults = results.subList(start, end);

        return pagedResults;
    }

    private KnowledgeInfo getKnowledgeByID(Integer knowledgeId, Integer userId) {
        System.out.println("获取的KnowledgeId" + knowledgeId);
        return getKnowledge(knowledgeId, userId);
    }

    private KnowledgeInfo getKnowledge(Integer knowledgeId, Integer userId) {
        System.out.println("获取的KnowledgeId: " + knowledgeId);
        if (knowledgeId != null) {
            Knowledge knowledge = knowledgeMapper.selectById(knowledgeId);
            if (knowledge != null) {
                System.out.println("获取的 create_time: " + knowledge.getCreatedTime());
                boolean ifRecommend = knowledge.getIsRecommended() == 1;

                // 获取知识内容并检查是否为空
                KnowledgeContents knowledgeContents = knowledgeContentsMapper.selectById(knowledge.getId());
                String content = (knowledgeContents != null) ? knowledgeContents.getContent() : null;

                // 检查是否已收藏
                QueryWrapper<KnowledgeCollection> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("student_id", userId).eq("resources_id", knowledgeId);
                KnowledgeCollection knowledgeCollection = knowledgeCollectionMapper.selectOne(queryWrapper);
                boolean isCollected = (knowledgeCollection != null);

                return new KnowledgeInfo(
                        knowledgeId,
                        knowledge.getTag(),
                        ifRecommend,
                        knowledge.getName(),
                        null,
                        knowledge.getCreatedTime(),
                        knowledge.getPictureUrl(),
                        content,
                        isCollected
                );
            } else {
                System.out.println("未找到对应的 knowledge 记录");
            }
        } else {
            System.out.println("未找到对应的 knowledgeId");
        }
        return null;
    }



    // 处理知识收藏
    public boolean handleKnowledgeCollect(CollectDTO collectDTO) {
        if (collectDTO.getNewCollect()) {
            return addKnowledgeCollect(collectDTO);
        } else {
            return removeKnowledgeCollect(collectDTO);
        }
    }


    public boolean addKnowledgeCollect(CollectDTO collectDTO) {
        // 检查是否已经存在
        Long count = knowledgeCollectionMapper.selectCount(
                new QueryWrapper<KnowledgeCollection>()
                        .eq("student_id", collectDTO.getUserId())
                        .eq("resources_id", collectDTO.getCollectId())
        );
        if (count > 0) {
            return false; // 已经存在，不重复添加
        }

        KnowledgeCollection knowledgeCollection = new KnowledgeCollection();
        knowledgeCollection.setStudentId(collectDTO.getUserId());
        knowledgeCollection.setResourcesId(collectDTO.getCollectId());
        return knowledgeCollectionMapper.insert(knowledgeCollection) > 0;
    }


    public boolean removeKnowledgeCollect(CollectDTO collectDTO) {
        return knowledgeCollectionMapper.delete(
                new QueryWrapper<KnowledgeCollection>()
                        .eq("student_id", collectDTO.getUserId())
                        .eq("resources_id", collectDTO.getCollectId())
        ) > 0;
    }
}

