package com.example.shadow.service;

import com.example.shadow.entity.Msg;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.shadow.entity.VO.ContactVO;
import com.example.shadow.entity.VO.MsgVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author shAdow
 * @since 2021-10-20
 */
public interface MsgService extends IService<Msg> {

    List<ContactVO> getContacts(Integer uid, Integer pageNo, Integer pageSize);

    List<MsgVO> getMsgWithUid(Integer uid, Integer contactId, Integer pageNo, Integer pageSize);

    void savePlainMsg(Integer uid, Integer contactId, String content);

    void saveFileMsg(Integer uid, Integer contactId, MultipartFile file);

    void saveDealMsg(Integer uid, Integer projectId, String content);

    void saveAssignMsg(Integer projectId);

}
