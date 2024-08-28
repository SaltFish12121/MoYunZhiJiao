package com.moyunzhijiao.system_app.service.collection;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_app.controller.dto.CollectDTO;
import com.moyunzhijiao.system_app.controller.dto.fonted.video.VideoListInfo;
import com.moyunzhijiao.system_app.entity.Video;
import com.moyunzhijiao.system_app.entity.collection.Collection1;
import com.moyunzhijiao.system_app.entity.collection.VideoAndCollection;
import com.moyunzhijiao.system_app.entity.collection.VideoCollection;
import com.moyunzhijiao.system_app.mapper.VideoMapper;
import com.moyunzhijiao.system_app.mapper.collection.VideoAndCollectionMapper;
import com.moyunzhijiao.system_app.mapper.collection.VideoCollectionMapper;
import com.moyunzhijiao.system_app.mapper.collection.CollectionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class VideoCollectionService extends ServiceImpl<VideoCollectionMapper, VideoCollection> {
    @Autowired
    VideoCollectionMapper videoCollectionMapper;
    @Autowired
    VideoAndCollectionMapper videoAndCollectionMapper;
    @Autowired
    CollectionMapper collectionMapper;

    @Autowired
    VideoMapper videoMapper;

    // 获取用户视频收藏
    public List<VideoListInfo> getVideoCollect(Integer userId, Integer pageNumber, Integer pageSize) {
        // 通过用户ID找到收藏表中的 videoId 和 type
        QueryWrapper<VideoCollection> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_id", userId);
        List<VideoCollection> collections = videoCollectionMapper.selectList(queryWrapper);

        System.out.println("获取的数据" + collections);

        List<VideoListInfo> results = new ArrayList<>();
        Set<Integer> processedVideoIds = new HashSet<>(); // 用于跟踪已处理的videoId
        Set<Integer> processedCollectionIds = new HashSet<>(); // 用于跟踪已处理的collectionId

        for (VideoCollection collection : collections) {
            Integer videoId = collection.getVideoId();
            if (!processedVideoIds.contains(videoId)) {
                System.out.println("获取的VideoId" + videoId);
                VideoListInfo result = getVideoByID(videoId);
                if (result != null) {
                    if ("合集".equals(result.getType())) {
                        if (!processedCollectionIds.contains(result.getId())) {
                            results.add(result);
                            processedCollectionIds.add(result.getId()); // 添加到已处理合集集合中
                        }
                    } else {
                        results.add(result);
                    }
                    processedVideoIds.add(videoId); // 添加到已处理视频集合中
                }
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

        List<VideoListInfo> pagedResults = results.subList(start, end);

        return pagedResults;
    }

    private VideoListInfo getVideoByID(Integer videoId) {
        System.out.println("获取的VideoId" + videoId);
        return getVideo(videoId);
    }

    private VideoListInfo getVideo(Integer videoId) {
        System.out.println("获取的VideoId" + videoId);
        if (videoId != null) {
            Video video = videoMapper.selectById(videoId);
            if (video != null) {
                System.out.println("获取的 create_time: " + video.getCreatedTime());

                // 检查 video_collection 表中是否存在该视频ID
                QueryWrapper<VideoAndCollection> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("video_id", videoId);
                VideoAndCollection videoAndCollection = videoAndCollectionMapper.selectOne(queryWrapper);

                if (videoAndCollection == null) {
                    // 视频ID不在 video_collection 表中，表示是单独的视频
                    return new VideoListInfo(videoId, "单独", video.getName(), video.getCreatedTime(), video.getPictureUrl());
                } else {
                    // 视频ID在 video_collection 表中，表示属于某个合集
                    Integer collectionId = videoAndCollection.getCollectionId();
                    Collection1 collection1 = (Collection1) collectionMapper.selectById(collectionId);
                    if (collection1 != null) {
                        return new VideoListInfo(collectionId, "合集", collection1.getName(), collection1.getCreatedTime(), collection1.getPictureUrl());
                    } else {
                        System.out.println("未找到对应的 collection 记录");
                    }
                }
            } else {
                System.out.println("未找到对应的 video 记录");
            }
        } else {
            System.out.println("未找到对应的 videoId");
        }
        return null;
    }


    // 处理单个视频的收藏
    public boolean handleCollect(CollectDTO collectDTO) {
        if (collectDTO.getNewCollect()) {
            return addCollect(collectDTO);
        } else {
            return removeCollect(collectDTO);
        }
    }


    // 添加单个视频
    public boolean addCollect(CollectDTO collectDTO) {
        // 检查是否已经存在
        Long count = videoCollectionMapper.selectCount(
                new QueryWrapper<VideoCollection>()
                        .eq("student_id", collectDTO.getUserId())
                        .eq("video_id", collectDTO.getCollectId())
        );
        if (count > 0) {
            return false; // 已经存在，不重复添加
        }

        VideoCollection videoCollection = new VideoCollection();
        videoCollection.setStudentId(collectDTO.getUserId());
        videoCollection.setVideoId(collectDTO.getCollectId());
        return videoCollectionMapper.insert(videoCollection) > 0;
    }


    // 删除单个视频
    public boolean removeCollect(CollectDTO collectDTO) {
        return videoCollectionMapper.delete(
                new QueryWrapper<VideoCollection>()
                        .eq("student_id", collectDTO.getUserId())
                        .eq("video_id", collectDTO.getCollectId())
        ) > 0;
    }


    // 处理视频合集的收藏
    public boolean handleCollectionCollect(CollectDTO collectDTO) {
        System.out.println("getNewCollect"+collectDTO.getNewCollect());
        if (collectDTO.getNewCollect()) {
            return addCollectionCollect(collectDTO);
        } else {
            return removeCollectionCollect(collectDTO);
        }
    }


    // 添加合集
    public boolean addCollectionCollect(CollectDTO collectDTO) {
        List<VideoAndCollection> videoAndCollections = videoAndCollectionMapper.selectList(
                new QueryWrapper<VideoAndCollection>().eq("collection_id", collectDTO.getCollectId())
        );
        for (VideoAndCollection videoAndCollection : videoAndCollections) {
            System.out.println("videoAndCollection"+videoAndCollection.getCollectionId()+videoAndCollection.getVideoId());
            // 检查是否已经存在
            Long count = videoCollectionMapper.selectCount(
                    new QueryWrapper<VideoCollection>()
                            .eq("student_id", collectDTO.getUserId())
                            .eq("video_id", videoAndCollection.getVideoId())
            );
            if (count == 0) {
                VideoCollection videoCollection = new VideoCollection();
                videoCollection.setStudentId(collectDTO.getUserId());
                videoCollection.setVideoId(videoAndCollection.getVideoId());
                videoCollectionMapper.insert(videoCollection);
            }
        }
        return true;
    }


    //删除合集
    public boolean removeCollectionCollect(CollectDTO collectDTO) {
        List<VideoAndCollection> videoAndCollections = videoAndCollectionMapper.selectList(
                new QueryWrapper<VideoAndCollection>().eq("collection_id", collectDTO.getCollectId())
        );
        for (VideoAndCollection videoAndCollection : videoAndCollections) {
            videoCollectionMapper.delete(
                    new QueryWrapper<VideoCollection>()
                            .eq("student_id", collectDTO.getUserId())
                            .eq("video_id", videoAndCollection.getVideoId())
            );
        }
        return true;
    }
}

