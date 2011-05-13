package au.gov.naa.digipres.spyd.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import au.gov.naa.digipres.spyd.core.Constants;

/**
 * A Class to represent an item record. 
 * This class is persisted to the
 * RDBMS table item_record.
 * 
 */
@Entity
@Table(name = "item_record")
public class ItemRecord {
	protected String id;
	protected String checksum;
	protected String checksumAlgorithm;
	protected Date lastChecked;
	protected Date dateCreated;
	protected int itemStatus;

	public ItemRecord() {
		setChecksumAlgorithm(Constants.DEFAULT_CHECKSUM_ALGORITHM);
		setChecksum("");
		setDateCreated(new Date());
		setItemStatus(Constants.STATUS_UNCHECKED);
	}

	@Id
	@Column(name = "id")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "checksum")
	public String getChecksum() {
		return checksum;
	}

	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}

	@Column(name = "checksum_algorithm")
	public String getChecksumAlgorithm() {
		return checksumAlgorithm;
	}

	public void setChecksumAlgorithm(String checksumAlgorithm) {
		this.checksumAlgorithm = checksumAlgorithm;
	}

	@Column(name = "last_checked")
	public Date getLastChecked() {
		return lastChecked;
	}

	public void setLastChecked(Date lastChecked) {
		this.lastChecked = lastChecked;
	}

	@Column(name = "date_created")
	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	@Column(name = "item_status")
	public int getItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(int itemStatus) {
		this.itemStatus = itemStatus;
	}

}
