package deu.java002_02.study.tprovider.gui.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import deu.java002_02.study.tprovider.gui.TestGui;
import deu.java002_02.study.tprovider.main.TProviderMain;
import deu.java002_02.study.tprovider.service.TService02;

public class ServiceEvent02 implements ActionListener
{
	private TestGui m_gui;

	public ServiceEvent02(TestGui _gui)
	{
		m_gui = _gui;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		TProviderMain.thread.enqueueService(new TService02(m_gui));
	}
}
