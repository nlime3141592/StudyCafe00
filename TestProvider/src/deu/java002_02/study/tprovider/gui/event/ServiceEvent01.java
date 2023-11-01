package deu.java002_02.study.tprovider.gui.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import deu.java002_02.study.tprovider.gui.TestGui;
import deu.java002_02.study.tprovider.main.TProviderMain;
import deu.java002_02.study.tprovider.service.TService01;

public class ServiceEvent01 implements ActionListener //, INetworkService
{
	private TestGui m_gui;

	public ServiceEvent01(TestGui _gui)
	{
		m_gui = _gui;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		TProviderMain.thread.enqueueService(new TService01(m_gui));
	}
}
