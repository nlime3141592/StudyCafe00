package deu.java002_02.study.provider.gui;

import javax.swing.JOptionPane;

import deu.java002_02.study.main.Service;
import deu.java002_02.study.provider.main.ProviderMain;
import deu.java002_02.study.provider.service.TestBlacklistRemoveService;

public class BlacklistRemoveView
{
	private BlacklistView m_blacklistView;

	public BlacklistRemoveView(BlacklistView _blacklistView)
	{
		m_blacklistView = _blacklistView;
	}
	
	public void show()
	{
		String uuidString = JOptionPane.showInputDialog(null, "������Ʈ���� ������ ������� ���� ��ȣ�� �Է��ϼ���.");
		
		if(uuidString == null)
			return;

		System.out.println("rm view : [" + uuidString + "]");

		try
		{
			int uuid = Integer.parseInt(uuidString.trim());
			
			// TODO: ���� ������ ������ �ּ��� �����մϴ�.
			// ProviderMain.getProviderThread().registerEventService(new BlacklistRemoveService(m_blacklistView, uuid));
			
			Service service = new TestBlacklistRemoveService(m_blacklistView, uuid);
			ProviderMain.getProviderThread().registerEventService(service);

			while(!service.isServiceEnded())
				continue;

			if(service.isExecutionSuccess())
				JOptionPane.showMessageDialog(null, "�̿��ڸ� ������Ʈ���� �����߽��ϴ�.");
			else
				JOptionPane.showMessageDialog(null, "�̿��ڸ� ������Ʈ���� ������ �� �����ϴ�.");
		}
		catch(Exception _ex)
		{

		}
	}
}
