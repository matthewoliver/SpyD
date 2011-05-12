/**
* This file is part of Spyd.
*
* Spyd is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* Spyd is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with Network Manifest Checker.  If not, see <http://www.gnu.org/licenses/>.
* 
* @author Matthew Oliver
*/
package au.gov.naa.digipres.spyd.dao.hibernate;

import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import au.gov.naa.digipres.spyd.dao.ItemRecordDAO;
import au.gov.naa.digipres.spyd.model.ItemRecord;

public class HibernateItemRecordDAO implements ItemRecordDAO {
	private Logger logger = Logger.getLogger(this.getClass().getName());

	@Override
	public ItemRecord getItemRecord(String id) {
		Session session = HibernateUtil.getSession();
		Criteria crit = session.createCriteria(ItemRecord.class);
		crit.add(Restrictions.eq("id", id));
		List results = crit.list();
		if ((results == null) || results.size() == 0) {
			return null;
		}
		ItemRecord item = (ItemRecord) results.get(0);
		session.flush();
		session.clear();

		return item;
	}

	@Override
	public List<ItemRecord> getAllItemRecords() {
		Session session = HibernateUtil.getSession();
		Criteria crit = session.createCriteria(ItemRecord.class);
		List<ItemRecord> itemRecords = crit.list();
		session.flush();
		session.clear();

		return itemRecords;
	}

	@Override
	public List<ItemRecord> getItemRecordsBySubDir(String subdir) {
		Session session = HibernateUtil.getSession();
		Criteria crit = session.createCriteria(ItemRecord.class);

		//TODO add a restriction to the criteria so it actually gets filtered. 
		List<ItemRecord> itemRecords = crit.list();
		session.flush();
		session.clear();

		return itemRecords;
	}

	@Override
	public void saveItemRecord(ItemRecord itemRecord) {
		logger.fine("Persisting Item Record");
		Session session = HibernateUtil.getSession();
		session.saveOrUpdate(itemRecord);
		session.flush();
		session.clear();
		logger.fine("Item Record Persisted.");
	}

	@Override
	public void removeItemRecord(ItemRecord itemRecord) {
		logger.fine("Removing Item Record");
		Session session = HibernateUtil.getSession();
		session.delete(itemRecord);
		session.flush();
		session.clear();
		logger.fine("Item Record Removed.");
	}

}
