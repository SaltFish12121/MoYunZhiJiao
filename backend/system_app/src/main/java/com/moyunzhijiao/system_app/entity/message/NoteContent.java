package com.moyunzhijiao.system_app.entity.message;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("note_content")
public class NoteContent {
    @TableId("note_id")
    Integer noteId;
    String message;
}
