package deu.java002_02.study.provider.gui;

import javax.swing.JFrame;

public abstract class View extends JFrame
{
	public View(String _title)
	{
		super.setTitle(_title);
		super.setResizable(false);
	}

	public abstract void showView();
	public abstract void hideView();
}
