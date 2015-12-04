package org.ranestar.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class User {
	
	private long id;
	private String user_id;
	private String user_pw;
	private String user_nm;
	private String user_tel;
	private String user_email;
	private Date reg_date;

}
