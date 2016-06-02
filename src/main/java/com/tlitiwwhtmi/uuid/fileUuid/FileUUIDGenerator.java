package com.tlitiwwhtmi.uuid.fileUuid;

import com.tlitiwwhtmi.uuid.UUIDGenerator;

import java.util.UUID;

/**
 * Created by saver on 16/5/31.
 */
public class FileUUIDGenerator extends UUIDGenerator{

    @Override
    public String generateUUID() {
        String s = UUID.randomUUID().toString();
        //去掉“-”符号
        return s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24);
    }
}
