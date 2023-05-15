package Model;

public class Account {
    private String accountId;
    private String accountType;
    private String username;
    
	private String customerId;
    private String staffId;
    private boolean acc_status;
    private String acc_password;

    public boolean isAcc_status() {
		return acc_status;
	}
    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setAcc_status(boolean acc_status) {
		this.acc_status = acc_status;
	}

	public String getAcc_password() {
		return acc_password;
	}

	public void setAcc_password(String acc_password) {
		this.acc_password = acc_password;
	}
	public void checkId()
	{
        if(customerId == "")
		{
			this.customerId = null;
		}
        else if(staffId == "")
		{
			this.staffId = null;
		}
	}

	public Account(String accountId,String userName , String accountType, String staffId, String customerId,boolean Status,String Password) {
        this.accountId = accountId;
        this.username = userName;
        this.accountType = accountType;
        this.staffId = staffId;
        this.customerId = customerId;
        this.acc_status= Status;
        this.acc_password=Password;
        if(customerId == "")
		{
			this.customerId = null;
		}
		if(staffId == "")
		{
			this.staffId = null;
		}
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }
}
