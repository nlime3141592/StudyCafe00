package deu.java002_02.study.provider.gui;

import javax.swing.JOptionPane;

import deu.java002_02.study.main.Service;
import deu.java002_02.study.provider.main.ProviderMain;
import deu.java002_02.study.provider.service.TestBlacklistAddService;

public class BlacklistAddView
{
	private BlacklistView m_blacklistView;

	public BlacklistAddView(BlacklistView _blacklistView)
	{
		m_blacklistView = _blacklistView;
	}

	public void show()
	{
		String uuidString = JOptionPane.showInputDialog(null, "블랙리스트에 추가할 이용자의 고유 번호를 입력하세요.");
		
		if(uuidString == null)
			return;

		System.out.println("ad view : [" + uuidString + "]");

		try
		{
			int uuid = Integer.parseInt(uuidString.trim());
			
			// TODO: 서비스 구현이 끝나면 주석을 해제합니다.
			Service service = new TestBlacklistAddService(m_blacklistView, uuid);
			ProviderMain.getProviderThread().registerEventService(service);
			
			while(!service.isServiceEnded())
				continue;

			if(service.isExecutionSuccess())
				JOptionPane.showMessageDialog(null, "이용자를 블랙리스트에 추가했습니다.");
			else
				JOptionPane.showMessageDialog(null, "이용자를 블랙리스트에 추가할 수 없습니다.");
		}
		catch(Exception _ex)
		{

		}
	}
}
