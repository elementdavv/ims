package model;

/**
 * TContact entity. @author MyEclipse Persistence Tools
 */

public class TContact implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 2969935776675746464L;
	private Integer id;
	private Integer memberId;
	private Integer contactId;
	private Integer contacttimes;
	private String lastcontactdate;

	// Constructors

	/** default constructor */
	public TContact() {
	}

	/** minimal constructor */
	public TContact(Integer memberId, Integer contactId, Integer contacttimes) {
		this.memberId = memberId;
		this.contactId = contactId;
		this.contacttimes = contacttimes;
	}

	/** full constructor */
	public TContact(Integer memberId, Integer contactId, Integer contacttimes,
			String lastcontactdate) {
		this.memberId = memberId;
		this.contactId = contactId;
		this.contacttimes = contacttimes;
		this.lastcontactdate = lastcontactdate;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMemberId() {
		return this.memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public Integer getContactId() {
		return this.contactId;
	}

	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}

	public Integer getContacttimes() {
		return this.contacttimes;
	}

	public void setContacttimes(Integer contacttimes) {
		this.contacttimes = contacttimes;
	}

	public String getLastcontactdate() {
		return this.lastcontactdate;
	}

	public void setLastcontactdate(String lastcontactdate) {
		this.lastcontactdate = lastcontactdate;
	}

}