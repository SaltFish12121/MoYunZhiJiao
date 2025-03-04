package com.moyunzhijiao.system_backend.service.resource;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_backend.common.Constants;
import com.moyunzhijiao.system_backend.controller.dto.resource.ArticleDTO;
import com.moyunzhijiao.system_backend.entiy.resource.Article;
import com.moyunzhijiao.system_backend.entiy.resource.ArticleContents;
import com.moyunzhijiao.system_backend.exception.ServiceException;
import com.moyunzhijiao.system_backend.mapper.resource.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class ArticleService extends ServiceImpl<ArticleMapper, Article> {
    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    ArticleContentsService articleContentsService;

    @Transactional
    public void addArticle(MultipartFile image, String htmlContent, Article article) {
        File projectRoot = new File(System.getProperty("user.dir"));
        String fileName = image.getOriginalFilename();
        String filePath = projectRoot.getParentFile().getParent()+"/frontend/system_frontend/public/images/articlePicture/" + fileName;
        String tempPath = "/images/articlePicture/"+fileName;
        File dest = new File(filePath);
        try {
            image.transferTo(dest);
            article.setPictureUrl(tempPath);
            articleMapper.insert(article);
            ArticleContents articleContents = new ArticleContents();
            articleContents.setContent(htmlContent);
            articleContents.setId(article.getId());
            articleContentsService.addArticleContents(articleContents);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceException(Constants.CODE_401,"文件上传失败");
        }
    }

    public IPage<ArticleDTO> selectPage(IPage<ArticleDTO> page, String str, Integer secondTypeId, boolean isRecommended) {
        page = articleMapper.selectPage(page,str,secondTypeId,isRecommended);
        Integer count = articleMapper.countPage(page,str,secondTypeId,isRecommended);
        if (count == null) {
            count = 0; // 确保count不为null
        }
        page.setTotal(count);
        return page;
    }

    public void deleteArticle(String id) {
        articleMapper.deleteById(id);
    }

    public void updateArticle(ArticleDTO articleDTO) {
        Article article = convertToEntity(articleDTO);
        ArticleContents articleContents = new ArticleContents();
        articleContents.setId(articleDTO.getId());
        articleContents.setContent(articleDTO.getContent());
        articleContentsService.updateById(articleContents);
        updateById(article);
    }
    private Article convertToEntity(ArticleDTO articleDTO){
        Article article = new Article();
        BeanUtil.copyProperties(articleDTO,article);
        return article;
    }
}
