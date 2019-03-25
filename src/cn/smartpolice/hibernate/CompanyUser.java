package cn.smartpolice.hibernate;

public class CompanyUser implements java.io.Serializable{

	private int userId;
	private CompanyInfo companyId;
	private String userName;
	private String password;
	private String name;
	private String email;
	private String number;
	private String position;  //职位
	private String state;  //0未审核1通过审核-1未通过2冻结
	private String question1;
	private String answer1;
	private String question2;
	private String answer2;
	
	public String getQuestion1() {
		return question1;
	}
	public void setQuestion1(String question1) {
		this.question1 = question1;
	}
	public String getAnswer1() {
		return answer1;
	}
	public void setAnswer1(String answer1) {
		this.answer1 = answer1;
	}
	public String getQuestion2() {
		return question2;
	}
	public void setQuestion2(String question2) {
		this.question2 = question2;
	}
	public String getAnswer2() {
		return answer2;
	}
	public void setAnswer2(String answer2) {
		this.answer2 = answer2;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}

	public CompanyInfo getCompanyId() {
		return companyId;
	}
	public void setCompanyId(CompanyInfo companyId) {
		this.companyId = companyId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	@Override
	public String toString() {
		return "CompanyUser [userId=" + userId + ",  userName=" + userName + ", password="
				+ password + ", name=" + name + ", email=" + email + ", number=" + number + ", position=" + position
				+ ", state=" + state + ", question1=" + question1 + ", answer1=" + answer1 + ", question2=" + question2
				+ ", answer2=" + answer2 + "]";
	}
}
