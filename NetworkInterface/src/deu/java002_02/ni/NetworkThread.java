package deu.java002_02.ni;

import java.net.Socket;

public abstract class NetworkThread implements Runnable, INetworkService
{
	private StandardNetworkModule m_netModule;

	private boolean m_started;
	private Thread m_thread;

	public NetworkThread(Socket _socket)
	{
		m_netModule = new StandardNetworkModule(_socket);

		m_started = false;
		m_thread = new Thread(this);
	}

	public abstract void onService(INetworkModule _netModule);

	@Override
	public final void run()
	{
		while(this.getThreadState() == ThreadState.RUNNING)
			this.onService(m_netModule);
	}

	public void start()
	{
		if(!m_started)
		{
			m_started = true;
			m_thread.start();
		}
	}

	public void stop()
	{
		if(m_started && m_thread != null)
		{
			m_netModule.stop();
			m_thread = null;
			System.out.println("클라이언트 서비스를 종료합니다.");
		}
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
	
	protected final INetworkModule getNetworkModule()
	{
		return m_netModule;
	}
}
