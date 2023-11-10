package deu.java002_02.study.provider.gui;

import java.awt.Insets;

public class SeatReserveView extends View
{
	private int m_width;
	private int m_height;

	public SeatReserveView(String _title)
	{
		super(_title);

		m_width = 480;
		m_height = 640;
	}

	@Override
	public void showView()
	{
		super.setVisible(true);

		Insets inset = super.getInsets();
		int l = inset.left;
		int t = inset.top;
		int r = inset.right;
		int b = inset.bottom;
		super.setSize(m_width + l + r, m_height + b + t);
	}

	@Override
	public void hideView()
	{
		super.setVisible(false);
		super.setSize(0, 0);
	}

	public void addRow(int _reserveId, String _nickname, String _beginTime, String _endTime)
	{
		// NOTE: ���� GUI�� �����Ǿ� ���� �ʽ��ϴ�.
		// TODO: GUI �������� �Ϸ�� �� �� �Լ��� �����ؾ� �մϴ�.
	}

	public void removeRow(int _reserveId)
	{
		// NOTE: ���� GUI�� �����Ǿ� ���� �ʽ��ϴ�.
		// TODO: GUI �������� �Ϸ�� �� �� �Լ��� �����ؾ� �մϴ�.
	}
}
