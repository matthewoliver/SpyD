package au.gov.naa.digipres.spyd.module.checksum;

import java.util.Iterator;
import java.util.logging.Logger;

import au.gov.naa.digipres.spyd.model.ItemRecord;

public class CheckerThread extends Thread {

	public static final int WORKING_STATE = 0;
	public static final int WAITING_STATE = 1;
	public static final int DIRTY_STATE = 2;
	public static final int IDLE_STATE = 4;

	// Using ints instead of boolean so we can support more thread types.
	public static final int PRIVATE_THREAD = 0;
	public static final int WORKER_THREAD = 1;

	// event state for the thread to use.. to know what to do.
	public static final int STOP_EVENT_STATE = 0;
	public static final int PAUSE_EVENT_STATE = 1;
	public static final int RUNNING_EVENT_STATE = 2;
	public static final int MESSAGE_EVENT_STATE = 3;

	protected int eventState;
	protected boolean changeState;

	protected int checkerState;

	protected ChecksumModule checksumModule;
	protected Logger logger = checksumModule.getModuleManager().getPluginManager().getCommunicationManager().getClassLogger(this);

	// Specifies whether the thread is a worker thread (Used in the normal rolling checksum checking behaviour)
	// or a private thread, used by the checksum module to do other odd jobs (e.g. re-check failed checksums) 
	// in essence a private thread alerts the module once it has completed, so it can be managed.
	protected int threadType;

	protected int threadId;
	protected boolean workCompleted;

	// The itemRecords to check
	Iterator<Object> itemRecordsIterator;

	public CheckerThread(ChecksumModule checksumModule, int threadId) {
		this.checksumModule = checksumModule;
		this.threadId = threadId;

		checkerState = IDLE_STATE;
		changeState = false;
		workCompleted = false;
	}

	public synchronized int getCheckerState() {
		return checkerState;
	}

	public int getThreadType() {
		return threadType;
	}

	public void setThreadType(int threadType) {
		this.threadType = threadType;
	}

	public synchronized void stopThread() {
		eventState = STOP_EVENT_STATE;
		changeState = true;
		notify();
	}

	public synchronized void pauseThread() {
		eventState = PAUSE_EVENT_STATE;
		checkerState = PAUSE_EVENT_STATE;
		changeState = true;
		notify();
	}

	public synchronized void resumeThread() {
		eventState = RUNNING_EVENT_STATE;
		checkerState = WORKING_STATE;
		changeState = true;
		notify();
	}

	protected synchronized void finishWork() {
		eventState = PAUSE_EVENT_STATE;
		checkerState = IDLE_STATE;
		changeState = true;
		notify();

		// if the thread is a private thread (A thread that is not one of the main rolling checksum checking threads) but
		// doing it's own checking job then notify the Checksum module to let it know it's complete.
		if (getThreadType() == PRIVATE_THREAD) {
			// notify the module to tell it the job is completed.
			checksumModule.threadCompletedEvent(this.threadId);
		}
	}

	@Override
	public synchronized void run() {
		// This loop is only ended when the when the stop event has been received.
		while (eventState != STOP_EVENT_STATE) {

			switch (eventState) {

			case PAUSE_EVENT_STATE:
				// the thread is told to pause so move it into a waiting state.
				try {
					wait();
				} catch (InterruptedException ex) {
					continue;
				}

			case RUNNING_EVENT_STATE:
				// thread running so lets continue by checking the next
				if (itemRecordsIterator == null) {
					// some work hasn't been given to this thread yet so wait. 
					try {
						wait();
					} catch (InterruptedException e) {
						continue;
					}
				}

				if (workCompleted) {
					// put itself in the paused state but mark as idle as is it waiting for other jobs.
					// cant use the pauseThread() method as it puts it into a paused state, so the checksum module
					// wont know it's ready for a new job.
					finishWork();

				}

				// Lets checksum the next item.
				if (itemRecordsIterator.hasNext()) {
					ItemRecord rec = (ItemRecord) itemRecordsIterator.next();

					//TODO log the checksumming, checksum, updated record, alert (log) 

				} else {
					workCompleted = true;
				}
			}

		}
	}

	public synchronized void setWork(Iterator<Object> itemRecordsToCheckIterator) throws CheckerThreadException {
		this.itemRecordsIterator = itemRecordsToCheckIterator;

		workCompleted = false;
		resumeThread();
	}

}
