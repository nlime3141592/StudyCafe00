package deu.java002_02.study.provider.service;

import deu.java002_02.study.ni.INetworkModule;
import deu.java002_02.study.ni.INetworkService;
import deu.java002_02.study.provider.gui.BlacklistView;

public class TestBlacklistAddService extends ProviderService implements INetworkService
{
	private INetworkModule m_netModule;
	
	private BlacklistView m_view;
	private int m_uuid;
	
	public TestBlacklistAddService(BlacklistView _view, int _uuid)
	{
		m_view = _view;
		m_uuid = _uuid;
	}

	@Override
	public void bindNetworkModule(INetworkModule _netModule)
	{
		m_netModule = _netModule;
	}

	@Override
	public boolean tryExecuteService()
	{
		// TEST: 테스트 코드입니다.
		System.out.println("add service executed.");
		
		try {
			Thread.sleep(1000);
			m_view.addRow(m_uuid, "fname", "ftime");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		System.out.println("add service ended.");

		return true;
	}

}
