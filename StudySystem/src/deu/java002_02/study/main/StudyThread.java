package deu.java002_02.study.main;

public abstract class StudyThread implements Runnable
{
	private boolean m_started;
	private Thread m_thread;
	
	public StudyThread()
	{
		m_started = false;
		m_thread = new Thread(this);
	}

	@Override
	public abstract void run();

	public boolean start()
	{
		if(m_started)
			return false;

		m_started = true;
		m_thread.start();
		return true;
	}

	public boolean stop()
	{
		if(!m_started || m_thread == null)
			return false;

		m_thread = null;
		return false;
	}

	public final boolean isRun()
	{
		return this.getThreadState() == ThreadState.RUNNING;
	}

	public final ThreadState getThreadState()
	{
		if(!m_started)
			return ThreadState.READY;
		else if(m_thread != null)
			return ThreadState.RUNNING;
		else
			return ThreadState.END;
	}
}
