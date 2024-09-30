package com.moyunzhijiao.system_app.entity.message;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("student_note_receive")
public class StudentNoteReceive {
    @TableId("note_id")
    Integer noteId;
    @TableField("student_id")
    Integer studentId;
    Integer reviewed;
}
