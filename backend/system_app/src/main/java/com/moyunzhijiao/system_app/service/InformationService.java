package com.moyunzhijiao.system_app.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.moyunzhijiao.system_app.controller.dto.fonted.competition.CompetitionInformInfo;
import com.moyunzhijiao.system_app.controller.dto.fonted.KnowledgeInfo;
import com.moyunzhijiao.system_app.controller.dto.fonted.ProblemInfo;
import com.moyunzhijiao.system_app.controller.dto.fonted.video.VideoCollectionInfo;
import com.moyunzhijiao.system_app.controller.dto.fonted.video.VideoContentInfo;
import com.moyunzhijiao.system_app.controller.dto.fonted.video.VideoInfo;
import com.moyunzhijiao.system_app.controller.dto.fonted.video.VideoListInfo;
import com.moyunzhijiao.system_app.entity.Agreement;
import com.moyunzhijiao.system_app.entity.CommonProblem;
import com.moyunzhijiao.system_app.entity.collection.KnowledgeCollection;
import com.moyunzhijiao.system_app.entity.competition.*;
import com.moyunzhijiao.system_app.entity.knowledge.Knowledge;
import com.moyunzhijiao.system_app.entity.Video;
import com.moyunzhijiao.system_app.entity.collection.Collection1;
import com.moyunzhijiao.system_app.entity.collection.VideoAndCollection;
import com.moyunzhijiao.system_app.entity.knowledge.KnowledgeContents;
import com.moyunzhijiao.system_app.mapper.AgreementMapper;
import com.moyunzhijiao.system_app.mapper.CommonProblemMapper;
import com.moyunzhijiao.system_app.mapper.collection.KnowledgeCollectionMapper;
import com.moyunzhijiao.system_app.mapper.collection.VideoCollectionMapper;
import com.moyunzhijiao.system_app.mapper.competition.*;
import com.moyunzhijiao.system_app.mapper.knowledge.KnowledgeContentsMapper;
import com.moyunzhijiao.system_app.mapper.knowledge.KnowledgeMapper;
import com.moyunzhijiao.system_app.mapper.VideoMapper;
import com.moyunzhijiao.system_app.mapper.collection.VideoAndCollectionMapper;
import com.moyunzhijiao.system_app.mapper.collection.CollectionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class InformationService{
    @Autowired
    CommonProblemMapper commonProblemMapper;
    @Autowired
    KnowledgeMapper knowledgeMapper;
    @Autowired
    VideoMapper videoMapper;
    @Autowired
    VideoAndCollectionMapper videoAndCollectionMapper;
    @Autowired
    VideoCollectionMapper videoCollectionMapper;
    @Autowired
    CollectionMapper collectionMapper;
    @Autowired
    CompetitionMapper competitionMapper;
    @Autowired
    CompetitionRulesMapper competitionRulesMapper;
    @Autowired
    CompetitionRequirementsMapper competitionRequirementsMapper;
    @Autowired
    AgreementMapper agreementMapper;
    @Autowired
    KnowledgeContentsMapper knowledgeContentsMapper;
    @Autowired
    KnowledgeCollectionMapper knowledgeCollectionMapper;

    // 获取常见问题
    public List<ProblemInfo> getCommonProblem() {
        // 从数据库中获取所有常见问题
        List<CommonProblem> commonProblems = commonProblemMapper.selectList(null);

        // 将 CommonProblem 转换为 ProblemInfo
        List<ProblemInfo> problemInfos = new ArrayList<>();
        for (CommonProblem commonProblem : commonProblems) {
            ProblemInfo problemInfo = new ProblemInfo(
                    commonProblem.getId(),
                    commonProblem.getType(),
                    commonProblem.getTitle(),
                    commonProblem.getQ(),
                    commonProblem.getA()
            );
            problemInfos.add(problemInfo);
        }

        return problemInfos;
    }


    // 获取首页知识展示
    public List<KnowledgeInfo> getKnowledgeShow(Integer userId) {
        // 从数据库中获取所有知识展示数据
        List<Knowledge> knowledgeList = knowledgeMapper.selectList(null);

        // 将 Knowledge 转换为 KnowledgeInfo
        List<KnowledgeInfo> knowledgeInfos = new ArrayList<>();

        for (Knowledge knowledge : knowledgeList) {
            System.out.println("knowledge"+knowledge.getId()+knowledge.getName());
            if (knowledge.getIsRecommended() == 1) {
                // 检查 knowledge_collection 表中是否存在该用户的 knowledgeId
                QueryWrapper<KnowledgeCollection> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("student_id", userId).eq("resources_id", knowledge.getId());
                KnowledgeCollection knowledgeCollection = knowledgeCollectionMapper.selectOne(queryWrapper);

                boolean isCollected = (knowledgeCollection != null);

                // 获取知识内容并检查是否为空
                KnowledgeContents knowledgeContents = knowledgeContentsMapper.selectById(knowledge.getId());
                String content = (knowledgeContents != null) ? knowledgeContents.getContent() : null;

                KnowledgeInfo knowledgeInfo = new KnowledgeInfo(
                        knowledge.getId(),
                        knowledge.getTag(),
                        true,
                        knowledge.getName(),
                        0,
                        knowledge.getCreatedTime(),
                        knowledge.getPictureUrl(),
                        content,
                        isCollected // 根据是否存在设置属性
                );
                knowledgeInfos.add(knowledgeInfo);
            }
        }

        return knowledgeInfos;
    }



    // 获取知识
    public List<KnowledgeInfo> getKnowledge(String search, String type, Integer pageNumber, Integer pageSize, Integer userId) {
        // 创建查询条件
        QueryWrapper<Knowledge> queryWrapper = new QueryWrapper<>();
        if (search != null && !search.isEmpty()) {
            queryWrapper.like("name", search);
        }
        if ("推荐".equals(type)) {
            queryWrapper.eq("is_recommended", 1);
        } else if (type != null && !type.isEmpty()) {
            queryWrapper.eq("tag", type);
        }

        // 从数据库中获取符合条件的知识信息
        List<Knowledge> knowledgeList = knowledgeMapper.selectList(queryWrapper);

        // 将 Knowledge 转换为 KnowledgeInfo
        List<KnowledgeInfo> knowledgeInfos = new ArrayList<>();
        for (Knowledge knowledge : knowledgeList) {
            // 检查 knowledge_collection 表中是否存在该用户的 knowledgeId
            QueryWrapper<KnowledgeCollection> collectionQueryWrapper = new QueryWrapper<>();
            collectionQueryWrapper.eq("student_id", userId).eq("resources_id", knowledge.getId());
            KnowledgeCollection knowledgeCollection = knowledgeCollectionMapper.selectOne(collectionQueryWrapper);

            boolean isCollected = (knowledgeCollection != null);
            Boolean ifRecommend = knowledge.getIsRecommended() == 1;

            // 获取知识内容并检查是否为空
            KnowledgeContents knowledgeContents = knowledgeContentsMapper.selectById(knowledge.getId());
            String content = (knowledgeContents != null) ? knowledgeContents.getContent() : null;

            knowledgeInfos.add(new KnowledgeInfo(
                    knowledge.getId(),
                    knowledge.getTag(),
                    ifRecommend,
                    knowledge.getName(),
                    null,
                    knowledge.getCreatedTime(),
                    knowledge.getPictureUrl(),
                    content,
                    isCollected // 根据是否存在设置属性
            ));
        }

        // 分页处理
        int start = (pageNumber - 1) * pageSize;
        int end = Math.min(start + pageSize, knowledgeInfos.size());

        // 检查索引有效性
        if (start >= knowledgeInfos.size()) {
            // 如果结果为空或起始索引超出范围，返回空列表
            return new ArrayList<>();
        }

        List<KnowledgeInfo> pagedResults = knowledgeInfos.subList(start, end);

        return pagedResults;
    }




    // 获取首页视频展示
    public List<VideoListInfo> getVideoShow() {
        // 从数据库中获取所有视频展示数据
        List<Video> videoList = videoMapper.selectList(null);

        // 将 Video 转换为 VideoListInfo
        List<VideoListInfo> videoInfos = new ArrayList<>();
        for (Video video : videoList) {
            if (video.getIsRecommended() == 1) {
                System.out.println("获取的VideoId" + video.getId());
                System.out.println("获取的 create_time: " + video.getCreatedTime());

                // 检查 video_collection 表中是否存在该视频ID
                QueryWrapper<VideoAndCollection> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("video_id", video.getId());
                VideoAndCollection videoAndCollection = videoAndCollectionMapper.selectOne(queryWrapper);

                if (videoAndCollection == null) {
                    // 视频ID不在 video_collection 表中，表示是单独的视频
                    videoInfos.add(new VideoListInfo(video.getId(), "单独", video.getName(), video.getCreatedTime(), video.getPictureUrl()));
                } else {
                    // 视频ID在 video_collection 表中，表示属于某个合集
                    Integer collectionId = videoAndCollection.getCollectionId();
                    Collection1 collection1 = (Collection1) collectionMapper.selectById(collectionId);
                    if (collection1 != null) {
                        videoInfos.add(new VideoListInfo(collectionId, "合集", collection1.getName(), collection1.getCreatedTime(), collection1.getPictureUrl()));
                    } else {
                        System.out.println("未找到对应的 collection 记录");
                    }
                }
            }
        }

        return videoInfos;
    }


    // 获取书法教学
    public List<VideoListInfo> getTeachingVideo(String search, Integer pageNumber, Integer pageSize) {
        // 创建查询条件
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        if (search != null && !search.isEmpty()) {
            queryWrapper.like("name", search).or().like("tag", search); // 根据视频名称和标签进行模糊搜索
        }

        // 从数据库中获取符合条件的视频展示数据
        List<Video> videoList = videoMapper.selectList(queryWrapper);

        // 将 Video 转换为 VideoListInfo
        List<VideoListInfo> videoInfos = new ArrayList<>();
        Set<Integer> addedCollections = new HashSet<>(); // 用于跟踪已添加的合集ID

        for (Video video : videoList) {
            System.out.println("获取的VideoId" + video.getId());
            System.out.println("获取的 create_time: " + video.getCreatedTime());

            // 检查 video_collection 表中是否存在该视频ID
            QueryWrapper<VideoAndCollection> videoQueryWrapper = new QueryWrapper<>();
            videoQueryWrapper.eq("video_id", video.getId());
            VideoAndCollection videoAndCollection = videoAndCollectionMapper.selectOne(videoQueryWrapper);

            if (videoAndCollection == null) {
                // 视频ID不在 video_collection 表中，表示是单独的视频
                videoInfos.add(new VideoListInfo(video.getId(), "单独", video.getName(), video.getCreatedTime(), video.getPictureUrl()));
            } else {
                // 视频ID在 video_collection 表中，表示属于某个合集
                Integer collectionId = videoAndCollection.getCollectionId();
                if (!addedCollections.contains(collectionId)) {
                    Collection1 collection1 = collectionMapper.selectById(collectionId);
                    if (collection1 != null) {
                        videoInfos.add(new VideoListInfo(collectionId, "合集", collection1.getName(), collection1.getCreatedTime(), collection1.getPictureUrl()));
                        addedCollections.add(collectionId); // 添加到已添加的合集ID集合中
                    } else {
                        System.out.println("未找到对应的 collection 记录");
                    }
                }
            }
        }

        // 分页处理
        int start = (pageNumber - 1) * pageSize;
        int end = Math.min(start + pageSize, videoInfos.size());

        // 检查索引有效性
        if (start >= videoInfos.size()) {
            // 如果结果为空或起始索引超出范围，返回空列表
            return new ArrayList<>();
        }

        List<VideoListInfo> pagedResults = videoInfos.subList(start, end);

        return pagedResults;
    }



    // 获取单独书法教学
    public VideoInfo getTeachingSingleVideo(Integer videoId, Integer userId) {
        // 根据视频ID从数据库中获取单独的视频信息
        Video video = videoMapper.selectById(videoId);

        if (video != null) {
            System.out.println("video find");

            // 根据 userId 和 videoId 检查是否收藏
            Boolean ifCollect = videoCollectionMapper.existsByUserIdAndVideoId(userId, videoId);

            // 将 Video 转换为 VideoInfo
            return new VideoInfo(video.getId(), video.getName(), video.getCreatedTime(), new ArrayList<>()/*标签*/, video.getSummary(), "60分钟", 0, ifCollect);
        } else {
            System.out.println("video cant find");

            // 如果没有找到对应的视频，返回 null 或者抛出异常
            return null;
        }
    }


    // 获取书法教学合集
    public VideoCollectionInfo getTeachingCollectionVideo(int collectionId, Integer userId) {
        // 根据 collectionId 从数据库中获取合集基础数据
        Collection1 collection = collectionMapper.selectById(collectionId);
        if (collection == null) {
            return null; // 或者抛出异常
        }

        // 从 video_collection 表中根据 collectionId 查找在这个合集中的 videoId
        QueryWrapper<VideoAndCollection> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("collection_id", collectionId);
        List<VideoAndCollection> videoAndCollections = videoAndCollectionMapper.selectList(queryWrapper);

        Boolean collectionIfCollect = false;

        // 获取子视频信息
        List<VideoInfo> sonVideos = new ArrayList<>();
        for (VideoAndCollection videoAndCollection : videoAndCollections) {
            Video video = videoMapper.selectById(videoAndCollection.getVideoId());
            if (video != null) {
                // 根据 userId 和 videoId 检查是否收藏
                Boolean ifCollect = videoCollectionMapper.existsByUserIdAndVideoId(userId, video.getId());

                if (ifCollect) {
                    collectionIfCollect = true;
                }

                sonVideos.add(new VideoInfo(video.getId(), video.getName(), video.getCreatedTime(), new ArrayList<>()/*标签*/, video.getSummary(), "60分钟"/*时长*/, 0, ifCollect));
            }
        }

        // 创建并返回 VideoCollectionInfo 对象
        return new VideoCollectionInfo(collection.getId(), new ArrayList<>()/*标签*/, collection.getSummary(), sonVideos, collectionIfCollect);
    }


    // 获取视频
    public VideoContentInfo getVideoContent(Integer videoId) {
        // 根据视频ID从数据库中获取视频信息
        Video video = videoMapper.selectById(videoId);

        if (video != null) {
            // 将 Video 转换为 VideoContentInfo
            return new VideoContentInfo(video.getId(), video.getPictureUrl(), video.getContent());
        } else {
            // 如果没有找到对应的视频，返回 null 或者抛出异常
            return null;
        }
    }


    // 获取首页竞赛展示
    public List<CompetitionInformInfo> getCompetitionShow() {
        // 从数据库中获取所有竞赛展示数据
        List<Competition> competitionList = competitionMapper.selectList(null);

        // 将 Competition 转换为 CompetitionInformInfo
        List<CompetitionInformInfo> competitionInformInfos = new ArrayList<>();
        for (Competition competition : competitionList) {
            // 从 competition_rules 表中获取规则
            QueryWrapper<CompetitionRules> rulesQueryWrapper = new QueryWrapper<>();
            rulesQueryWrapper.eq("id", competition.getId());
            CompetitionRules competitionRules = competitionRulesMapper.selectOne(rulesQueryWrapper);
            String rulesContent = competitionRules != null ? competitionRules.getQuestion() : "";

            // 从 competition_requirements 表中获取要求
            QueryWrapper<CompetitionRequirements> requirementsQueryWrapper = new QueryWrapper<>();
            requirementsQueryWrapper.eq("competition_id", competition.getId());
            CompetitionRequirements competitionRequirements = competitionRequirementsMapper.selectOne(requirementsQueryWrapper);
            String picture = competition.getPicture();
            String requirements = competitionRequirements != null ? competitionRequirements.getRequirements() : "";

            CompetitionInformInfo competitionInformInfo = new CompetitionInformInfo(
                    competition.getId(),
                    true,
                    competition.getName(),
                    competition.getCreatedTime(),
                    picture,  // 这里可以根据需要填充封面数据
                    requirements + "\n" + rulesContent  // 合并规则和要求内容
            );
            competitionInformInfos.add(competitionInformInfo);
        }

        return competitionInformInfos;
    }


    @Autowired
    AnnouncementMapper announcementMapper;
    @Autowired
    AnnouncementContentMapper announcementContentMapper;

    // 获取竞赛通知
    public List<CompetitionInformInfo> getCompetition() {
        // 从数据库中获取所有竞赛展示数据
        List<Competition> competitionList = competitionMapper.selectList(null);
        List<Announcement> announcementList = announcementMapper.selectList(null);

        // 将 Competition 和 Announcement 转换为 CompetitionInformInfo
        List<CompetitionInformInfo> competitionInformInfos = new ArrayList<>();

        // 处理 Competition 表中的数据
        for (Competition competition : competitionList) {
            // 从 competition_rules 表中获取规则
            QueryWrapper<CompetitionRules> rulesQueryWrapper = new QueryWrapper<>();
            rulesQueryWrapper.eq("id", competition.getId());
            CompetitionRules competitionRules = competitionRulesMapper.selectOne(rulesQueryWrapper);
            String rulesContent = competitionRules != null ? competitionRules.getQuestion() : "";

            // 从 competition_requirements 表中获取要求
            QueryWrapper<CompetitionRequirements> requirementsQueryWrapper = new QueryWrapper<>();
            requirementsQueryWrapper.eq("competition_id", competition.getId());
            CompetitionRequirements competitionRequirements = competitionRequirementsMapper.selectOne(requirementsQueryWrapper);
            String picture = competition.getPicture();
            String requirements = competitionRequirements != null ? competitionRequirements.getRequirements() : "";

            CompetitionInformInfo competitionInformInfo = new CompetitionInformInfo(
                    competition.getId(),
                    true,
                    competition.getName(),
                    competition.getCreatedTime(),
                    picture,  // 这里可以根据需要填充封面数据
                    requirements + "\n" + rulesContent  // 合并规则和要求内容
            );
            competitionInformInfos.add(competitionInformInfo);
        }

        // 处理 Announcement 表中的数据
        for (Announcement announcement : announcementList) {
            // 检查 type 和 target 字段
            if ("第三方竞赛公告".equals(announcement.getType()) &&
                    ("全体".equals(announcement.getTarget()) || "学生".equals(announcement.getTarget()))) {

                // 从 announcement_content 表中获取内容
                QueryWrapper<AnnouncementContent> contentQueryWrapper = new QueryWrapper<>();
                contentQueryWrapper.eq("announcement_id", announcement.getId());
                AnnouncementContent announcementContent = announcementContentMapper.selectOne(contentQueryWrapper);
                String content = announcementContent != null ? announcementContent.getMessage() : "";

                CompetitionInformInfo competitionInformInfo = new CompetitionInformInfo(
                        announcement.getId(),
                        false,
                        announcement.getName(),
                        announcement.getCreatedTime(),
                        announcement.getPictureUrl(),  // 使用公告中的封面数据
                        content  // 直接使用公告内容
                );
                competitionInformInfos.add(competitionInformInfo);
            }
        }

        return competitionInformInfos;
    }




    // 获取单个竞赛的详细信息
    public CompetitionInformInfo getCompetitionSingle(Integer competitionId) {
        // 根据竞赛ID从数据库中获取竞赛信息
        Competition competition = competitionMapper.selectById(competitionId);
        if (competition == null) {
            return null; // 竞赛未找到
        }

        // 从 competition_rules 表中获取规则
        QueryWrapper<CompetitionRules> rulesQueryWrapper = new QueryWrapper<>();
        rulesQueryWrapper.eq("id", competition.getId());
        CompetitionRules competitionRules = competitionRulesMapper.selectOne(rulesQueryWrapper);
        String rulesContent = competitionRules != null ? competitionRules.getQuestion() : "";

        // 从 competition_requirements 表中获取要求
        QueryWrapper<CompetitionRequirements> requirementsQueryWrapper = new QueryWrapper<>();
        requirementsQueryWrapper.eq("competition_id", competition.getId());
        CompetitionRequirements competitionRequirements = competitionRequirementsMapper.selectOne(requirementsQueryWrapper);
        String picture = competition.getPicture();
        String requirements = competitionRequirements != null ? competitionRequirements.getRequirements() : "";

        return new CompetitionInformInfo(
                competition.getId(),
                true,
                competition.getName(),
                competition.getCreatedTime(),
                picture,  // 这里可以根据需要填充封面数据
                requirements + "\n" + rulesContent  // 合并规则和要求内容
        );
    }


    // 获取各种协议
    public String getAgreement(String type) {
        // 创建查询条件
        QueryWrapper<Agreement> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", type); // 根据协议类型进行查询

        // 从数据库中获取符合条件的协议信息
        Agreement agreement = agreementMapper.selectOne(queryWrapper);

        if (agreement != null) {
            // 将 Agreement 数据返回
            return agreement.getContent();
        } else {
            // 如果没有找到对应的协议，返回 null 或者抛出异常
            return null;
        }
    }
}
