package deu.java002_02.study.provider.gui;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

public abstract class View extends JFrame implements WindowListener
{
	public View(String _title)
	{
		super.setTitle(_title);
		super.setResizable(false);
		super.addWindowListener(this);
	}

	@Override
	public void windowOpened(WindowEvent e) { }

	// NOTE: Frame 타이틀 바의 X 버튼을 누르면 이 함수가 실행됩니다.
	@Override
	public void windowClosing(WindowEvent e) { }

	// NOTE: Window.dispose() 함수가 호출되면 이 함수가 실행됩니다.
	@Override
	public void windowClosed(WindowEvent e)
	{
		// NOTE: 참조 카운터를 감소시키는 로직 적용
		/*
		Model model = this.getModel();

		if(model != null)
			model.unregisterView(this);
		*/
	}

	@Override
	public void windowIconified(WindowEvent e) { }

	@Override
	public void windowDeiconified(WindowEvent e) { }

	@Override
	public void windowActivated(WindowEvent e) { }

	@Override
	public void windowDeactivated(WindowEvent e) { }
	
	public abstract void showView();
	public abstract void hideView();
}
