package com.sunkaisens.omc.service.impl.hss;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sunkaisens.omc.context.core.OmcServerContext;
import com.sunkaisens.omc.exception.CustomException;
import com.sunkaisens.omc.mapper.core.EntityMapper;
import com.sunkaisens.omc.mapper.core.HssMetaMapper;
import com.sunkaisens.omc.mapper.core.ModuleMapper;
import com.sunkaisens.omc.mapper.hss.GroupInfoMapper;
import com.sunkaisens.omc.mapper.hss.GroupMemberMapper;
import com.sunkaisens.omc.mapper.hss.HlrMapper;
import com.sunkaisens.omc.mapper.hss.HssGroupMapper;
import com.sunkaisens.omc.po.hss.GroupMember;
import com.sunkaisens.omc.service.hss.GroupMemberService;
import com.sunkaisens.omc.thread.cncpMsg.CncpTaskExecutor;
import com.sunkaisens.omc.thread.cncpMsg.SendCncpMsgFactory;
import com.sunkaisens.omc.util.hss.BeanToXmlUtil;
import com.sunkaisens.omc.util.hss.CreateGroupListEntry;
import com.sunkaisens.omc.util.hss.DdtGroupUtil;
import com.sunkaisens.omc.vo.core.PageBean;
/**
 *GroupMemberService接口实现类
 */
@Service
public class GroupMemberServiceImpl implements GroupMemberService {

	@Resource
	private GroupMemberMapper mapper;
	@Resource
	private HlrMapper hlrMapper;
	@Resource
	private HssGroupMapper groupMapper;
	@Resource
	private SendCncpMsgFactory sendCncpMsgFactory;
	@Resource
	private CncpTaskExecutor cncpTaskExecutor;
	@Resource
	private ModuleMapper moduleMapper;
	@Resource
	private EntityMapper entityMapper;
	@Resource
	private GroupInfoMapper groupInfoMapper;
	@Resource
	private HssMetaMapper hssMetaMapper;
	
	//private Logger logger = LoggerFactory.getLogger(getClass());
	
	private List<GroupMember> groupmemberOptionList = new ArrayList<GroupMember>();
	//分页使用
	private List<GroupMember> groupmemberOptionListTem = new ArrayList<GroupMember>();
	
	//查询组成员
	@Override
	public PageBean getGroupMemberList(Integer pageNum, Integer pageSize, GroupMember member) {
		PageBean p = null;
		groupmemberOptionList.clear();
		groupmemberOptionListTem.clear();
		List<GroupMember> groupmemberList = DdtGroupUtil.getGroupmemberListByGroupId(member.getGroupId());
		if(member.getGroupId()!=null || !"".equals(member.getGroupId())){
			for (GroupMember groupMember : groupmemberList) {
				if(member.getGroupId().equals(groupMember.getGroupId())){
					groupmemberOptionList.add(groupMember);
				}
			}
			//分页操作
			int pageCount = (groupmemberOptionList.size() + pageSize - 1) / pageSize;
			if(pageNum > pageCount){
				pageNum = 1;
			}
			int beginIndex = (pageNum - 1) * pageSize;
			int endIndex = beginIndex + pageSize;
			if(endIndex >= groupmemberOptionList.size()){
				endIndex = groupmemberOptionList.size();
			}
			Object[] arrays = groupmemberOptionList.toArray();
			for (int i = beginIndex; i < endIndex; i++) {
				groupmemberOptionListTem.add((GroupMember)arrays[i]);
			}
			p = new PageBean(pageNum, pageSize, groupmemberOptionListTem, groupmemberOptionList.size());
			return p;
		}else{
			//分页操作
			int pageCount = (groupmemberOptionList.size() + pageSize - 1) / pageSize;
			if(pageNum > pageCount){
				pageNum = 1;
			}
			int beginIndex = (pageNum - 1) * pageSize;
			int endIndex = beginIndex + pageSize;
			if(endIndex >= groupmemberOptionList.size()){
				endIndex = groupmemberOptionList.size();
			}
			Object[] arrays = groupmemberOptionList.toArray();
			for (int i = beginIndex; i < endIndex; i++) {
				groupmemberOptionListTem.add((GroupMember)arrays[i]);
			}
			p = new PageBean(pageNum, pageSize, groupmemberOptionListTem, groupmemberOptionList.size());
			return p;
		}
		
	}
	
