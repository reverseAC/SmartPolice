package cn.smartpolice.hibernate;

import java.util.Date;

public class CommentInfo {

	private int commentId; 
	private CompanyUser userId;  //反馈用户
	private int type;  //反馈类型0建议1问题2投诉3表扬
	private Date date;  //反馈时间
	private String title;  //主题
	private String content;  //内容
	private String handle;  //0未处理1已处理
	private int replyid;  //处理人
	private Date replydate;  //处理时间
	private String reply;  //处理意见
	
	public int getCommentId() {
		return commentId;
	}
	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public CompanyUser getUserId() {
		return userId;
	}
	public void setUserId(CompanyUser userId) {
		this.userId = userId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getHandle() {
		return handle;
	}
	public void setHandle(String handle) {
		this.handle = handle;
	}
	public int getReplyid() {
		return replyid;
	}
	public void setReplyid(int replyid) {
		this.replyid = replyid;
	}
	public Date getReplydate() {
		return replydate;
	}
	public void setReplydate(Date replydate) {
		this.replydate = replydate;
	}
	public String getReply() {
		return reply;
	}
	public void setReply(String reply) {
		this.reply = reply;
	}
}
