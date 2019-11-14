package jsonEntities;

import java.io.Serializable;

import javax.json.bind.annotation.JsonbPropertyOrder;

@JsonbPropertyOrder({"current", "newPassword", "confirm"})
public class PasswordJson implements Serializable{

	private static final long serialVersionUID = 1L;
	private String current;
	private String newPassword;
	private String confirm;

	public PasswordJson() {
		// TODO Auto-generated constructor stub
	}
	
	public PasswordJson(Long id, String current, String newPassword, String confirm) {
		super();
		this.current = current;
		this.newPassword = newPassword;
		this.confirm = confirm;
	}
	
	public String getCurrent() {
		return current;
	}
	public void setCurrent(String current) {
		this.current = current;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getConfirm() {
		return confirm;
	}
	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Current: " + this.current);
		sb.append("\nNew password: " + this.newPassword);
		sb.append("\nConfirm: " + this.confirm);
		return sb.toString().trim();
	}

}