	//添加组成员
	@Override
	public void save(GroupMember member) throws CustomException {
		String groupMemberStr = "/~~/service-group/list%5b@uri=%22sip:"+member.getGroupId()+"@test.com%22%5d/entry%5b@uri=%22sip:"+member.getMdn()+"@test.com%22%5d"; 
		String addGroupMemberUrl = OmcServerContext.getInstance().getAddGroupMemberUrl()+member.getGroupId()+groupMemberStr;
		System.err.println("发送Put请求的Url参数信息:"+addGroupMemberUrl);
		//拼接conentBody中xml字符串
		CreateGroupListEntry createGroupListEntry = new CreateGroupListEntry();
		createGroupListEntry.setUri("sip:"+member.getMdn()+"@test.com");
		createGroupListEntry.setDisplayName(member.getMdn());
		String contentBody = BeanToXmlUtil.convertBeanToXmlStr(createGroupListEntry);
		System.err.println("contentBody:"+contentBody);
		//添加组成员的时候发送消息判断该组号是否已经存在
		List<GroupMember> groupmemberList = DdtGroupUtil.getGroupmemberListByGroupId(member.getGroupId());
		System.err.println(">>>>>>>>>>添加的组成员号码是:" + member.getMdn());
		for (GroupMember groupMember : groupmemberList) {
			if(groupMember.getMdn().equals(member.getMdn())){
				throw new CustomException("组成员【" + member.getMdn() + "】已存在");
			}
		}
		if (null == hlrMapper.selectByMdn(member.getMdn())) {
			throw new CustomException("电话号码【" + member.getMdn() + "】对应的用户不存在!");
		}
		//发送PUT请求
		DdtGroupUtil.addPutGroupMemberList(member,addGroupMemberUrl,contentBody);
	}
	
	//修改组成员
	@Override
	public void update(GroupMember member) throws CustomException {
		String groupMemberStr = "/~~/service-group/list%5b@uri=%22sip:"+member.getGroupId()+"@test.com%22%5d/entry%5b@uri=%22sip:"+member.getMdn()+"@test.com%22%5d"; 
		String updateGroupMemberUrl = OmcServerContext.getInstance().getUpdateGroupMemberUrl()+member.getGroupId()+groupMemberStr;
		System.err.println("发送Put请求的Url参数信息:"+updateGroupMemberUrl);
		CreateGroupListEntry createGroupListEntry = new CreateGroupListEntry();
		createGroupListEntry.setUri("sip:"+member.getMdn()+"@test.com");
		createGroupListEntry.setDisplayName(member.getMdn());
		createGroupListEntry.setUserType(String.valueOf(member.getRole()));
		String contentBody = BeanToXmlUtil.convertBeanToXmlStr(createGroupListEntry);
		//发送Update请求
		DdtGroupUtil.updatePutGroupMemberList(member,updateGroupMemberUrl,contentBody);
	}
	
	//删除组成员
	@Override
	public void delete(String[] mdn, String[] groupId) throws CustomException {
		for (int i = 0; i < mdn.length; i++) {
			String groupMemberStr = "/~~/service-group/list%5b@uri=%22sip:"+groupId[0]+"@test.com%22%5d/entry%5b@uri=%22sip:"+mdn[i]+"@test.com%22%5d"; 
			String updateGroupMemberUrl = OmcServerContext.getInstance().getUpdateGroupMemberUrl()+groupId[i]+groupMemberStr;
			System.err.println("发送Delete请求的Url参数信息:"+updateGroupMemberUrl);
			//发送Delete请求
			DdtGroupUtil.deleteGroupMemberList(mdn[i],groupId[0],updateGroupMemberUrl);
		}
	}

	@Override
	public List<GroupMember> selectBelongGroup(String mdn) {
		List<GroupMember> belongGroups = mapper.selectBelongGroup(mdn);
		return belongGroups;
	}
}
