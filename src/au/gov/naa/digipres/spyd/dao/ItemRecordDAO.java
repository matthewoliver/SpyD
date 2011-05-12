package au.gov.naa.digipres.spyd.dao;

import java.util.List;

import au.gov.naa.digipres.spyd.model.ItemRecord;

public interface ItemRecordDAO {
	public ItemRecord getItemRecord(String id);

	public List<ItemRecord> getAllItemRecords();

	public List<ItemRecord> getItemRecordsBySubDir(String subdir);

	public void saveItemRecord(ItemRecord itemRecord);

	public void removeItemRecord(ItemRecord itemRecord);
}
